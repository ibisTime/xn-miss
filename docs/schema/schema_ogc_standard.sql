/*
 Navicat MySQL Data Transfer

 Source Server         : 47.96.161.183
 Source Server Version : 50633
 Source Host           : 47.96.161.183
 Source Database       : ogc_standard

 Target Server Version : 50633
 File Encoding         : utf-8

 Date: 09/12/2018 15:51:49 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tcoin_accept_order`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_accept_order`;
CREATE TABLE `tcoin_accept_order` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `order_uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单序号',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(0买入/1卖出)',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `accept_user_id` varchar(32) DEFAULT NULL COMMENT '承兑商',
  `trade_currency` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `trade_coin` varchar(32) DEFAULT NULL COMMENT '交易数字货币',
  `trade_price` decimal(32,3) DEFAULT NULL COMMENT '交易单价',
  `count` decimal(64,0) DEFAULT NULL COMMENT '交易数量',
  `trade_amount` decimal(32,3) DEFAULT NULL COMMENT '交易总额',
  `fee` decimal(64,0) DEFAULT NULL COMMENT '手续费',
  `invalid_datetime` datetime DEFAULT NULL COMMENT '支付超时时间',
  `receive_type` varchar(32) DEFAULT NULL COMMENT '收款方式',
  `receive_info` varchar(255) DEFAULT NULL COMMENT '收款信息',
  `receive_bank` varchar(255) DEFAULT NULL COMMENT '收款银行',
  `receive_card_no` varchar(32) DEFAULT NULL COMMENT '收款卡号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(0=待支付 1=待确认 2=已完成 3=已取消)',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `mark_datetime` datetime DEFAULT NULL COMMENT '标记打款时间',
  `mark_note` varchar(255) DEFAULT NULL COMMENT '标记打款说明',
  `updater` varchar(32) DEFAULT NULL COMMENT '最后更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`),
  UNIQUE KEY `order_uid_UNIQUE` (`order_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8 COMMENT='承兑交易订单';

-- ----------------------------
--  Table structure for `tcoin_ads`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_ads`;
CREATE TABLE `tcoin_ads` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `trade_type` varchar(32) NOT NULL COMMENT '广告类型（0购买 1出售）',
  `user_id` varchar(32) NOT NULL COMMENT '发布用户编号',
  `trade_currency` varchar(8) NOT NULL COMMENT '交易币种',
  `trade_coin` varchar(8) NOT NULL COMMENT '数字货币类型',
  `only_trust` varchar(2) NOT NULL COMMENT '是否只有信任的人可以交易',
  `premium_rate` decimal(6,4) NOT NULL COMMENT '溢价率',
  `total_count` decimal(64,0) NOT NULL COMMENT '广告的交易总额',
  `left_count` decimal(64,0) NOT NULL COMMENT '剩余可交易',
  `market_price` decimal(32,3) NOT NULL COMMENT '行情价格',
  `protect_price` decimal(32,3) NOT NULL COMMENT '保护单价',
  `true_price` decimal(32,3) NOT NULL COMMENT '溢价率换算后的真实价格',
  `min_trade` decimal(32,3) DEFAULT NULL COMMENT '单笔最小交易额',
  `max_trade` decimal(32,3) DEFAULT NULL COMMENT '单笔最大交易额',
  `pay_type` varchar(32) NOT NULL COMMENT '付款方式',
  `pay_limit` int(11) DEFAULT NULL COMMENT '付款超时时间（分钟）',
  `status` varchar(4) NOT NULL COMMENT '状态 0=草稿、1=上架中、2=下架',
  `leave_message` text CHARACTER SET utf8mb4 NOT NULL COMMENT '广告留言',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  `fee_rate` decimal(8,4) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tcoin_ads_display_time`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_ads_display_time`;
CREATE TABLE `tcoin_ads_display_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ads_code` varchar(32) NOT NULL COMMENT '编号',
  `week` char(1) NOT NULL COMMENT '周几',
  `start_time` int(11) NOT NULL COMMENT '开始时间',
  `end_time` int(11) NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ads_week` (`ads_code`,`week`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tcoin_arbitrate`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_arbitrate`;
CREATE TABLE `tcoin_arbitrate` (
  `code` varchar(32) NOT NULL COMMENT '工单编号',
  `trade_order_code` varchar(32) DEFAULT NULL COMMENT '交易订单编号',
  `yuangao` varchar(32) DEFAULT NULL COMMENT '原告',
  `beigao` varchar(32) DEFAULT NULL COMMENT '被告',
  `reason` text CHARACTER SET utf8mb4 COMMENT '仲裁理由',
  `attach` text CHARACTER SET utf8mb4 COMMENT '附件',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `result` text COMMENT '处理结果',
  `updater` varchar(45) DEFAULT NULL COMMENT '处理人',
  `update_datetime` datetime DEFAULT NULL COMMENT '处理时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tcoin_btc_maddress`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_btc_maddress`;
CREATE TABLE `tcoin_btc_maddress` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) DEFAULT NULL COMMENT '比特币地址',
  `privatekey` varchar(255) DEFAULT NULL COMMENT '私钥',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(0正常 1广播使用中 2=已弃用)',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='btc散取地址';

-- ----------------------------
--  Table structure for `tcoin_btc_transaction`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_btc_transaction`;
CREATE TABLE `tcoin_btc_transaction` (
  `txid` varchar(255) NOT NULL COMMENT '交易ID',
  `version` varchar(32) DEFAULT NULL COMMENT '版本',
  `blockheight` bigint(32) DEFAULT NULL COMMENT '区块高度',
  `coinbase` varchar(32) DEFAULT NULL COMMENT '是否是挖矿奖励',
  `blockhash` varchar(255) DEFAULT NULL COMMENT '区块hash',
  `blocktime` datetime DEFAULT NULL COMMENT '区块生成时间',
  `valuein` decimal(64,0) DEFAULT NULL COMMENT '输入总额',
  `valueout` decimal(64,0) DEFAULT NULL COMMENT '输出总额',
  `fees` decimal(64,0) DEFAULT NULL COMMENT '矿工费',
  `vin` text COMMENT '输入列表',
  `vout` text COMMENT '输出列表',
  `ref_no` varchar(32) DEFAULT NULL COMMENT '关联订单号',
  PRIMARY KEY (`txid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tcoin_btc_waddress`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_btc_waddress`;
CREATE TABLE `tcoin_btc_waddress` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) DEFAULT NULL COMMENT '比特币地址',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(0正常 1广播使用中 2=已弃用)',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='btc归集地址';

-- ----------------------------
--  Table structure for `tcoin_btc_xaddress`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_btc_xaddress`;
CREATE TABLE `tcoin_btc_xaddress` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) DEFAULT NULL COMMENT '比特币地址',
  `privatekey` varchar(255) DEFAULT NULL COMMENT '私钥',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tcoin_currency_rate`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_currency_rate`;
CREATE TABLE `tcoin_currency_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `currency` varchar(10) NOT NULL COMMENT '币种',
  `refer_currency` varchar(10) NOT NULL COMMENT '参照币种 为CNY',
  `origin` varchar(32) NOT NULL COMMENT '汇率来源',
  `rate` decimal(8,4) NOT NULL COMMENT '汇率',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法币汇率';

-- ----------------------------
--  Table structure for `tcoin_eth_maddress`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_eth_maddress`;
CREATE TABLE `tcoin_eth_maddress` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) DEFAULT NULL COMMENT '以太坊地址',
  `keystore_pwd` varchar(255) NOT NULL COMMENT 'keystore密码',
  `keystore_name` text COMMENT 'keystore文件名',
  `keystore_content` text COMMENT 'keystore文件内容',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `abandon_datetime` datetime DEFAULT NULL COMMENT '弃用时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ETH平台散取地址';

DROP TABLE IF EXISTS `tcoin_eth_saddress`;
CREATE TABLE `tcoin_eth_saddress` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) DEFAULT NULL COMMENT '以太坊地址',
  `keystore_pwd` varchar(255) NOT NULL COMMENT 'keystore密码',
  `keystore_name` text COMMENT 'keystore文件名',
  `keystore_content` text COMMENT 'keystore文件内容',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `abandon_datetime` datetime DEFAULT NULL COMMENT '弃用时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ETH平台散取地址';


-- ----------------------------
--  Table structure for `tcoin_eth_transaction`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_eth_transaction`;
CREATE TABLE `tcoin_eth_transaction` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `hash` varchar(255) NOT NULL COMMENT '交易哈希',
  `nonce` bigint(32) NOT NULL COMMENT '交易次数',
  `block_hash` varchar(255) NOT NULL COMMENT '区块哈希',
  `block_number` bigint(32) NOT NULL COMMENT '区块号',
  `block_create_datetime` datetime NOT NULL COMMENT '区块形成时间',
  `transaction_index` bigint(32) NOT NULL COMMENT '交易index',
  `from` varchar(255) NOT NULL COMMENT '转出地址',
  `to` varchar(255) NOT NULL COMMENT '转入地址',
  `value` decimal(64,0) NOT NULL COMMENT '交易额',
  `sync_datetime` datetime NOT NULL COMMENT '同步时间',
  `gas_price` decimal(64,0) NOT NULL COMMENT 'gas价格',
  `gas_limit` bigint(20) NOT NULL COMMENT 'gas最大使用限制',
  `gas_used` bigint(20) NOT NULL COMMENT 'gas实际使用量',
  `gas_fee` decimal(64,0) NOT NULL COMMENT 'gas手续费',
  `input` text COMMENT 'input 输入',
  `public_key` text COMMENT 'publicKey',
  `raw` text COMMENT 'raw',
  `r` text COMMENT 'r',
  `s` text COMMENT 's',
  PRIMARY KEY (`id`),
  UNIQUE KEY `hash_UNIQUE` (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ETH交易记录';

DROP TABLE IF EXISTS `tcoin_token_event`;
CREATE TABLE `tcoin_token_event` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `hash` varchar(255) NOT NULL COMMENT '交易哈希',
  `token_from` varchar(255) DEFAULT NULL COMMENT 'token币发起地址',
  `token_to` varchar(255) DEFAULT NULL COMMENT 'token币接收地址',
  `token_value` decimal(64,0) DEFAULT NULL COMMENT 'token币数量',
  `token_log_index` bigint(32) DEFAULT NULL COMMENT 'event_log_index',
  `symbol` varchar(32) NOT NULL COMMENT '币种符号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ETHtoken交易event';

-- ----------------------------
--  Table structure for `tcoin_eth_waddress`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_eth_waddress`;
CREATE TABLE `tcoin_eth_waddress` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) DEFAULT NULL COMMENT '以太坊地址',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `abandon_datetime` datetime DEFAULT NULL COMMENT '弃用时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ETH平台归集地址';

-- ----------------------------
--  Table structure for `tcoin_eth_xaddress`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_eth_xaddress`;
CREATE TABLE `tcoin_eth_xaddress` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) NOT NULL COMMENT '以太坊地址',
  `keystore_pwd` varchar(255) NOT NULL COMMENT 'keystore密码',
  `keystore_name` text COMMENT 'keystore文件名',
  `keystore_content` text COMMENT 'keystore文件内容',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ETH用户分发地址';

-- ----------------------------
--  Table structure for `tcoin_market`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_market`;
CREATE TABLE `tcoin_market` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `symbol` varchar(10) NOT NULL COMMENT '数字货币',
  `refer_currency` varchar(10) NOT NULL COMMENT '参照法币币种',
  `origin` varchar(32) NOT NULL COMMENT '行情来源',
  `last_price` decimal(18,4) NOT NULL COMMENT '最新成交价格',
  `bid` decimal(18,4) DEFAULT NULL COMMENT '买家期望价格',
  `ask` decimal(18,4) DEFAULT NULL COMMENT '卖家期望价格',
  `mid` decimal(18,4) NOT NULL COMMENT '中间价格',
  `low` decimal(18,4) NOT NULL COMMENT '最低价格',
  `high` decimal(18,4) NOT NULL COMMENT '最高价格',
  `volume` varchar(32) DEFAULT NULL COMMENT '交易量',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  `coinmarketcap_id` int(10) NOT NULL COMMENT 'coinmarketcap中的币种id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `origin` (`origin`,`symbol`,`refer_currency`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种行情';

-- ----------------------------
--  Table structure for `tcoin_personal_address`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_personal_address`;
CREATE TABLE `tcoin_personal_address` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `symbol` varchar(32) DEFAULT NULL COMMENT '币种',
  `address` varchar(255) DEFAULT NULL COMMENT '提现地址',
  `label` varchar(64) DEFAULT NULL COMMENT '标签',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '最后操作人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最后一次更时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户取现地址';

-- ----------------------------
--  Table structure for `tcoin_token_event`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_token_event`;
CREATE TABLE `tcoin_token_event` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `hash` varchar(255) NOT NULL COMMENT '交易哈希',
  `token_from` varchar(255) DEFAULT NULL COMMENT 'token币发起地址',
  `token_to` varchar(255) DEFAULT NULL COMMENT 'token币接收地址',
  `token_value` decimal(64,0) DEFAULT NULL COMMENT 'token币数量',
  `token_log_index` bigint(32) DEFAULT NULL COMMENT 'event_log_index',
  `symbol` varchar(32) NOT NULL COMMENT '币种符号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ETHtoken交易event';

-- ----------------------------
--  Table structure for `tcoin_trade_order`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_trade_order`;
CREATE TABLE `tcoin_trade_order` (
  `code` varchar(32) NOT NULL COMMENT '订单编号',
  `type` varchar(32) DEFAULT NULL COMMENT '订单类型',
  `ads_code` varchar(32) DEFAULT NULL COMMENT '广告编号',
  `buy_user` varchar(32) DEFAULT NULL COMMENT '买方',
  `sell_user` varchar(32) DEFAULT NULL COMMENT '卖方',
  `leave_message` text CHARACTER SET utf8mb4 COMMENT '广告留言',
  `trade_currency` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `trade_coin` varchar(32) DEFAULT NULL COMMENT '交易数字货币类型',
  `trade_price` decimal(32,3) DEFAULT NULL COMMENT '交易单价',
  `trade_amount` decimal(32,3) DEFAULT NULL COMMENT '交易总额',
  `fee` decimal(64,0) DEFAULT NULL COMMENT '手续费',
  `count` decimal(64,0) DEFAULT NULL COMMENT '交易数字货币数量',
  `pay_type` varchar(32) DEFAULT NULL COMMENT '付款方式',
  `invalid_datetime` datetime DEFAULT NULL COMMENT '支付超时时间',
  `status` varchar(4) DEFAULT NULL COMMENT '订单状态',
  `sb_comment` varchar(32) DEFAULT NULL COMMENT '卖方对买方的评价',
  `bs_comment` varchar(32) DEFAULT NULL COMMENT '买方对卖方的评价',
  `mark_datetime` datetime DEFAULT NULL COMMENT '标记打款时间',
  `release_datetime` datetime DEFAULT NULL COMMENT '币释放时间',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '最后更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tforum_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_comment`;
CREATE TABLE `tforum_comment` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(32) DEFAULT NULL COMMENT '类型（1=帖子 2=帖子评论）',
  `content` text COMMENT '评论内容',
  `user_id` varchar(32) DEFAULT NULL COMMENT '评论人',
  `comment_datetime` datetime DEFAULT NULL COMMENT '评论时间',
  `point_count` int(11) DEFAULT NULL COMMENT '点赞量',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父级编号',
  `parent_user_id` varchar(32) DEFAULT NULL COMMENT '父级评论编号',
  `object_code` varchar(32) DEFAULT NULL COMMENT '对象编号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `approver` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`) COMMENT '评论表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `tforum_interact`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_interact`;
CREATE TABLE `tforum_interact` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) DEFAULT NULL COMMENT '类型(1=点赞 2=收藏 3=浏览)',
  `object_type` varchar(32) DEFAULT NULL COMMENT '对象类型',
  `object_code` varchar(32) DEFAULT NULL COMMENT '对象编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`) COMMENT '点赞收藏表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tforum_keyword`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_keyword`;
CREATE TABLE `tforum_keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号（自增长）',
  `word` varchar(255) DEFAULT NULL COMMENT '词语',
  `level` varchar(4) DEFAULT NULL COMMENT '作用等级',
  `reaction` varchar(4) DEFAULT NULL COMMENT '反应(1 直接拦截/2 替换**/3 审核)',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) COMMENT '关键字'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `tforum_match`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_match`;
