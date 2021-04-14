

package com.stg.gateway.constant;

/**
 * @author yq
 * @date 2021-04-14 15:43
 */
public interface CommonConstants {

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;

	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * mq消息主题
	 */
	String MQ_TOPIC_STG = "MQ_TOPIC_STG";

	/**
	 * mq消息组
	 */
	String MQ_TOPIC_GROUP = "service-tcp-gateway_" + MQ_TOPIC_STG;

	/**
	 * websocket注册用户信息
	 */
	String WEBSOCKET_USER_INFO = "WEBSOCKET_USER_INFO";

	/**
	 * 发送给所有人
	 */
	Integer WEBSOCKET_MESSAGE_TYPE_ALL = 1;

	/**
	 * 发送给多个用户
	 */
	Integer WEBSOCKET_MESSAGE_TYPE_USERS = 2;

	/**
	 * 发送给单个用户
	 */
	Integer WEBSOCKET_MESSAGE_TYPE_USER = 3;

}
