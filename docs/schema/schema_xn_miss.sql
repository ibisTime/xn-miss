/*
 Navicat MySQL Data Transfer

 Source Server         : 183
 Source Server Type    : MySQL
 Source Server Version : 50633
 Source Host           : 47.96.161.183:3307
 Source Schema         : dev_xn_miss

 Target Server Type    : MySQL
 Target Server Version : 50633
 File Encoding         : 65001

 Date: 19/10/2018 21:06:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for thqxj_action
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_action`;
CREATE TABLE `thqxj_action` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) NOT NULL COMMENT '类型（0 分享，1 足迹，2 关注，3 收藏，4 点赞）',
  `to_type` varchar(4) NOT NULL COMMENT '操作对象类型（选手）',
  `to_code` varchar(32) NOT NULL COMMENT '操作对象编号',
  `creater` varchar(32) NOT NULL COMMENT '操作人',
  `create_datetime` datetime NOT NULL COMMENT '操作时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为';

-- ----------------------------
-- Table structure for thqxj_answer
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_answer`;
CREATE TABLE `thqxj_answer` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `question` varchar(255) NOT NULL COMMENT '问题描述',
  `answer` varchar(255) NOT NULL COMMENT '回复内容',
  `status` varchar(4) NOT NULL COMMENT '状态（0 草稿，1 待审批，2 审批不通过，3 审批通过，4 已上架，5 已下架）',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='回复模版';

-- ----------------------------
-- Table structure for thqxj_comment
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_comment`;
CREATE TABLE `thqxj_comment` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父级编号',
  `type` varchar(32) DEFAULT NULL COMMENT '类型（1=帖子 2=帖子评论）',
  `creater` varchar(32) DEFAULT NULL COMMENT '评论人',
  `to_code` varchar(32) DEFAULT NULL COMMENT '评论对象',
  `content` text COMMENT '评论内容',
  `create_datetime` datetime DEFAULT NULL COMMENT '评论时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 待审核，1 审核通过，2 审核不通过）',
  `approver` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`) COMMENT '评论'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论';

-- ----------------------------
-- Table structure for thqxj_event
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_event`;
CREATE TABLE `thqxj_event` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `status` varchar(4) NOT NULL COMMENT '状态（0 草稿，1 已提交待审批，2 审批不通过，3 已审批待上架，4 已上架,5 已下架）',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赛事信息';

-- ----------------------------
-- Table structure for thqxj_keyword
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_keyword`;
CREATE TABLE `thqxj_keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号（自增长）',
  `word` varchar(255) DEFAULT NULL COMMENT '词语',
  `level` varchar(4) DEFAULT NULL COMMENT '作用等级',
  `reaction` varchar(4) DEFAULT NULL COMMENT '反应(1 直接拦截/2 替换**/3 审核)',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) COMMENT '关键字'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='关键字';

-- ----------------------------
-- Table structure for thqxj_player
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_player`;
CREATE TABLE `thqxj_player` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `match` varchar(64) NOT NULL COMMENT '赛区',
  `match_play_code` varchar(32) NOT NULL COMMENT '选手编号',
  `cname` varchar(64) DEFAULT NULL COMMENT '中文名',
  `ename` varchar(64) DEFAULT NULL COMMENT '英文名',
  `native_place` varchar(64) DEFAULT NULL COMMENT '户籍',
  `height` varchar(64) DEFAULT NULL COMMENT '身高',
  `weight` varchar(64) DEFAULT NULL COMMENT '体重',
  `xwei` varchar(64) DEFAULT NULL COMMENT '胸围',
  `ywei` varchar(64) DEFAULT NULL COMMENT '腰围',
  `twei` varchar(64) DEFAULT NULL COMMENT '臀围',
  `description` varchar(255) DEFAULT NULL COMMENT '文字描述',
  `list_pic` varchar(255) DEFAULT NULL COMMENT '列表图',
  `banner_pics` varchar(255) DEFAULT NULL COMMENT 'banner 图',
  `pics` varchar(255) DEFAULT NULL COMMENT '详情图',
  `location` varchar(64) DEFAULT NULL COMMENT 'UI位置',
  `order_no` int(11) DEFAULT NULL COMMENT 'UI次序',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 草稿，1 已提交待审批，2 审批不通过，3 已审批待上架，4 已上架，5 已下架）',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ticket_sum` bigint(20) DEFAULT NULL COMMENT '加油总数',
  `attention_sum` bigint(20) DEFAULT NULL COMMENT '关注总数',
  `share_sum` bigint(20) DEFAULT NULL COMMENT '分享总数',
  `scan_sum` bigint(20) DEFAULT NULL COMMENT '足迹总数',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选手';

