package com.stg.gateway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2021-04-19 9:49
 */
@Data
@TableName("stg_log")
@EqualsAndHashCode(callSuper = true)
public class StgLog extends Model<StgLog> {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String platform;

    /**
     *
     */
    private String body;

    /**
     *
     */
    private String toUser;

    /**
     *
     */
    private Integer messageType;

    /**
     *
     */
    private LocalDateTime time;
}
