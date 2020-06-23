package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradeMqConsumerLog implements Serializable {

    private Long mqConsumerLogId;

    private String msgId;

    private String msgGroupName;

    private String msgTag;

    private String msgKey;

    private String msgBody;

    private Integer consumerStatus;

    private Integer consumerCount;

    private Long consumerTime;

    private String remark;

    private Long createTime;

    private Long updateTime;
}