-- ----------------------------
-- Table structure for thqxj_question
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_question`;
CREATE TABLE `thqxj_question` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `session_code` varchar(32) NOT NULL COMMENT '会话编号',
  `user_id` varchar(32) NOT NULL COMMENT '说话人',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `status` varchar(4) NOT NULL COMMENT '状态（0 未读，1 已读）',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='会话消息';

-- ----------------------------
-- Table structure for thqxj_rank
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_rank`;
CREATE TABLE `thqxj_rank` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) NOT NULL COMMENT '类型（1 日榜 2总榜）',
  `batch` varchar(32) DEFAULT NULL COMMENT '批次',
  `player_code` varchar(32) DEFAULT NULL COMMENT '选手编号',
  `rank` int(11) DEFAULT NULL COMMENT '排名',
  `match` varchar(64) DEFAULT NULL COMMENT '赛区',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ticket_sum` bigint(20) DEFAULT NULL COMMENT '实际加油总数',
  `fake_ticket_sum` bigint(20) DEFAULT NULL COMMENT '虚拟加油总数',
  `attention_sum` bigint(20) DEFAULT NULL COMMENT '关注总数',
  `share_sum` bigint(20) DEFAULT NULL COMMENT '分享总数',
  `scan_sum` bigint(20) DEFAULT NULL COMMENT '足迹总数',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='榜单';

-- ----------------------------
-- Table structure for thqxj_read
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_read`;
CREATE TABLE `thqxj_read` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `to_type` varchar(4) NOT NULL COMMENT '对象类型',
  `to_code` varchar(32) NOT NULL COMMENT '对象编号',
  `status` varchar(4) NOT NULL COMMENT '状态（0 未读，1 已读）',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='阅读';

-- ----------------------------
-- Table structure for thqxj_session
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_session`;
CREATE TABLE `thqxj_session` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) NOT NULL COMMENT '类型（帮助）',
  `user1` varchar(32) DEFAULT NULL COMMENT '说话人1',
  `user2` varchar(32) DEFAULT NULL COMMENT '说话人2',
  `unread_sum` bigint(20) NOT NULL COMMENT '未读消息数量',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话';

-- ----------------------------
-- Table structure for thqxj_ticket
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_ticket`;
CREATE TABLE `thqxj_ticket` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `player_code` varchar(32) DEFAULT NULL COMMENT '选手表编号',
  `ticket` bigint(20) DEFAULT NULL COMMENT '票数',
  `amount` decimal(64,3) DEFAULT NULL COMMENT '下单金额',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '下单人',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 待支付，1 已支付，2 超时取消）',
  `create_datetime` datetime DEFAULT NULL COMMENT '下单时间',
  `invalid_datetime` datetime DEFAULT NULL COMMENT '失效时间',
  `pay_type` varchar(4) DEFAULT NULL COMMENT '支付方式',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付渠道单号',
  `pay_amount` decimal(64,0) DEFAULT NULL COMMENT '支付金额',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `canceler` varchar(32) DEFAULT NULL COMMENT '取消人',
  `cancel_datetime` datetime DEFAULT NULL COMMENT '取消时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='加油订单';

