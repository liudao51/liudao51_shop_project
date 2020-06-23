/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.43 : Database - liudao51_shop
*********************************************************************
*/

USE `liudao51_shop`;

/*Table structure for table `trade_coupon` */

CREATE TABLE `trade_coupon` (
  `coupon_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '优惠券ID',
  `coupon_price` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券金额',
  `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '订单ID',
  `use_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否使用(0未使用,1已使用)',
  `use_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '使用时间',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`coupon_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `trade_coupon` */

INSERT  INTO `trade_coupon`(`coupon_id`,`coupon_price`,`user_id`,`order_id`,`is_used`,`used_time`,`create_time`,`update_time`) VALUES (345988230098857984,'20.00',345963634385633280,727572335934181376,1,1593508362870,1593507270138,1593508362870);

/*Table structure for table `trade_goods` */

CREATE TABLE `trade_goods` (
  `goods_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '商品ID',
  `goods_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '商品名称',
  `goods_stock` INT(11) NOT NULL DEFAULT '0' COMMENT '商品库存',
  `goods_price` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `goods_desc` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '商品描述',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `trade_goods` */

INSERT  INTO `trade_goods`(`goods_id`,`goods_name`,`goods_stock`,`goods_price`,`goods_desc`,`create_time`,`update_time`) VALUES (345959443973935104,'Javase课程',999,'1000.00','传智播客出品Java视频课程',1593423849569,1593508362859);

/*Table structure for table `trade_goods_stock_log` */

CREATE TABLE `trade_goods_stock_log` (
  `goods_stock_id` BIGINT(11) UNSIGNED NOT NULL COMMENT '商品库存日志ID',
  `goods_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '商品ID',
  `order_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '订单ID',
  `goods_stock` INT(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`goods_stock_id`),
  UNIQUE KEY `unique_order_id_and_goods_id` (`order_id`,`goods_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `trade_goods_stock_log` */

INSERT  INTO `trade_goods_stock_log`(`goods_stock_id`,`goods_id`,`order_id`,`goods_stock`,`create_time`,`update_time`) VALUES (727572337347661824,345959443973935104,727572335934181376,-1,1593508362859,1593508362859);

/*Table structure for table `trade_mq_consumer_log` */

CREATE TABLE `trade_mq_consumer_log` (
  `mq_consumer_log_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消费者日志ID',
  `msg_id` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '消息ID',
  `msg_group_name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '消息分组名',
  `msg_tag` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '消息tag',
  `msg_key` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '消息key',
  `msg_body` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '消息内容体',
  `consumer_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消费者处理状态(0正在处理, 1处理成功, 2处理失败)',
  `consumer_count` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消费次数',
  `consumer_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消费时间',
  `remark` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`mq_consumer_log_id`),
  UNIQUE KEY `unique_group_name_and_msg_tag_and_msg_key` (`group_name`,`msg_tag`,`msg_key`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `trade_mq_consumer_log` */

/*Table structure for table `trade_mq_producer_temp` */

CREATE TABLE `trade_mq_producer_temp` (
  `my_producer_temp_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '生产者ID',
  `group_name` VARCHAR(100) NOT NULL DEFAULT '',
  `msg_topic` VARCHAR(100) NOT NULL DEFAULT '',
  `msg_tag` VARCHAR(100) NOT NULL DEFAULT '',
  `msg_key` VARCHAR(100) NOT NULL DEFAULT '',
  `msg_body` VARCHAR(500) NOT NULL DEFAULT '',
  `msg_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消息状态(0未处理, 1已经处理)',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`my_producer_temp_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `trade_mq_producer_temp` */

/*Table structure for table `trade_order` */

CREATE TABLE `trade_order` (
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单ID',
  `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '订单状态(0未确认,1已确认,2已取消,3无效,4退款)',
  `pay_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '支付状态(0未支付,1支付中,2已支付)',
  `shipping_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发货状态(0未发货,1已发货,2已收货)',
  `address` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '收货地址',
  `consignee` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '收货人',
  `goods_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '商品ID',
  `goods_number` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '商品数量',
  `goods_price` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `goods_amount` DECIMAL(10,0) NOT NULL DEFAULT '0' COMMENT '商品总价',
  `shipping_fee` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `order_amount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单价格',
  `coupon_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '优惠券ID',
  `coupon_paid` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券',
  `money_paid` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '已付金额',
  `pay_amount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `pay_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '支付时间',
  `confirm_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '订单确认时间',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT  INTO `trade_order`(`order_id`,`user_id`,`order_status`,`pay_status`,`shipping_status`,`address`,`consignee`,`goods_id`,`goods_number`,`goods_price`,`goods_amount`,`shipping_fee`,`order_amount`,`coupon_id`,`coupon_paid`,`money_paid`,`pay_amount`,`pay_time`,`confirm_time`,`create_time`,`update_time`) VALUES (727572335934181376,345963634385633280,0,0,0,'北京','小威',345959443973935104,1,'1000.00','1000','0.00','980.00',345988230098857984,'20.00','0.00','0.00',0,0,1593508362524,1593508362524);

/*Table structure for table `trade_pay` */

CREATE TABLE `trade_pay` (
  `pay_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '支付ID',
  `order_id` BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '订单ID',
  `pay_amount` DECIMAL(10,2) DEFAULT '0.00' COMMENT '支付金额',
  `is_paid` TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否已支付(0未付款, 1正在付款, 2已经付款)',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`pay_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `trade_user` */

CREATE TABLE `trade_user` (
  `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `user_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `user_password` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户密码',
  `user_mobile` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '手机号',
  `user_score` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '积分',
  `user_money` DECIMAL(10,0) NOT NULL DEFAULT '0' COMMENT '用户余额',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT  INTO `trade_user`(`user_id`,`user_name`,`user_password`,`user_mobile`,`user_score`,`user_money`,`create_time`,`update_time`) VALUES (345963634385633280,'zs','123456','18888888888',100,'1000',0,0);

/*Table structure for table `trade_user_money_log` */
CREATE TABLE `trade_user_money_log` (
  `user_money_log_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户余额日志ID',
  `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '订单ID',
  `money_log_type` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '日志类型(1订单付款, 2订单退款)',
  `use_money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '使用金额',
  `create_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`user_money_log_id`),
  KEY `idx_user_id_and_order_id` (`user_id`,`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