CREATE TABLE `tforum_match` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `start_datetime` datetime DEFAULT NULL COMMENT '开始时间',
  `end_datetime` datetime DEFAULT NULL COMMENT '结束时间',
  `order_no` int(11) DEFAULT NULL COMMENT '序号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（1待发布，2已发布，3已开始，4已过期）',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`) COMMENT '赛事表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tforum_match_apply`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_match_apply`;
CREATE TABLE `tforum_match_apply` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `match_code` varchar(32) DEFAULT NULL COMMENT '赛事编号',
  `team_name` varchar(255) DEFAULT NULL COMMENT ' 战队名称',
  `logo` varchar(255) DEFAULT NULL COMMENT 'logo',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（1待审核，2审核通过，3审核不通过）',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '申请人编号',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `approver` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`) COMMENT '参赛申请表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tforum_post`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_post`;
CREATE TABLE `tforum_post` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `content` text COMMENT '内容',
  `user_id` varchar(32) DEFAULT NULL COMMENT '发布人',
  `publish_datetime` datetime DEFAULT NULL COMMENT '发布时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `location` varchar(4) DEFAULT NULL COMMENT '位置(0普通 1热门)',
  `plate_code` varchar(32) DEFAULT NULL COMMENT '板块编号',
  `comment_count` int(11) DEFAULT NULL COMMENT '评论数',
  `point_count` int(11) DEFAULT NULL COMMENT '点赞数',
  `read_count` int(11) DEFAULT NULL COMMENT '阅读数',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`) COMMENT '帖子'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `tforum_team`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_team`;
