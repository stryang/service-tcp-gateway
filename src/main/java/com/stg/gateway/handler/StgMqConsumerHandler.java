package com.stg.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.stg.gateway.service.StgWebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yq
 * @date 2021-04-14 10:54
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "MQ_TOPIC_STG", consumerGroup = "service-tcp-gateway_MQ_TOPIC_STG")
public class StgMqConsumerHandler implements RocketMQListener<String> {

    @Autowired
    private StgWebsocketService stgWebsocketService;

    @Override
    public void onMessage(String message) {
        log.info("rocketmq收到消息: {}", message);
        stgWebsocketService.sendMessage(message);
    }

}
