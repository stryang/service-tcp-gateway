package com.stg.gateway.util;

import com.stg.gateway.constant.CommonConstants;
import com.stg.gateway.vo.WebsocketRegisterVo;
import lombok.experimental.UtilityClass;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author yq
 * @date 2021-04-14 14:06
 */
@UtilityClass
public class WebSocketUtil {

    /**
     * 从websocket session获取当前登录用户信息
     * @param session
     * @return
     */
    public WebsocketRegisterVo getCurrentUserInfo(WebSocketSession session) {
        Object object = session.getAttributes().get(CommonConstants.WEBSOCKET_USER_INFO);
        if(object == null) {
            return null;
        }
        return (WebsocketRegisterVo) object;
    }

    public String getCurrentUsername(WebSocketSession session) {
        WebsocketRegisterVo websocketRegisterVo = getCurrentUserInfo(session);
        if(websocketRegisterVo != null) {
            return websocketRegisterVo.getUsername();
        }
        return "";
    }
}
