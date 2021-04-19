package com.stg.gateway.service.impl;

import com.stg.gateway.service.StgRocketMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yq
 * @date 2021-04-14 10:48
 */
@Slf4j
@Service
public class StgRocketMqServiceImpl implements StgRocketMqService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public boolean sendMessage(String topic, String message) {
        try {
            rocketMQTemplate.convertAndSend(topic, message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送MQ消息异常={}", e.getMessage());
            return false;
        }
        return true;
    }
}
