package com.stg.gateway.config;

import com.stg.gateway.constant.CommonConstants;
import com.stg.gateway.vo.WebsocketRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * websocket建立握手拦截器：
 * <p>
 * 从session中获取到当前登录的用户信息. 作为socket的账号信息.
 * <p>
 * session的用户信息,在用户打开页面的时候设置从session中获取到当前登录的用户信息.
 * <p>
 * 作为socket的账号信息. session的用户信息,在用户打开页面的时候设置
 *
 * @author yq
 * @date 2021-04-14 13:14
 */
@Slf4j
@Component
public class StgWebsocketHandshakeConfig implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String username = servletRequest.getParameter("username");
            String platform = servletRequest.getParameter("platform");

            WebsocketRegisterVo websocketRegisterVo = new WebsocketRegisterVo();
            websocketRegisterVo.setUsername(username);
            websocketRegisterVo.setPlatform(platform);

            attributes.put(CommonConstants.WEBSOCKET_USER_INFO, websocketRegisterVo);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest,
                               ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler,
                               Exception e) {

    }
}