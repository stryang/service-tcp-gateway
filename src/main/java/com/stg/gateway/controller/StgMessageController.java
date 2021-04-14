package com.stg.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.stg.gateway.constant.CommonConstants;
import com.stg.gateway.service.StgRocketMqService;
import com.stg.gateway.util.R;
import com.stg.gateway.vo.MessagePushVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 提供restful接口推送消息
 *
 * @author yq
 * @date 2021-04-14 9:57
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class StgMessageController {

    @Autowired
    private StgRocketMqService stgRocketMqService;

    @PostMapping("/push")
    public R push(@RequestBody MessagePushVo messagePushVo) {
        return R.ok(stgRocketMqService.sendMessage(CommonConstants.MQ_TOPIC_STG, JSONObject.toJSONString(messagePushVo)));
    }

}
