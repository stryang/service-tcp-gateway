package com.stg.gateway.service;


/**
 * @author yq
 * @date 2021-04-14 10:48
 */
public interface StgRocketMqService {

    boolean sendMessage(String topic, String message);

}
