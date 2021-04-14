package com.stg.gateway.controller;

import com.stg.gateway.handler.StgWebSocketHandler;
import com.stg.gateway.util.R;
import org.springframework.web.bind.annotation.*;


/**
 * @author yq
 * @date 2021-04-14 13:26
 */
@RestController
@RequestMapping("/websocket")
public class StgWebsocketController {

    /**
     * 获取当前所有用户数
     * @return
     */
    @GetMapping("/getWebsocketConnectionsNum")
    public R getWebsocketConnectionsNum() {
        return R.ok(StgWebSocketHandler.getSessionConcurrentHashMap().size());
    }

    /**
     * 获取当前所有用户
     * @return
     */
    @GetMapping("/getWebsocketConnections")
    public R getWebsocketConnections() {
        return R.ok(StgWebSocketHandler.getSessionConcurrentHashMap().keys());
    }
}