CREATE TABLE `tforum_team` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `match_code` varchar(32) DEFAULT NULL COMMENT '赛事编号',
  `name` varchar(255) DEFAULT NULL COMMENT ' 战队名称',
  `logo` varchar(255) DEFAULT NULL COMMENT 'logo',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `captain` varchar(32) DEFAULT NULL COMMENT '队长编号',
  `weight` decimal(18,8) DEFAULT NULL COMMENT '权重',
  `location` varchar(4) DEFAULT NULL COMMENT '位置',
  `order_no` int(11) DEFAULT NULL COMMENT '序号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(待开始，参赛中，已结束)',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `post_count` int(11) DEFAULT NULL COMMENT '帖子数',
  `member_count` int(11) DEFAULT NULL COMMENT '队员人数',
  `benefit_rate` decimal(18,8) DEFAULT NULL COMMENT '总收益率',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`) COMMENT '战队表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tforum_team_member_apply`
-- ----------------------------
DROP TABLE IF EXISTS `tforum_team_member_apply`;
CREATE TABLE `tforum_team_member_apply` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `team_code` varchar(32) DEFAULT NULL COMMENT '战队编号',
  `apply_user` varchar(32) DEFAULT NULL COMMENT ' 申请人',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（1待审核，2审核通过，3审核不通过）',
  `approver` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`) COMMENT '战队成员申请表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tcoin_coin`
