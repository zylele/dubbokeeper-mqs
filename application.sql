CREATE DATABASE `dubbox_monitor` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

use dubbox_monitor;

CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `type` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `应用名词索引` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `service_warning` ();
CREATE TABLE dubbox_monitor.biz_warning (
	id INT NOT NULL AUTO_INCREMENT,
	trace_id varchar(100) NULL COMMENT '报文ID',
	trace_content varchar(100) NULL COMMENT '报文内容',
	trace_dt DATETIME NULL COMMENT '报文时间',
	CONSTRAINT biz_warning_pk PRIMARY KEY (id),
	CONSTRAINT biz_warning_un UNIQUE KEY (trace_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_unicode_ci ;

CREATE TABLE dubbox_monitor.notification (
	id INT NOT NULL AUTO_INCREMENT,
	`type` varchar(100) NULL COMMENT '01-邮件，02-手机，03-微信',
	receiver varchar(100) NULL COMMENT '接收地址'
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_unicode_ci ;


