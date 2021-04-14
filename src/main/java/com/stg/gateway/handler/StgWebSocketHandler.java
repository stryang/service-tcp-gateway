package com.stg.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.stg.gateway.service.StgWebsocketService;
import com.stg.gateway.util.WebSocketUtil;
import com.stg.gateway.vo.WebsocketRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yq
 * @date 2021-04-14 13:11
 */
@Slf4j
@Component
public class StgWebSocketHandler implements WebSocketHandler {

    @Autowired
    private StgWebsocketService stgWebsocketService;

    /**
     * 为了保存在线用户信息，在方法中新建一个map存储一下【实际项目依据复杂度，可以存储到数据库或者缓存】
     */
    private static final ConcurrentHashMap<String, WebSocketSession> sessionConcurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("链接成功......");
        session.getHandshakeHeaders();
        WebsocketRegisterVo currentUserInfo = WebSocketUtil.getCurrentUserInfo(session);
        if (currentUserInfo == null) {
            return;
        }
        sessionConcurrentHashMap.put(currentUserInfo.getUsername(), session);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", sessionConcurrentHashMap.size());
        session.sendMessage(new TextMessage(jsonObject.toJSONString()));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageStr = message.getPayload().toString();
        log.info("websocket收到消息={}", messageStr);
        stgWebsocketService.sendMessage(messageStr);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.info("链接出错，关闭链接......");
        removeSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("链接关闭......" + closeStatus.toString());
        removeSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void removeSession(WebSocketSession session) {
        sessionConcurrentHashMap.remove(WebSocketUtil.getCurrentUsername(session));
    }

    public static ConcurrentHashMap<String, WebSocketSession> getSessionConcurrentHashMap() {
        return sessionConcurrentHashMap;
    }

}