-- ----------------------------
DROP TABLE IF EXISTS `tcoin_coin`;
CREATE TABLE `tcoin_coin` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `symbol` varchar(64) NOT NULL DEFAULT '' COMMENT '英文简称',
  `ename` varchar(64) DEFAULT NULL COMMENT '英文名称',
  `cname` varchar(64) DEFAULT NULL COMMENT '中文名称',
  `type` varchar(32) NOT NULL COMMENT '分类 0-以太币 1-比特币 2—万维币 0T-以太token币 2T-万维token币',
  `unit` int(11) NOT NULL COMMENT '单位',
  `icon` varchar(255) NOT NULL COMMENT '币种图标',
  `pic1` varchar(255) DEFAULT NULL COMMENT '图标1',
  `pic2` varchar(255) DEFAULT NULL COMMENT '图标2',
  `pic3` varchar(255) DEFAULT NULL COMMENT '图标3',
  `order_no` int(11) NOT NULL COMMENT 'UI序号',
  `introduction` varchar(255) DEFAULT NULL COMMENT '介绍',
  `total_supply` varchar(255) DEFAULT NULL COMMENT '流通量',
  `total_supply_market` varchar(255) DEFAULT NULL COMMENT '流通市值',
  `max_supply` varchar(255) DEFAULT NULL COMMENT '发行总量',
  `max_supply_market` varchar(255) DEFAULT NULL COMMENT '发行市值',
  `rank` varchar(255) DEFAULT NULL COMMENT '市值排名',
  `white_paper` varchar(255) DEFAULT NULL COMMENT '白皮书',
  `web_url` varchar(255) DEFAULT NULL COMMENT '官网',
  `block_url` varchar(255) DEFAULT NULL COMMENT '区块查询',
  `ico_datetime` varchar(255) DEFAULT NULL COMMENT 'ico时间',
  `collect_start` decimal(64,0) NOT NULL COMMENT '归集阀值',
  `withdraw_fee` decimal(64,0) NOT NULL COMMENT '取现手续费',
  `contract_address` varchar(255) DEFAULT NULL COMMENT '合约地址',
  `contract_abi` text COMMENT '合约ABI',
  `status` varchar(8) NOT NULL COMMENT '状态',
  `updater` varchar(32) NOT NULL COMMENT '最后操作人',
  `update_datetime` datetime NOT NULL COMMENT '最后操作时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='币种';


