package com.stg.gateway.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yq
 * @date 2021-04-14 10:04
 */
@Data
public class MessagePushVo {

    private String body;
    private String form;
    private List<String> to;
    private Integer type;
    private Integer save;

}