-- ----------------------------
-- Table structure for tstd_account
-- ----------------------------
DROP TABLE IF EXISTS `tstd_account`;
CREATE TABLE `tstd_account` (
  `account_number` varchar(32) NOT NULL COMMENT '账户编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `real_name` varchar(64) DEFAULT NULL COMMENT '户名',
  `type` varchar(4) DEFAULT NULL COMMENT '类别（B端账号，C端账号，平台账号）',
  `status` varchar(2) DEFAULT NULL COMMENT '状态（正常/程序冻结/人工冻结）',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '余额',
  `frozen_amount` decimal(64,0) DEFAULT NULL COMMENT '冻结金额',
  `md5` varchar(32) DEFAULT NULL COMMENT 'MD5',
  `add_amount` decimal(64,0) DEFAULT '0' COMMENT '累计增加金额',
  `in_amount` decimal(64,0) DEFAULT '0' COMMENT '总充值金额（入金）',
  `out_amount` decimal(64,0) DEFAULT '0' COMMENT '总取现金额（出金）',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `last_order` varchar(32) DEFAULT NULL COMMENT '最近一次变动对应的流水编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  PRIMARY KEY (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户';

-- ----------------------------
-- Table structure for tstd_bankcard
-- ----------------------------
DROP TABLE IF EXISTS `tstd_bankcard`;
CREATE TABLE `tstd_bankcard` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `bankcard_number` varchar(64) DEFAULT NULL COMMENT '银行卡编号',
  `bank_code` varchar(32) DEFAULT NULL COMMENT '银行行别',
  `bank_name` varchar(32) DEFAULT NULL COMMENT '银行名称',
  `subbranch` varchar(255) DEFAULT NULL COMMENT '开户支行',
  `bind_mobile` varchar(32) DEFAULT NULL COMMENT '银行卡绑定手机号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `real_name` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `type` varchar(4) DEFAULT NULL COMMENT '类型',
  `status` varchar(2) DEFAULT NULL COMMENT '状态',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '余额',
  `frozen_amount` decimal(64,0) DEFAULT NULL COMMENT '冻结金额',
  `md5` varchar(32) DEFAULT NULL COMMENT 'MD5',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `last_order` varchar(32) DEFAULT NULL COMMENT '最近一次变动对应的流水编号',
  `system_code` varchar(32) NOT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tstd_charge
-- ----------------------------
DROP TABLE IF EXISTS `tstd_charge`;
CREATE TABLE `tstd_charge` (
  `code` varchar(32) NOT NULL COMMENT '充值编号',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '订单分组组号',
  `ref_no` varchar(32) DEFAULT NULL COMMENT '流水分组组号',
  `account_number` varchar(32) DEFAULT NULL COMMENT '针对账号',
  `collection_account_number` varchar(32) DEFAULT NULL COMMENT '收款账号',
  `account_name` varchar(64) DEFAULT NULL COMMENT '针对户名',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '充值金额',
  `type` varchar(4) DEFAULT NULL COMMENT '类别（C端账号/B端账号/P平台账号）',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `biz_type` varchar(32) DEFAULT NULL COMMENT '业务类型编号（因为什么业务类型而充值）',
  `biz_note` varchar(255) DEFAULT NULL COMMENT '业务类型说明（因为什么业务类型而充值）',
  `pay_card_info` varchar(255) DEFAULT NULL COMMENT ' 支付渠道账号信息（如开户支行）',
  `pay_card_no` varchar(32) DEFAULT NULL COMMENT '支付渠道账号（如银行卡号）',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 待审核/1 审核通过/2 审核不通过）',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '申请人',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `pay_user` varchar(32) DEFAULT NULL COMMENT '支付回录人',
  `pay_note` varchar(255) DEFAULT NULL COMMENT '支付渠道的说明',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `channel_type` varchar(32) DEFAULT NULL COMMENT '支付渠道',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值';

-- ----------------------------
-- Table structure for tstd_company_channel
-- ----------------------------
DROP TABLE IF EXISTS `tstd_company_channel`;
CREATE TABLE `tstd_company_channel` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '编号（自增长）',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(32) DEFAULT NULL COMMENT '公司名称',
  `channel_type` varchar(4) DEFAULT NULL COMMENT '渠道类型',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（启用/不启用）',
  `channel_company` varchar(32) DEFAULT NULL COMMENT '渠道给公司的代号',
  `private_key1` text COMMENT '秘钥1',
  `private_key2` text COMMENT '私钥2',
  `private_key3` text COMMENT '私钥3',
  `private_key4` text COMMENT '私钥4',
  `private_key5` text COMMENT '私钥5',
  `page_url` varchar(255) DEFAULT NULL COMMENT '界面正确回调地址',
  `error_url` varchar(255) DEFAULT NULL COMMENT '界面错误回调地址',
  `back_url` varchar(255) DEFAULT NULL COMMENT '服务器回调地址',
  `fee` bigint(32) DEFAULT NULL COMMENT '手续费',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `system_code` varchar(32) NOT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tstd_exchange_currency
-- ----------------------------
DROP TABLE IF EXISTS `tstd_exchange_currency`;
CREATE TABLE `tstd_exchange_currency` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `to_user_id` varchar(32) DEFAULT NULL COMMENT '去方用户',
  `to_amount` decimal(64,0) DEFAULT NULL COMMENT '去方金额',
  `to_currency` varchar(32) DEFAULT NULL COMMENT '去方币种',
  `from_user_id` varchar(32) DEFAULT NULL COMMENT '来方用户',
  `from_amount` decimal(64,0) DEFAULT NULL COMMENT '来方金额',
  `from_currency` varchar(32) DEFAULT NULL COMMENT '来方币种',
  `create_datetime` datetime DEFAULT NULL COMMENT '产生时间',
  `status` varchar(4) NOT NULL COMMENT '状态',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `pay_type` varchar(4) DEFAULT NULL COMMENT '支付方式',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `pay_amount` bigint(20) DEFAULT NULL COMMENT '支付金额',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '币种兑换'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tstd_hlorder
-- ----------------------------
DROP TABLE IF EXISTS `tstd_hlorder`;
CREATE TABLE `tstd_hlorder` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `account_number` varchar(32) NOT NULL COMMENT '账户编号',
  `account_name` varchar(64) NOT NULL COMMENT '针对户名（手机号或其他）',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `jour_code` varchar(32) DEFAULT NULL COMMENT '流水号',
  `channel_type` varchar(32) DEFAULT NULL COMMENT '支付渠道',
  `direction` char(1) NOT NULL COMMENT '方向：1=蓝补；0=红冲',
  `amount` decimal(64,0) NOT NULL COMMENT '金额',
  `status` varchar(4) NOT NULL COMMENT '状态',
  `apply_user` varchar(32) NOT NULL COMMENT '申请人',
  `apply_note` varchar(255) NOT NULL COMMENT '申请说明',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `approve_user` varchar(32) DEFAULT NULL COMMENT '审批人',
  `approve_note` varchar(255) DEFAULT NULL COMMENT '审批说明',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审批时间',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红蓝订单';

-- ----------------------------
-- Table structure for tstd_jour
-- ----------------------------
DROP TABLE IF EXISTS `tstd_jour`;
CREATE TABLE `tstd_jour` (
  `code` varchar(32) NOT NULL COMMENT '流水编号',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '订单分组组号（',
  `ref_no` varchar(32) DEFAULT NULL COMMENT '流水分组组号',
  `channel_order` varchar(32) DEFAULT NULL COMMENT '支付渠道单号',
  `account_number` varchar(32) DEFAULT NULL COMMENT '流水所属账号',
  `trans_amount` decimal(64,0) DEFAULT NULL COMMENT '变动金额（有正负之分）',
  `user_id` varchar(32) DEFAULT NULL COMMENT '流水所属用户编号',
  `real_name` varchar(64) DEFAULT NULL COMMENT '流水所属真实姓名',
  `type` varchar(4) DEFAULT NULL COMMENT '类型(0余额流水1冻结金额流水)',
  `account_type` varchar(4) DEFAULT NULL COMMENT '账户类型（P平台C C端账户 B B端账户）',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `biz_type` varchar(32) DEFAULT NULL COMMENT '业务类型',
  `biz_note` varchar(255) DEFAULT NULL COMMENT '业务说明',
  `pre_amount` decimal(64,0) DEFAULT NULL COMMENT '变动前金额',
  `post_amount` decimal(64,0) DEFAULT NULL COMMENT '变动后金额',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `work_date` varchar(8) DEFAULT NULL COMMENT '拟对账日期',
  `check_user` varchar(32) DEFAULT NULL COMMENT '对账人',
  `check_note` varchar(255) DEFAULT NULL COMMENT '对账说明',
  `check_datetime` datetime DEFAULT NULL COMMENT '对账时间',
  `adjust_user` varchar(32) DEFAULT NULL COMMENT '调账人',
  `adjust_note` varchar(255) DEFAULT NULL COMMENT '调账说明',
  `adjust_datetime` datetime DEFAULT NULL COMMENT '调账时间',
  `channel_type` varchar(32) DEFAULT NULL COMMENT '支付渠道（线下/招商代付/支付宝/内部转账）',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流水';

-- ----------------------------
-- Table structure for tstd_sign_log
-- ----------------------------
DROP TABLE IF EXISTS `tstd_sign_log`;
CREATE TABLE `tstd_sign_log` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `type` varchar(4) DEFAULT NULL COMMENT '分类（1-登录日志；2-签到日志）',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `client` varchar(4) DEFAULT NULL COMMENT '客户端',
  `location` varchar(255) DEFAULT NULL COMMENT '登录时定位',
  `create_datetime` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tstd_user
-- ----------------------------
DROP TABLE IF EXISTS `tstd_user`;
CREATE TABLE `tstd_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `union_id` varchar(32) DEFAULT NULL COMMENT '开放平台和公众平台唯一号',
  `h5_open_id` varchar(32) DEFAULT NULL COMMENT '微信h5编号',
  `login_name` varchar(64) DEFAULT NULL COMMENT '登陆名',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `kind` char(1) DEFAULT NULL COMMENT '用户类型（C 普通用户，M 机器人，D 渠道商）',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `login_pwd` varchar(32) DEFAULT NULL COMMENT '登陆密码',
  `login_pwd_strength` char(1) DEFAULT NULL COMMENT '登陆密码强度',
  `level` varchar(4) DEFAULT NULL COMMENT '用户等级',
  `user_referee` varchar(32) DEFAULT NULL COMMENT '推荐人',
  `id_kind` char(1) DEFAULT NULL COMMENT '证件类型',
  `id_no` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `real_name` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `id_face` varchar(255) DEFAULT NULL COMMENT '证件照正面',
  `id_oppo` varchar(255) DEFAULT NULL COMMENT '证件照背面',
  `id_hold` varchar(255) DEFAULT NULL COMMENT '证件照手持照',
  `trade_pwd` varchar(32) DEFAULT NULL COMMENT '安全密码',
  `trade_pwd_strength` char(1) DEFAULT NULL COMMENT '安全密码强度',
  `google_secret` varchar(64) DEFAULT NULL COMMENT '谷歌验证密钥',
  `status` varchar(2) DEFAULT NULL COMMENT '状态',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `area` varchar(255) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `longitude` varchar(255) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) DEFAULT NULL COMMENT '维度',
  `resp_area` varchar(255) DEFAULT 'NIULL' COMMENT '负责区域',
  `trade_rate` decimal(8,4) DEFAULT NULL COMMENT '交易手续费',
  `create_datetime` datetime DEFAULT NULL COMMENT '注册时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tstd_user_ext
-- ----------------------------
DROP TABLE IF EXISTS `tstd_user_ext`;
CREATE TABLE `tstd_user_ext` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `gender` char(1) DEFAULT NULL COMMENT '性别(1 男 0 女)',
  `introduce` varchar(255) DEFAULT NULL COMMENT '自我介绍',
  `birthday` varchar(16) DEFAULT NULL COMMENT '生日',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `driver_li` varchar(255) DEFAULT NULL COMMENT '驾驶照',
  `passport` varchar(255) DEFAULT NULL COMMENT '护照',
  `diploma` varchar(4) DEFAULT NULL COMMENT '学位',
  `occupation` varchar(64) DEFAULT NULL COMMENT '职业',
  `grad_datetime` datetime DEFAULT NULL COMMENT '毕业时间',
  `work_time` varchar(4) DEFAULT NULL COMMENT '工作年限',
  `pdf` varchar(255) DEFAULT NULL COMMENT '用户资料',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tstd_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `tstd_withdraw`;
CREATE TABLE `tstd_withdraw` (
  `code` varchar(32) NOT NULL COMMENT '订单编号',
  `account_number` varchar(32) DEFAULT NULL COMMENT '针对账号',
  `account_name` varchar(64) DEFAULT NULL COMMENT '针对户名（手机号或其他',
  `type` varchar(4) DEFAULT NULL COMMENT '账户类型',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '取现金额',
  `currency` varchar(8) DEFAULT NULL COMMENT ' 取现币种',
  `fee` decimal(64,0) DEFAULT NULL COMMENT '取现手续费',
  `pay_fee` decimal(64,0) DEFAULT NULL COMMENT '取现转账费',
  `channel_type` varchar(32) DEFAULT NULL COMMENT '支付渠道',
  `channel_bank` varchar(32) DEFAULT NULL COMMENT '渠道银行代号',
  `pay_card_info` varchar(255) DEFAULT NULL COMMENT '支付渠道账号信息',
  `subbranch` varchar(64) DEFAULT NULL COMMENT '开户支行',
  `pay_card_no` varchar(32) DEFAULT NULL COMMENT ' 支付渠道账号（如银行卡号）',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（待审核/审核不通过/审核通过待支付/支付成功/支付失败）',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '申请人',
  `apply_note` varchar(255) DEFAULT NULL COMMENT '申请说明',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `approve_user` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_note` varchar(255) DEFAULT NULL COMMENT '审核说明',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `pay_user` varchar(32) DEFAULT NULL COMMENT '支付回录人',
  `pay_note` varchar(255) DEFAULT NULL COMMENT '支付回录说明',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号（信息流代表）',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付回录时间',
  `channel_order` varchar(32) DEFAULT NULL COMMENT '支付渠道的订单编号（支付渠道代表）',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='取现';

-- ----------------------------
-- Table structure for tsys_channel_bank
-- ----------------------------
DROP TABLE IF EXISTS `tsys_channel_bank`;
CREATE TABLE `tsys_channel_bank` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '编号（自增长）',
  `bank_code` varchar(32) DEFAULT NULL COMMENT '银行编号',
  `bank_name` varchar(32) DEFAULT NULL COMMENT '银行名称',
  `channel_type` varchar(4) DEFAULT NULL COMMENT '渠道类型',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（启用/不启用）',
  `channel_bank` varchar(32) DEFAULT NULL COMMENT '渠道给银行的代号',
  `max_order` bigint(32) DEFAULT NULL COMMENT '笔数限制',
  `order_amount` bigint(32) DEFAULT NULL COMMENT '单笔限额',
  `day_amount` bigint(32) DEFAULT NULL COMMENT '每日限额',
  `month_amount` bigint(32) DEFAULT NULL COMMENT '每月限额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tsys_cnavigate
-- ----------------------------
DROP TABLE IF EXISTS `tsys_cnavigate`;
CREATE TABLE `tsys_cnavigate` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `url` varchar(255) DEFAULT NULL COMMENT '访问Url',
  `pic` varchar(255) DEFAULT NULL COMMENT '图片',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(1 显示 0 不显示)',
  `location` varchar(32) DEFAULT NULL COMMENT '位置',
  `order_no` int(11) DEFAULT NULL COMMENT '相对位置编号',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tsys_config
-- ----------------------------
DROP TABLE IF EXISTS `tsys_config`;
CREATE TABLE `tsys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `ckey` varchar(255) DEFAULT NULL COMMENT 'key',
  `cvalue` text COMMENT 'value',
  `updater` varchar(32) DEFAULT NULL COMMENT '最近修改人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最近修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tsys_dict
-- ----------------------------
DROP TABLE IF EXISTS `tsys_dict`;
CREATE TABLE `tsys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `parent_key` varchar(32) DEFAULT NULL COMMENT '父亲key',
  `dkey` varchar(255) DEFAULT NULL COMMENT 'key',
  `dvalue` varchar(255) DEFAULT NULL COMMENT 'value',
  `updater` varchar(32) DEFAULT NULL COMMENT '最近修改人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最近修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tsys_menu
-- ----------------------------
DROP TABLE IF EXISTS `tsys_menu`;
CREATE TABLE `tsys_menu` (
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '编号',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父亲key',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `type` varchar(2) DEFAULT NULL COMMENT '类型',
  `url` varchar(255) DEFAULT NULL COMMENT 'url',
  `order_no` int(11) DEFAULT NULL COMMENT '次序',
  `updater` varchar(32) DEFAULT NULL COMMENT '最近修改人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最近修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tsys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `tsys_menu_role`;
CREATE TABLE `tsys_menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `menu_code` varchar(32) DEFAULT NULL COMMENT '菜单编号',
  `updater` varchar(32) DEFAULT NULL COMMENT '最近修改人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最近修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tsys_role
-- ----------------------------
DROP TABLE IF EXISTS `tsys_role`;
CREATE TABLE `tsys_role` (
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `updater` varchar(32) DEFAULT NULL COMMENT '最近修改人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最近修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tsys_user
-- ----------------------------
DROP TABLE IF EXISTS `tsys_user`;
CREATE TABLE `tsys_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `department_code` varchar(32) DEFAULT NULL COMMENT '部门编号(当有部门表时才有此字段）',
  `real_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
  `login_name` varchar(64) DEFAULT NULL COMMENT '登录名',
  `login_pwd` varchar(256) DEFAULT NULL COMMENT '登录密码',
  `login_pwd_strength` char(1) DEFAULT NULL COMMENT '登录密码强度',
  `create_datetime` datetime DEFAULT NULL COMMENT '注册时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