-- ----------------------------
--  Table structure for `tmk_exchange`
-- ----------------------------
DROP TABLE IF EXISTS `tmk_exchange`;
CREATE TABLE `tmk_exchange` (
  `id` bigint(11) NOT NULL COMMENT '序号',
  `ename` varchar(255) NOT NULL COMMENT '交易所英文名',
  `cname` varchar(255) NOT NULL COMMENT '交易所名称',
  `introduce` text COMMENT '简介',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `order_no` bigint(11) DEFAULT NULL COMMENT 'UI序号',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易所';

-- ----------------------------
--  Table structure for `tmk_exchange_pair`
-- ----------------------------
DROP TABLE IF EXISTS `tmk_exchange_pair`;
CREATE TABLE `tmk_exchange_pair` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `exchange_ename` varchar(255) DEFAULT NULL COMMENT '交易所英文名',
  `symbol` varchar(32) DEFAULT NULL COMMENT '基础币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  `pair` varchar(32) DEFAULT NULL COMMENT '交易对',
  `price` decimal(64,18) DEFAULT NULL COMMENT '最新价格',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易对';

-- ----------------------------
--  Table structure for `tsimu_attention`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_attention`;
CREATE TABLE `tsimu_attention` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(32) NOT NULL COMMENT '类型(1-关注/0-提醒)',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `group_code` varchar(32) NOT NULL COMMENT '组合编号',
  `group_user_id` varchar(32) NOT NULL COMMENT '组合用户编号',
  `create_datetime` datetime NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注表';

-- ----------------------------
--  Table structure for `tsimu_day_benefit`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_day_benefit`;
CREATE TABLE `tsimu_day_benefit` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `group_code` varchar(32) NOT NULL COMMENT '组合编号',
  `yesterday_assets` decimal(64,18) DEFAULT NULL COMMENT '昨日总资产',
  `today_assets` decimal(64,18) DEFAULT NULL COMMENT '今日总资产',
  `benefit` decimal(64,18) DEFAULT NULL COMMENT '收益',
  `benefit_rate` decimal(18,4) DEFAULT NULL COMMENT '收益率',
  `create_datetime` datetime NOT NULL COMMENT '产生日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日收益统计表';

-- ----------------------------
--  Table structure for `tsimu_group`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_group`;
CREATE TABLE `tsimu_group` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `match_code` varchar(32) NOT NULL COMMENT '赛事编号',
  `team_code` varchar(32) NOT NULL COMMENT '战队编号',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `symbol` varchar(32) NOT NULL COMMENT '代币符号',
  `init_amount` decimal(64,0) NOT NULL COMMENT '初始金额',
  `balance` decimal(64,0) NOT NULL COMMENT ' 余额',
  `total_assets` decimal(64,0) NOT NULL COMMENT '总资产',
  `total_benefit` decimal(64,0) DEFAULT NULL COMMENT '总收益',
  `day_benefit` decimal(64,0) DEFAULT NULL COMMENT '今日收益',
  `week_benefit` decimal(64,0) DEFAULT NULL COMMENT '周收益',
  `month_benefit` decimal(64,0) DEFAULT NULL COMMENT '月收益',
  `order_no` bigint(32) NOT NULL COMMENT '排行',
  `follow_number` bigint(32) DEFAULT NULL COMMENT '关注人数',
  `status` varchar(32) NOT NULL COMMENT '状态（1-进行中，0-已结束）',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组合表';

-- ----------------------------
--  Table structure for `tsimu_group_coin`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_group_coin`;
CREATE TABLE `tsimu_group_coin` (
  `account_number` varchar(32) NOT NULL COMMENT '账户编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `group_code` varchar(32) NOT NULL COMMENT '组合编号',
  `symbol` varchar(32) DEFAULT NULL COMMENT '代币符号',
  `count` decimal(64,18) DEFAULT NULL COMMENT '数量',
  `frozen_count` decimal(64,18) DEFAULT NULL COMMENT '冻结数量',
  `assets` decimal(64,18) DEFAULT NULL COMMENT '币种资产',
  `rate` decimal(18,4) DEFAULT NULL COMMENT '占比',
  `last_order` varchar(32) DEFAULT NULL COMMENT '最近一次变动对应的流水编号',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='币种配置账户表';

-- ----------------------------
--  Table structure for `tsimu_group_coin_jour`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_group_coin_jour`;
CREATE TABLE `tsimu_group_coin_jour` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `ref_no` varchar(32) DEFAULT NULL COMMENT '参考订单号',
  `account_number` varchar(32) DEFAULT NULL COMMENT '账户编号',
  `trans_amount` bigint(32) DEFAULT NULL COMMENT '变动金额',
  `group_code` varchar(32) NOT NULL COMMENT '组合编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `symbol` varchar(8) DEFAULT NULL COMMENT '币种',
  `biz_type` varchar(32) DEFAULT NULL COMMENT '业务类型',
  `biz_note` varchar(255) DEFAULT NULL COMMENT '业务类型',
  `pre_amount` bigint(32) DEFAULT NULL COMMENT '变动前金额',
  `post_amount` bigint(32) DEFAULT NULL COMMENT '变动后金额',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='币种配置账户流水表';

-- ----------------------------
--  Table structure for `tsimu_handicap`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_handicap`;
CREATE TABLE `tsimu_handicap` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `order_code` varchar(32) DEFAULT NULL COMMENT '委托单',
  `price` decimal(64,8) DEFAULT NULL COMMENT '价格',
  `count` decimal(64,8) DEFAULT NULL COMMENT '数量',
  `direction` varchar(32) DEFAULT NULL COMMENT '买卖方向',
  `symbol` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='盘口';

-- ----------------------------
--  Table structure for `tsimu_kline`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_kline`;
CREATE TABLE `tsimu_kline` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `symbol` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  `period` varchar(32) DEFAULT NULL COMMENT 'K线类型',
  `volume` decimal(64,8) DEFAULT NULL COMMENT '成交量',
  `quantity` decimal(64,8) DEFAULT NULL COMMENT '成交笔数',
  `amount` decimal(64,8) DEFAULT NULL COMMENT '成交额',
  `open` decimal(64,8) DEFAULT NULL COMMENT '开盘价',
  `close` decimal(64,8) DEFAULT NULL COMMENT '收盘价',
  `high` decimal(64,8) DEFAULT NULL COMMENT '最高价',
  `low` decimal(64,8) DEFAULT NULL COMMENT '最低价',
  `create_datetime` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行情K线';

-- ----------------------------
--  Table structure for `tsimu_match_result`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_match_result`;
CREATE TABLE `tsimu_match_result` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '撮合编号',
  `buy_order_code` varchar(32) DEFAULT NULL COMMENT '买单成交单编号',
  `sell_order_code` varchar(32) DEFAULT NULL COMMENT '卖单成交单编号',
  `symbol` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  `buy_user_id` varchar(32) DEFAULT NULL COMMENT '买方用户编号',
  `sell_user_id` varchar(32) DEFAULT NULL COMMENT '卖方用户编号',
  `buy_amount` decimal(64,8) DEFAULT NULL COMMENT '交易数量',
  `sell_amount` decimal(64,8) DEFAULT NULL COMMENT '计价数量',
  `buy_fee` decimal(64,8) DEFAULT NULL COMMENT '买方手续费',
  `sell_fee` decimal(64,8) DEFAULT NULL COMMENT '卖方手续费',
  `create_datetime` datetime DEFAULT NULL COMMENT '撮合时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='撮合结果';

-- ----------------------------
--  Table structure for `tsimu_match_result_history`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_match_result_history`;
CREATE TABLE `tsimu_match_result_history` (
  `id` bigint(32) NOT NULL DEFAULT '0' COMMENT '撮合编号',
  `buy_order_code` varchar(32) DEFAULT NULL COMMENT '买单成交单编号',
  `sell_order_code` varchar(32) DEFAULT NULL COMMENT '卖单成交单编号',
  `symbol` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  `buy_user_id` varchar(32) DEFAULT NULL COMMENT '买方用户编号',
  `sell_user_id` varchar(32) DEFAULT NULL COMMENT '卖方用户编号',
  `buy_amount` decimal(64,8) DEFAULT NULL COMMENT '交易数量',
  `sell_amount` decimal(64,8) DEFAULT NULL COMMENT '计价数量',
  `buy_fee` decimal(64,8) DEFAULT NULL COMMENT '买方手续费',
  `sell_fee` decimal(64,8) DEFAULT NULL COMMENT '卖方手续费',
  `create_datetime` datetime DEFAULT NULL COMMENT '撮合时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='撮合结果历史';

-- ----------------------------
--  Table structure for `tsimu_order`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_order`;
CREATE TABLE `tsimu_order` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '委托用户编号',
  `symbol` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(0-市价，1-限价)',
  `direction` varchar(32) DEFAULT NULL COMMENT '买卖方向(0-买入，1-卖出)',
  `price` decimal(64,8) DEFAULT NULL COMMENT '委托价格',
  `total_count` decimal(64,8) DEFAULT NULL COMMENT '委托总数量',
  `total_amount` decimal(64,8) DEFAULT NULL COMMENT '委托总金额',
  `traded_count` decimal(64,8) DEFAULT NULL COMMENT '已成交数量',
  `traded_amount` decimal(64,8) DEFAULT NULL COMMENT '已成交总金额',
  `traded_fee` decimal(64,8) DEFAULT NULL COMMENT '已成交总手续费',
  `avg_price` decimal(64,8) DEFAULT NULL COMMENT '已成交均价',
  `last_traded_datetime` datetime DEFAULT NULL COMMENT '最后成交时间',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `cancel_datetime` datetime DEFAULT NULL COMMENT '撤销时间',
  `status` varchar(32) DEFAULT NULL COMMENT '状态(2-部分成交撤销，3-完全成交，4-已撤销)',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='委托单';

-- ----------------------------
--  Table structure for `tsimu_order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_order_detail`;
CREATE TABLE `tsimu_order_detail` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `order_code` varchar(32) DEFAULT NULL COMMENT '委托单编号',
  `match_result_id` bigint(32) DEFAULT NULL COMMENT '撮合编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `symbol` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  `traded_price` decimal(64,8) DEFAULT NULL COMMENT '成交价格',
  `traded_count` decimal(64,8) DEFAULT NULL COMMENT '成交数量',
  `traded_amount` decimal(64,8) DEFAULT NULL COMMENT '成交总金额',
  `traded_fee` decimal(64,8) DEFAULT NULL COMMENT '成交手续费',
  `create_datetime` datetime DEFAULT NULL COMMENT '成交时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成交单';

-- ----------------------------
--  Table structure for `tsimu_order_history`
-- ----------------------------
DROP TABLE IF EXISTS `tsimu_order_history`;
CREATE TABLE `tsimu_order_history` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '委托用户编号',
  `symbol` varchar(32) DEFAULT NULL COMMENT '交易币种',
  `to_symbol` varchar(32) DEFAULT NULL COMMENT '计价币种',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(0-市价，1-限价)',
  `direction` varchar(32) DEFAULT NULL COMMENT '买卖方向(0-买入，1-卖出)',
  `price` decimal(64,8) DEFAULT NULL COMMENT '委托价格',
  `total_count` decimal(64,8) DEFAULT NULL COMMENT '委托总数量',
  `total_amount` decimal(64,8) DEFAULT NULL COMMENT '委托总金额',
  `traded_count` decimal(64,8) DEFAULT NULL COMMENT '已成交数量',
  `traded_amount` decimal(64,8) DEFAULT NULL COMMENT '已成交总金额',
  `traded_fee` decimal(64,8) DEFAULT NULL COMMENT '已成交总手续费',
  `avg_price` decimal(64,8) DEFAULT NULL COMMENT '已成交均价',
  `last_traded_datetime` datetime DEFAULT NULL COMMENT '最后成交时间',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `cancel_datetime` datetime DEFAULT NULL COMMENT '撤销时间',
  `status` varchar(32) DEFAULT NULL COMMENT '状态(0-待成交，1-部分成交)',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='历史委托单';

-- ----------------------------
--  Table structure for `tstd_account`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_account`;
CREATE TABLE `tstd_account` (
  `account_number` varchar(32) NOT NULL COMMENT '账户编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `address` varchar(255) DEFAULT NULL COMMENT '区块链地址',
  `type` varchar(4) DEFAULT NULL COMMENT '类别（B端账号，C端账号，平台账号）',
  `status` varchar(2) DEFAULT NULL COMMENT '状态（正常/程序冻结/人工冻结）',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '余额',
  `frozen_amount` decimal(64,0) DEFAULT NULL COMMENT '冻结金额',
  `md5` varchar(32) DEFAULT NULL COMMENT 'MD5',
  `in_amount` decimal(64,0) DEFAULT '0' COMMENT '总充值金额（入金）',
  `out_amount` decimal(64,0) DEFAULT '0' COMMENT '总取现金额（出金）',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `last_order` varchar(32) DEFAULT NULL COMMENT '最近一次变动对应的流水编号',
  PRIMARY KEY (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户';

-- ----------------------------
--  Table structure for `tstd_auth_log`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_auth_log`;
CREATE TABLE `tstd_auth_log` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `type` varchar(4) DEFAULT NULL COMMENT '认证类型',
  `auth_arg1` varchar(255) DEFAULT NULL COMMENT '入参1',
  `auth_arg2` varchar(255) DEFAULT NULL COMMENT '入参2',
  `auth_arg3` varchar(255) DEFAULT NULL COMMENT '入参3',
  `status` varchar(4) DEFAULT NULL COMMENT '状态 0=待处理 1=认证通过 2=认证不通过',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '申请人',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `approve_user` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `result` varchar(255) DEFAULT NULL COMMENT '出参',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tstd_bankcard`
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
  `amount` bigint(32) DEFAULT NULL COMMENT '余额',
  `frozen_amount` bigint(32) DEFAULT NULL COMMENT '冻结金额',
  `md5` varchar(32) DEFAULT NULL COMMENT 'MD5',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `last_order` varchar(32) DEFAULT NULL COMMENT '最近一次变动对应的流水编号',
  `system_code` varchar(32) NOT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `tstd_blacklist`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_blacklist`;
CREATE TABLE `tstd_blacklist` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `type` varchar(4) DEFAULT NULL COMMENT '拉黑粒度',
  `status` varchar(4) DEFAULT NULL COMMENT '状态 0-已失效 1-已生效',
  `create_datetime` datetime DEFAULT NULL COMMENT '拉黑时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` varchar(45) DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tstd_charge`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_charge`;
CREATE TABLE `tstd_charge` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `account_number` varchar(32) DEFAULT NULL COMMENT '账户编号',
  `account_type` varchar(4) DEFAULT NULL COMMENT '账户类型',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '充值金额',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `biz_type` varchar(32) DEFAULT NULL COMMENT '关联业务类型',
  `biz_note` varchar(255) DEFAULT NULL COMMENT '关联业务备注',
  `biz_no` varchar(255) DEFAULT NULL COMMENT '关联订单号',
  `pay_card_info` varchar(255) DEFAULT NULL COMMENT '支付渠道账号信息',
  `pay_card_no` varchar(255) DEFAULT NULL COMMENT '支付渠道账号',
  `status` varchar(4) NOT NULL COMMENT '状态 1待支付 2支付失败 3支付成功',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '申请人',
  `apply_note` varchar(255) DEFAULT NULL COMMENT '申请说明',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `pay_user` varchar(32) DEFAULT NULL COMMENT '支付回录人',
  `pay_note` varchar(255) DEFAULT NULL COMMENT '支付渠道说明',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `channel_type` varchar(32) DEFAULT NULL COMMENT '支付渠道',
  `channel_order` varchar(255) DEFAULT NULL COMMENT '支付渠道单号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值订单';

-- ----------------------------
--  Table structure for `tstd_collect`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_collect`;
CREATE TABLE `tstd_collect` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `currency` varchar(32) DEFAULT NULL COMMENT '币种',
  `from_address` text COMMENT '被归集地址',
  `to_address` text COMMENT '归集地址',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '归集数量',
  `tx_hash` varchar(255) DEFAULT NULL COMMENT '交易hash',
  `tx_fee` decimal(64,0) DEFAULT NULL COMMENT '矿工费',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '发起时间',
  `confirm_datetime` datetime DEFAULT NULL COMMENT '网络记账时间',
  `finish_datetime` datetime DEFAULT NULL COMMENT '完成时间',
  `ref_no` varchar(32) DEFAULT NULL COMMENT '关联订单号',
  `coin_type` varchar(32) DEFAULT NULL COMMENT '币种类型',
  `pre_from` varchar(255) DEFAULT NULL COMMENT '矿工费补给地址',
  `pre_to` varchar(255) DEFAULT NULL COMMENT '矿工费收款地址',
  `pre_amount` decimal(64,0) DEFAULT NULL COMMENT '矿工费补给数量',
  `pre_tx_hash` varchar(255) DEFAULT NULL COMMENT '矿工费补给交易hash',
  `pre_tx_fee` decimal(64,0) DEFAULT NULL COMMENT '矿工费补给交易矿工费',
  `pre_create_datetime` datetime DEFAULT NULL COMMENT '矿工费补给发起时间',
  `pre_confirm_datetime` datetime DEFAULT NULL COMMENT '矿工费补给网络记账时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='归集订单';

-- ----------------------------
--  Table structure for `tstd_deposit`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_deposit`;
CREATE TABLE `tstd_deposit` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `currency` varchar(32) DEFAULT NULL COMMENT '币种',
  `from_address` text COMMENT '付款地址',
  `to_address` text COMMENT '收款地址',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '定存数量',
  `tx_hash` varchar(255) DEFAULT NULL COMMENT '交易hash',
  `tx_fee` decimal(64,0) DEFAULT NULL COMMENT '矿工费',
  `confirm_datetime` datetime DEFAULT NULL COMMENT '网络记账时间',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定存订单';

-- ----------------------------
--  Table structure for `tstd_divide`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_divide`;
CREATE TABLE `tstd_divide` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `divide_profit` decimal(64,0) DEFAULT NULL COMMENT '分红利润',
  `divide_amount` decimal(64,0) DEFAULT NULL COMMENT '分红总金额',
  `status` varchar(4) NOT NULL COMMENT '状态(0=待分红 1=已分红)',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  
  `divide_datetime` datetime DEFAULT NULL COMMENT '分红时间',
  `divide_user` varchar(32) DEFAULT NULL COMMENT '分红人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分红';

-- ----------------------------
--  Table structure for `tstd_divide_detail`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_divide_detail`;
CREATE TABLE `tstd_divide_detail` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种(X币)',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '当时余额',
  `divide_amount` decimal(64,0) DEFAULT NULL COMMENT '分红金额',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  `divide_datetime` datetime DEFAULT NULL COMMENT '分红时间',
  `divide_id` bigint(32) NOT NULL COMMENT '分红ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分红明细';

-- ----------------------------
--  Table structure for `tstd_award`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_award`;
CREATE TABLE `tstd_award` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `user_kind` varchar(32) NOT NULL COMMENT '用户种类',
  `currency` char(8) DEFAULT NULL COMMENT '币种(X币)',
  `count` decimal(64,0) DEFAULT NULL COMMENT '奖励数量',
  `order_count` decimal(64,0) DEFAULT NULL COMMENT '订单数量',
  `rate` decimal(18,8) DEFAULT NULL COMMENT '奖励比例',
  `ref_type` char(1) DEFAULT NULL COMMENT '参考类型(1=注册分佣 2=交易分佣)',
  `ref_code` varchar(32) DEFAULT NULL COMMENT '参考订单编号',
  `ref_note` varchar(255) DEFAULT NULL COMMENT '参考说明',
  `status` char(1) NOT NULL COMMENT '状态(0=待结算 1=已结算 2=不结算)',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `handle_datetime` datetime DEFAULT NULL COMMENT '处理时间',
  `handle_note` varchar(255) DEFAULT NULL COMMENT '处理说明',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖励';

-- ----------------------------
--  Table structure for `tstd_award_month`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_award_month`;
CREATE TABLE `tstd_award_month` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `unsettle_count` decimal(64,0) DEFAULT NULL COMMENT '未结算数量',
  `settle_count` decimal(64,0) DEFAULT NULL COMMENT '结算数量',
  `nosettle_count` decimal(64,0) DEFAULT NULL COMMENT '不结算数量',
  `next_unsettle_count` decimal(64,0) DEFAULT NULL COMMENT '下个月未结算数量',
  `current_count` decimal(64,0) DEFAULT NULL COMMENT '当月最后一次结算后余额',
  `start_date` datetime DEFAULT NULL COMMENT '开始结算日期',
  `end_date` datetime DEFAULT NULL COMMENT '结束结算日期',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `settle_datetime` datetime DEFAULT NULL COMMENT '结算时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖励结算';

-- ----------------------------
--  Table structure for `tstd_hlorder`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_hlorder`;
CREATE TABLE `tstd_hlorder` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `account_number` varchar(32) NOT NULL COMMENT '账户编号',
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
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红蓝订单';

-- ----------------------------
--  Table structure for `tstd_jour`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_jour`;
CREATE TABLE `tstd_jour` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(32) DEFAULT NULL COMMENT '流水类型（余额流水、冻结流水）',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `account_number` varchar(32) DEFAULT NULL COMMENT '账户编号',
  `account_type` varchar(4) DEFAULT NULL COMMENT '账户类型',
  `currency` varchar(8) DEFAULT NULL COMMENT '币种',
  `biz_type` varchar(32) DEFAULT NULL COMMENT '业务类型',
  `biz_note` varchar(255) DEFAULT NULL COMMENT '业务说明',
  `trans_amount` decimal(64,0) DEFAULT NULL COMMENT '变动金额',
  `pre_amount` decimal(64,0) DEFAULT NULL COMMENT '变动前金额',
  `post_amount` decimal(64,0) DEFAULT NULL COMMENT '变动后金额',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `channel_type` varchar(32) DEFAULT NULL COMMENT '支付渠道类型',
  `channel_order` varchar(255) DEFAULT NULL COMMENT '支付渠道单号',
  `ref_no` varchar(255) DEFAULT NULL COMMENT '系统内部参考订单号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `work_date` varchar(8) DEFAULT NULL COMMENT '拟对账时间',
  `check_user` varchar(32) DEFAULT NULL COMMENT '对账人',
  `check_note` varchar(255) DEFAULT NULL COMMENT '对账说明',
  `check_datetime` datetime DEFAULT NULL COMMENT '对账时间',
  `adjust_user` varchar(32) DEFAULT NULL COMMENT '调账人',
  `adjust_note` varchar(255) DEFAULT NULL COMMENT '调账说明',
  `adjust_datetime` datetime DEFAULT NULL COMMENT '调账时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户流水';

-- ----------------------------
--  Table structure for `tstd_read`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_read`;
CREATE TABLE `tstd_read` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `sms_code` varchar(32) DEFAULT NULL COMMENT '消息编号',
  `receive_way` varchar(4) DEFAULT NULL COMMENT '接受方式(站内消息，APP推送,短信)',
  `status` varchar(4) DEFAULT NULL COMMENT '状态 0-未阅读 1-已阅读 2-已删除',
  `create_datetime` datetime DEFAULT NULL COMMENT '推送时间',
  `read_datetime` datetime DEFAULT NULL COMMENT '阅读时间',
  `delete_datetime` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tstd_sign_log`
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
--  Table structure for `tstd_sms`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_sms`;
CREATE TABLE `tstd_sms` (
  `code` varchar(32) NOT NULL COMMENT '消息编号',
  `type` varchar(4) DEFAULT NULL COMMENT '消息类型（系统公告，短信内容）',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `status` varchar(4) DEFAULT NULL COMMENT '状态 0-草稿 1-已发送 2-已回撤',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` varchar(45) DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tstd_user`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_user`;
CREATE TABLE `tstd_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
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
--  Table structure for `tstd_user_ext`
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

DROP TABLE IF EXISTS `tstd_user_field_approve`;
CREATE TABLE `tstd_user_field_approve` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `type` char(1) DEFAULT NULL COMMENT '类型(1=手机,2=邮箱)',
  `field` varchar(255) DEFAULT NULL COMMENT '修改字段',
  `captcha` varchar(16) DEFAULT NULL COMMENT '验证码',
  `id_hold` varchar(255) DEFAULT NULL COMMENT '证件照手持面',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '申请人',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `status` char(1) DEFAULT NULL COMMENT '状态(0=待审核 1=审核通过 2=审核不通过)',
  `approve_user` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '用户字段修改审核';

DROP TABLE IF EXISTS `tstd_user_id_auth`;
CREATE TABLE `tstd_user_id_auth` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `country` varchar(32) NOT NULL COMMENT '隶属国家',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `id_kind` char(1) NOT NULL COMMENT '证件类型(1=身份证)',
  `id_no` varchar(255) NOT NULL COMMENT '证件号码',
  `id_face` varchar(255) NOT NULL COMMENT '证件照正面',
  `id_oppo` varchar(255) NOT NULL COMMENT '证件照反面',
  `id_hold` varchar(255) DEFAULT NULL COMMENT '手持证件照',
  `apply_user` varchar(32) NOT NULL COMMENT '申请人',
  `apply_datetime` datetime NOT NULL COMMENT '申请时间',
  `status` char(1) DEFAULT NULL COMMENT '状态(0=待审核 1=审核通过 2=审核不通过)',
  `approve_user` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tstd_user_relation`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_user_relation`;
CREATE TABLE `tstd_user_relation` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `to_user` varchar(32) DEFAULT NULL COMMENT '关系人编号',
  `type` varchar(4) DEFAULT NULL COMMENT '关系类型',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tstd_user_settings`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_user_settings`;
CREATE TABLE `tstd_user_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `type` varchar(4) NOT NULL COMMENT '设置类型',
  `value` varchar(10) NOT NULL COMMENT '预留值',
  `create_datetime` datetime NOT NULL,
  `update_datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_type` (`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tstd_withdraw`
-- ----------------------------
DROP TABLE IF EXISTS `tstd_withdraw`;
CREATE TABLE `tstd_withdraw` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `account_number` varchar(32) DEFAULT NULL COMMENT '账户编号',
  `account_type` varchar(4) DEFAULT NULL COMMENT '类别（B端账号，C端账号，平台账号）',
  `currency` varchar(32) DEFAULT NULL COMMENT '币种',
  `amount` decimal(64,0) DEFAULT NULL COMMENT '取现金额',
  `fee` decimal(64,0) DEFAULT NULL COMMENT '手续费',
  `actual_amount` decimal(64,0) DEFAULT NULL COMMENT '实际到账金额',
  `channel_type` varchar(32) DEFAULT NULL COMMENT '支付渠道',
  `channel_bank` varchar(32) DEFAULT NULL COMMENT '渠道银行代号',
  `channel_order` varchar(255) DEFAULT NULL COMMENT '支付渠道编号',
  `pay_card_info` varchar(255) DEFAULT NULL COMMENT '支付渠道账号信息',
  `pay_card_no` varchar(255) DEFAULT NULL COMMENT '支付渠道账号',
  `status` varchar(4) NOT NULL COMMENT '状态',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '申请人',
  `apply_note` varchar(255) DEFAULT NULL COMMENT '申请说明',
  `apply_datetime` datetime DEFAULT NULL COMMENT '申请时间',
  `approve_user` varchar(32) DEFAULT NULL COMMENT '审批人',
  `approve_note` varchar(255) DEFAULT NULL COMMENT '审批说明',
  `approve_datetime` varchar(32) DEFAULT NULL COMMENT '审批时间',
  `pay_user` varchar(255) DEFAULT NULL COMMENT '支付回录人',
  `pay_note` varchar(255) DEFAULT NULL COMMENT '支付回录说明',
  `pay_fee` decimal(64,0) DEFAULT NULL COMMENT '支付渠道手续费（矿工费）',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付回录时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='取现订单';

-- ----------------------------
--  Table structure for `tsys_channel_bank`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tsys_cnavigate`
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
--  Table structure for `tsys_company`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_company`;
CREATE TABLE `tsys_company` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tsys_config`
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
--  Table structure for `tsys_department`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_department`;
CREATE TABLE `tsys_department` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `leader` varchar(255) DEFAULT NULL COMMENT '部门负责人',
  `leader_mobile` varchar(16) DEFAULT NULL COMMENT '负责人手机号',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '上级部门',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tsys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_dict`;
CREATE TABLE `tsys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `parent_key` varchar(32) DEFAULT NULL COMMENT '父亲key',
  `dkey` varchar(32) DEFAULT NULL COMMENT 'key',
  `dvalue` varchar(255) DEFAULT NULL COMMENT 'value',
  `updater` varchar(32) DEFAULT NULL COMMENT '最近修改人',
  `update_datetime` datetime DEFAULT NULL COMMENT '最近修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tsys_menu`
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
--  Table structure for `tsys_menu_role`
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
--  Table structure for `tsys_role`
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
--  Table structure for `tsys_user`
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
