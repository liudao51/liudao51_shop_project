package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradeMqProducerTemp implements Serializable {
    private Long mqProducerTempId;

    private String groupName;

    private String msgTopic;

    private String msgTag;

    private String msgKey;

    private String msgBody;

    private Integer msgStatus;

    private Long createTime;

    private Long updateTime;
}