CREATE DATABASE `dubbox_monitor` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

use dubbox_monitor;

CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `type` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `应用名词索引` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `notification` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '01-邮件，02-手机，03-微信',
  `receiver` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接收人',
  `address` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接收地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `notification_un` (`receiver`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `biz_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `trace_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报文ID',
  `trace_content` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报文内容',
  `trace_dt` datetime(6) DEFAULT NULL COMMENT '报文时间',
  `error` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tx_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `biz_warning_un` (`trace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `service_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `host` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `status` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '00-新告警，01-手动恢复，02-告警过期',
  `content` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_interface` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `application` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `trading`(
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`tx_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易编号',
	`now_time` date DEFAULT NULL,
	`total_num` bigint(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '总次数',
	`time_avg` bigint(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '平均耗时',
	`time_max` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最大耗时',
	`time_min` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最小耗时',
	`success` int(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '成功总数',
  	`fail` int(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '失败总数',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `statistics_day`(
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`start_time` datetime(6) DEFAULT NULL COMMENT '开始时间',
	`timestamp` bigint(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '时间戳',
	`total_time_num` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易量',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

