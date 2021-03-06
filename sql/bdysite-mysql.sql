
-- 产品表

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name`  VARCHAR(20) NULL COMMENT '产品名称',
  `img_url` VARCHAR(200) NOT NULL COMMENT '图片路径',
  `detail` VARCHAR(300) NULL COMMENT '产品描述',
  `amount` DECIMAL(10,2) NULL COMMENT '产品金额',
  `is_show` CHAR(1) NULL COMMENT '0展示,1不展示',
  `is_sort` INT NULL COMMENT '值越大越靠前',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY  (`id`)
) ENGINE=INNODB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='产品表';

-- 用户表 

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` VARCHAR(30) NULL COMMENT 'openId',
  `nick_name` VARCHAR(30) NULL COMMENT '昵称',
  `sex` CHAR(1) NOT NULL COMMENT '1男性,2女性,0未知',
  `province` VARCHAR(30) NULL COMMENT '省份',
  `city` VARCHAR(50) NULL COMMENT '城市',
  `country` VARCHAR(30) NULL COMMENT '国家',
  `headimg` VARCHAR(200) NULL COMMENT '头像', 
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY  (`id`)
) ENGINE=INNODB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 邮件表
DROP TABLE IF EXISTS `mail`;

CREATE TABLE `mail` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `smtp` VARCHAR(30) NULL COMMENT 'smtp',
  `from` VARCHAR(30) NULL COMMENT '发送人',
  `password` VARCHAR(30) NULL COMMENT '邮箱密码',
  `to` VARCHAR(30) NULL COMMENT '收信人',
  `copyto`   VARCHAR(200) NULL COMMENT '抄送',
  `subject`  VARCHAR(200) NULL COMMENT '主题',
  `content`  TEXT NULL COMMENT '内容',
  `file_name` VARCHAR(30) NULL COMMENT '附件名称',
  `time_date` TIME DEFAULT NULL COMMENT '定时时间',
  `show` TIME DEFAULT '20' COMMENT '是否有效  10有效,20无效',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY  (`id`)
) ENGINE=INNODB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='邮件表';

-- 意见表
DROP TABLE IF EXISTS `opinion`;

CREATE TABLE `opinion` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` VARCHAR(30) NULL COMMENT '用户ID',
  `countent` VARCHAR(300) NULL COMMENT '意见内容',
  `img_url` VARCHAR(100) NULL COMMENT '图片路径',
  `contact` VARCHAR(300) NULL COMMENT '联系方式',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY  (`id`)
) ENGINE=INNODB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='意见表';