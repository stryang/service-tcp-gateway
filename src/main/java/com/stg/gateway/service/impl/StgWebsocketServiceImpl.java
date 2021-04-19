package com.stg.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stg.gateway.constant.CommonConstants;
import com.stg.gateway.entity.StgLog;
import com.stg.gateway.handler.StgWebSocketHandler;
import com.stg.gateway.service.StgLogService;
import com.stg.gateway.service.StgWebsocketService;
import com.stg.gateway.util.LocalDateTimeUtil;
import com.stg.gateway.vo.MessagePushVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yq
 * @date 2021-04-14 15:43
 */
@Slf4j
@Service
public class StgWebsocketServiceImpl implements StgWebsocketService {

    @Autowired
    private StgLogService stgLogService;

    @Override
    @Transactional
    public void sendMessage(String message) {
        MessagePushVo messagePushVo = JSON.parseObject(message, MessagePushVo.class);
        JSONObject toMessageJsonObject = new JSONObject();
        toMessageJsonObject.put("body", messagePushVo.getBody());
        toMessageJsonObject.put("from", messagePushVo.getForm());
        toMessageJsonObject.put("time", LocalDateTimeUtil.getyyyy_MMddHHmmss(LocalDateTime.now()));

        // 记录入库
        if(messagePushVo.getSave().equals(CommonConstants.MESSAGE_SAVE)) {
            StgLog stgLog = new StgLog();
            stgLog.setTime(LocalDateTime.now());
            stgLog.setMessageType(messagePushVo.getType());
            stgLog.setBody(messagePushVo.getBody());
            stgLog.setPlatform(messagePushVo.getForm());
            stgLog.setToUser(messagePushVo.getTo().toString());
            stgLogService.save(stgLog);
        }

        if (messagePushVo.getType().equals(CommonConstants.WEBSOCKET_MESSAGE_TYPE_ALL)) {
            this.sendMessageToAllUsers(toMessageJsonObject);
            return;
        }

        if (messagePushVo.getType().equals(CommonConstants.WEBSOCKET_MESSAGE_TYPE_USERS)) {
            this.sendMessageToUsers(messagePushVo.getTo(), toMessageJsonObject);
            return;
        }

        if (messagePushVo.getType().equals(CommonConstants.WEBSOCKET_MESSAGE_TYPE_USER)) {
            this.sendMessageToUsers(messagePushVo.getTo(), toMessageJsonObject);
        }
    }

    // 发送给多个人
    public void sendMessageToUsers(List<String> toUsername, JSONObject toMessageJsonObject) {
        List<WebSocketSession> webSocketSessionList = new ArrayList<>();
        toUsername.forEach(username -> webSocketSessionList.add(StgWebSocketHandler
                        .getSessionConcurrentHashMap()
                        .get(username)));
        sendMessageToUsers(webSocketSessionList, new TextMessage(toMessageJsonObject.toJSONString()));
    }

    // 发送给所有人
    public void sendMessageToAllUsers(JSONObject toMessageJsonObject) {
        sendMessageToUsers(StgWebSocketHandler.getSessionConcurrentHashMap().values(), new TextMessage(toMessageJsonObject.toJSONString()));
    }

    public void sendMessageToUsers(Collection<WebSocketSession> webSocketSessionList, TextMessage message) {
        webSocketSessionList.forEach(webSocketSession -> {
            if(webSocketSession == null) {
                return;
            }
            try {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("发送消息失败：{}", message);
            }
        });
    }
}
