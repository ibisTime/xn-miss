/*
 Navicat MySQL Data Transfer

 Source Server         : 31客户服务器
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : rm-bp17qpn5d4bpo2155.mysql.rds.aliyuncs.com:3306
 Source Schema         : xn_miss

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 25/10/2018 23:56:57
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
-- Records of thqxj_action
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_action` VALUES ('AC201810252353585516415', '3', '1', 'P201810212016459583633', 'U201810252351015563056', '2018-10-25 23:53:58', 'Koala @Theia Wallet于2018年10月25日 下午11:53:58查看了选手罗紫琳');
INSERT INTO `thqxj_action` VALUES ('AC201810252354104939560', '3', '1', 'P201810212016459583633', 'U201810252351015563056', '2018-10-25 23:54:10', 'Koala @Theia Wallet于2018年10月25日 下午11:54:10查看了选手罗紫琳');
INSERT INTO `thqxj_action` VALUES ('AC201810252355580819497', '3', '1', 'P201810212016459583633', 'U201810252351015563056', '2018-10-25 23:55:58', 'Koala @Theia Wallet于2018年10月25日 下午11:55:58查看了选手罗紫琳');
INSERT INTO `thqxj_action` VALUES ('AC201810252356036428409', '3', '1', 'P201810212016459583633', 'U201810252351015563056', '2018-10-25 23:56:03', 'Koala @Theia Wallet于2018年10月25日 下午11:56:03查看了选手罗紫琳');
INSERT INTO `thqxj_action` VALUES ('AC201810252356049241359', '3', '1', 'P201810212016459583633', 'U201810252351015563056', '2018-10-25 23:56:04', 'Koala @Theia Wallet于2018年10月25日 下午11:56:04查看了选手罗紫琳');
INSERT INTO `thqxj_action` VALUES ('AC201810252356168149318', '2', '1', 'P201810212016459583633', 'U201810252351015563056', '2018-10-25 23:56:16', 'Koala @Theia Wallet于2018年10月25日 下午11:56:16关注了选手罗紫琳');
INSERT INTO `thqxj_action` VALUES ('AC201810252356345629822', '3', '1', 'P201810212025539515477', 'U201810252351015563056', '2018-10-25 23:56:34', 'Koala @Theia Wallet于2018年10月25日 下午11:56:34查看了选手秦美苏');
INSERT INTO `thqxj_action` VALUES ('AC201810252356409817275', '2', '1', 'P201810212025539515477', 'U201810252351015563056', '2018-10-25 23:56:40', 'Koala @Theia Wallet于2018年10月25日 下午11:56:40关注了选手秦美苏');
INSERT INTO `thqxj_action` VALUES ('AC201810252356465547508', '3', '1', 'P201810212021420536941', 'U201810252351015563056', '2018-10-25 23:56:46', 'Koala @Theia Wallet于2018年10月25日 下午11:56:46查看了选手许继丹');
INSERT INTO `thqxj_action` VALUES ('AC201810252356499036481', '3', '1', 'P201810212016459583633', 'U201810252351015563056', '2018-10-25 23:56:49', 'Koala @Theia Wallet于2018年10月25日 下午11:56:49查看了选手罗紫琳');
INSERT INTO `thqxj_action` VALUES ('AC201810252356540154256', '3', '1', 'P201810212025539515477', 'U201810252351015563056', '2018-10-25 23:56:54', 'Koala @Theia Wallet于2018年10月25日 下午11:56:54查看了选手秦美苏');
COMMIT;

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
-- Records of thqxj_answer
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_answer` VALUES ('AN201810211958503324015', '如何充值', '打开系统，点击“我的----账户管理”打开界面，点击“充值”，按步骤完成微信充值。', '1', '2018-10-21 16:33:09', 'admin', '2018-10-21 16:33:09', '上架回复模版');
INSERT INTO `thqxj_answer` VALUES ('AN201810212000552262875', '为什么要绑定手机号', '用户绑定手机号，可以让平台更快更精准的了解用户，提高服务质量。', '1', '2018-10-21 16:33:09', 'admin', '2018-10-21 16:33:09', '上架回复模版');
INSERT INTO `thqxj_answer` VALUES ('AN201810212002055296316', '什么是交易密码', '交易密码，是在动用平台用户账户余额时，必须输入的密码。它是保障用户资金安全的重要环节。请牢记且勿告知他人。', '1', '2018-10-21 16:33:09', 'admin', '2018-10-21 16:33:09', '上架回复模版');
COMMIT;

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
-- Records of thqxj_comment
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_comment` VALUES ('C201810252354179805710', 'P201810212016459583633', '2', 'U201810252351015563056', 'P201810212016459583633', '我的', '2018-10-25 23:54:17', 'D', NULL, NULL, NULL);
COMMIT;

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
-- Records of thqxj_keyword
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_keyword` VALUES (6, '政治', '0', '3', 'admin', '2018-10-21 16:39:11', '初始化数据');
INSERT INTO `thqxj_keyword` VALUES (7, '经济', '0', '3', 'admin', '2018-10-21 16:39:11', '初始化数据');
INSERT INTO `thqxj_keyword` VALUES (8, '习近平', '0', '3', 'admin', '2018-10-21 16:39:11', '初始化数据');
COMMIT;

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
-- Records of thqxj_player
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_player` VALUES ('P201810212016459583633', '1', '0001', '罗紫琳', 'Roseline', '上海', '175', '45', '120', '70', '90', '罗紫琳征战巴西,在圣保罗举办的第60届环球小姐全球总决 赛上获得第5名,作为中国小姐,罗紫琳被数亿网友评为 “最具人气佳丽”。', 'FuSuzpzDdCZeTety1owUkjCT0vgB', 'FuSuzpzDdCZeTety1owUkjCT0vgB', 'FuSuzpzDdCZeTety1owUkjCT0vgB', '1', 1, '4', '2018-10-21 20:16:45', 'admin', '2018-10-21 20:17:09', '初始化数据', 1, 2, 0, 6);
INSERT INTO `thqxj_player` VALUES ('P201810212021420536941', '1', '0002', '许继丹', 'Diana', '吉林辽源', '180', '50', '34', '25', '36', '许继丹参加61届美国拉斯维加斯总决赛获得“最佳民族服 饰奖”,现任环球小姐大赛中国区组委会执行经理。', 'FkLlvXjb-YeFbvTvWQa6wXD1axec', 'FkLlvXjb-YeFbvTvWQa6wXD1axec', 'FkLlvXjb-YeFbvTvWQa6wXD1axec', '1', 2, '4', '2018-10-21 20:21:42', 'admin', '2018-10-21 20:22:11', '初始化数据', 0, 0, 0, 1);
INSERT INTO `thqxj_player` VALUES ('P201810212025539515477', '1', '0007', '秦美苏', 'Meisu Q', '辽宁', '178', '50', '34', '25', '36', '学校与专业:加拿大阿尔伯塔大学 \n兴趣爱好:欧美爵士舞、健身(有氧运动&拳击)、流行歌曲、旅游\n过往经历:曾是健康明朗的Top.10足球宝贝;参加环姐之前曾经以模特 身份在加拿大,意大利米兰和韩国工作;2017年世界小姐中国区才艺单 项赛十强;超模单项赛十强;网络人气十强;BaByLiss时尚美发大使及 品牌全年代言人。', 'FuRAN4JDNjij3zBz47AOal5zine_', 'FuRAN4JDNjij3zBz47AOal5zine_', 'FuRAN4JDNjij3zBz47AOal5zine_', '1', 3, '4', '2018-10-21 20:25:53', 'admin', '2018-10-21 20:26:16', '初始化数据', 0, 1, 0, 2);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='会话消息';

-- ----------------------------
-- Records of thqxj_question
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_question` VALUES (25, 'SE201810252355215046959', 'SYS_USER', '欢迎使用客服,有什么可以帮助到您', '1', '2018-10-25 23:55:21');
INSERT INTO `thqxj_question` VALUES (26, 'SE201810252355215046959', 'U201810252351015563056', '我们的生活', '0', '2018-10-25 23:55:21');
COMMIT;

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
-- Records of thqxj_rank
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_rank` VALUES ('R201810252356168269537', '1', '20181025', 'P201810212016459583633', 1, '1', '2018-10-25 23:56:16', NULL, NULL, NULL, 1, 0, 0, 0, 0);
INSERT INTO `thqxj_rank` VALUES ('R201810252356168367491', '2', NULL, 'P201810212016459583633', 1, '1', '2018-10-25 23:56:16', NULL, NULL, NULL, 1, 0, 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for thqxj_read
-- ----------------------------
DROP TABLE IF EXISTS `thqxj_read`;
CREATE TABLE `thqxj_read` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `to_type` varchar(4) NOT NULL COMMENT '对象类型',
  `to_code` varchar(32) NOT NULL COMMENT '对象编号',
  `status` varchar(4) NOT NULL COMMENT '状态（0 未读，1 已读）',
  `create_datetime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COMMENT='阅读';

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
-- Records of thqxj_session
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_session` VALUES ('SE201810252355215046959', 'cq', 'U201810252351015563056', 'SYS_USER', 1, '2018-10-25 23:55:21');
COMMIT;

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
-- Records of thqxj_ticket
-- ----------------------------
BEGIN;
INSERT INTO `thqxj_ticket` VALUES ('T201810252356062578289', 'P201810212016459583633', 1, 10.000, 'U201810252351015563056', '1', '2018-10-25 23:56:06', '2018-10-26 00:26:06', '5', 'T201810252356062578289', '4200000213201810255221957662', 10, '2018-10-25 23:56:16', NULL, NULL, '支付成功');
COMMIT;

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
-- Records of tstd_account
-- ----------------------------
BEGIN;
INSERT INTO `tstd_account` VALUES ('A201810252351015669379', 'U201810252351015563056', 'Koala @Theia Wallet', 'C', '0', 'CNY', 0, 0, '4122cb13c7a474c1976c9706ae36521d', 0, 0, 0, '2018-10-25 23:51:01', NULL, 'CD-MISS000030', NULL);
INSERT INTO `tstd_account` VALUES ('SYS_ACOUNT_B', 'B_USER', '品牌方账户', 'B', '0', 'CNY', 8, 0, 'd38901788c533e8286cb6400b40b386d', 8, 0, 0, '2018-10-25 23:36:40', 'AJ201810252356168014070', 'CD-MISS000030', 'CD-MISS000030');
INSERT INTO `tstd_account` VALUES ('SYS_ACOUNT_CNY', 'SYS_USER', '平台盈亏账户', 'P', '0', 'CNY', -8, 0, 'be763e0a4af321b1759bf1130a5b094f', 0, 0, 0, '2018-10-25 23:36:40', 'AJ201810252356167952978', 'CD-MISS000030', 'CD-MISS000030');
INSERT INTO `tstd_account` VALUES ('SYS_ACOUNT_OFFLINE_TG', 'SYS_USER', '平台线下托管账户', 'P', '0', 'CNY', 0, 0, '76c8527bc17bab8fd525cab5e5e4d354', 0, 0, 0, '2018-10-25 23:36:40', NULL, 'CD-MISS000030', 'CD-MISS000030');
INSERT INTO `tstd_account` VALUES ('SYS_ACOUNT_WEIXIN_TG', 'SYS_USER', '平台线下微信托管账户', 'P', '0', 'CNY', 10, 0, '0f19d4d93ea331b17b6156e8fcdc6e3c', 10, 0, 0, '2018-10-25 23:36:40', 'AJ201810252356167744573', 'CD-MISS000030', 'CD-MISS000030');
COMMIT;

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
-- Records of tstd_charge
-- ----------------------------
BEGIN;
INSERT INTO `tstd_charge` VALUES ('CH201810252356094007128', 'T201810252356062578289', 'T201810252356062578289', 'SYS_ACOUNT_CNY', NULL, '平台盈亏账户', 10, 'P', 'CNY', 'ticket', '加油订单', NULL, NULL, '3', 'U201810252351015563056', '2018-10-25 23:56:09', NULL, '在线充值自动回调', '2018-10-25 23:56:16', '35', 'CD-MISS000030', 'CD-MISS000030');
COMMIT;

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
-- Records of tstd_company_channel
-- ----------------------------
BEGIN;
INSERT INTO `tstd_company_channel` VALUES (2, 'CD-MISS000030', '环球小姐', '35', '1', '1481010032', 'r2jdDFSdiikklwlllejlwjio3232451n', 'wx9c709bf30e60c07b', '', NULL, NULL, NULL, NULL, '', NULL, NULL, 'CD-MISS000030');
COMMIT;

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
-- Records of tstd_jour
-- ----------------------------
BEGIN;
INSERT INTO `tstd_jour` VALUES ('AJ201810252356167664482', 'T201810252356062578289', 'T201810252356062578289', '4200000213201810255221957662', 'SYS_ACOUNT_CNY', 10, 'SYS_USER', '平台盈亏账户', '0', 'P', 'CNY', 'ticket', '加油订单', 0, 10, '1', '记得对账哦', '2018-10-25 23:56:16', '20181025', NULL, NULL, NULL, NULL, NULL, NULL, '35', 'CD-MISS000030', 'CD-MISS000030');
INSERT INTO `tstd_jour` VALUES ('AJ201810252356167744573', 'T201810252356062578289', 'T201810252356062578289', '4200000213201810255221957662', 'SYS_ACOUNT_WEIXIN_TG', 10, 'SYS_USER', '平台线下微信托管账户', '0', 'P', 'CNY', 'ticket', '加油订单', 0, 10, '1', '记得对账哦', '2018-10-25 23:56:16', '20181025', NULL, NULL, NULL, NULL, NULL, NULL, '35', 'CD-MISS000030', 'CD-MISS000030');
INSERT INTO `tstd_jour` VALUES ('AJ201810252356167952978', NULL, 'T201810252356062578289', NULL, 'SYS_ACOUNT_CNY', -8, 'SYS_USER', '平台盈亏账户', '0', 'P', 'CNY', 'dividend', '加油分成', 0, -8, '1', '记得对账哦', '2018-10-25 23:56:16', '20181025', NULL, NULL, NULL, NULL, NULL, NULL, '0', 'CD-MISS000030', 'CD-MISS000030');
INSERT INTO `tstd_jour` VALUES ('AJ201810252356168014070', NULL, 'T201810252356062578289', NULL, 'SYS_ACOUNT_B', 8, 'B_USER', '品牌方账户', '0', 'B', 'CNY', 'dividend', '加油分成', 0, 8, '1', '记得对账哦', '2018-10-25 23:56:16', '20181025', NULL, NULL, NULL, NULL, NULL, NULL, '0', 'CD-MISS000030', 'CD-MISS000030');
COMMIT;

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
  `nickname` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
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
-- Records of tstd_user
-- ----------------------------
BEGIN;
INSERT INTO `tstd_user` VALUES ('U201810252351015563056', NULL, 'oMSU70vSm0XNnVc6FMP6vETd0Ns4', NULL, NULL, NULL, 'C', 'http://thirdwx.qlogo.cn/mmopen/vi_32/yzyibXO2etfcMTdDiaMaeLAiahwFzwYu98GBuCV46x0hFXCz1pMf9kketNpdzae2UdiciamVQ4oEo9M2Eibm4y3upMsA/132', 'Koala @Theia Wallet', '21218cca77804d2ba1922c33e0151105', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-10-25 23:51:01', NULL, NULL, NULL, NULL);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_channel_bank
-- ----------------------------
BEGIN;
INSERT INTO `tsys_channel_bank` VALUES (19, 'CMBC', '中国民生银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (20, 'ZJTLCB', '浙江泰隆商业银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (21, 'ZJCZCB', '浙江稠州商业银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (22, 'CMB', '招商银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (23, 'SHB', '上海银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (24, 'PAB', '平安银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (25, 'SPDB', '浦发银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (26, 'CIB', '兴业银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (27, 'ICBC', '中国工商银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (28, 'CEB', '中国光大银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (29, 'CCB', '中国建设银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (30, 'BCM', '中国交通银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (31, 'ABC', '中国农业银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (32, 'BOC', '中国银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (33, 'PSBC', '中国邮政储蓄银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (34, 'CITIC', '中信银行', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (35, 'alipay', '支付宝', '40', '1', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tsys_channel_bank` VALUES (36, 'WeChat', '微信', '40', '1', NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

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
-- Records of tsys_cnavigate
-- ----------------------------
BEGIN;
INSERT INTO `tsys_cnavigate` VALUES ('DH201810211945216783706', '中国美大使', '2', '', 'lhXGWhfN7mFgWwfDb5GeeoAV95JW', '1', 'index_banner', 1, '0', '');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_config
-- ----------------------------
BEGIN;
INSERT INTO `tsys_config` VALUES (16, 'qiniu', 'qiniu_access_key', 'Dc0pMP8ImFm78-uk4iGsOPpB2-vHc64D07OsOQVi', 'admin', '2018-10-20 14:29:38', '七牛云key1');
INSERT INTO `tsys_config` VALUES (17, 'qiniu', 'qiniu_secret_key', '3NP-tpZP9-5fH-R-FhvKTfYpPPVFNvjFF3JXmrcq', 'admin', '2018-10-20 14:29:38', '七牛云key1');
INSERT INTO `tsys_config` VALUES (18, 'qiniu', 'qiniu_bucket', 'test', 'admin', '2018-10-20 14:29:38', '存储空间');
INSERT INTO `tsys_config` VALUES (19, 'qiniu', 'qiniu_domain', 'ounm8iw2d.bkt.clouddn.com', 'admin', '2018-10-20 14:29:38', '访问域名');
INSERT INTO `tsys_config` VALUES (20, 'wx_h5', 'wx_h5_access_key', 'wx9c709bf30e60c07b', 'admin', '2018-10-20 14:29:38', 'WX_H5_ACCESS_KEY');
INSERT INTO `tsys_config` VALUES (21, 'wx_h5', 'wx_h5_secret_key', '0cb61ce758017e9a41547fb0ad710b37', 'admin', '2018-10-20 14:29:38', 'WX_H5_SECRET_KEY');
INSERT INTO `tsys_config` VALUES (22, 'sys_txt', 'REGISTRATION_AGREEMENT', '注册协议', 'admin', '2018-10-20 14:29:38', '注册协议');
INSERT INTO `tsys_config` VALUES (23, 'sys_txt', 'invite_url', '邀请好友链接', 'admin', '2018-10-20 14:29:38', '邀请好友链接');
INSERT INTO `tsys_config` VALUES (24, 'ticket', 'price', '0.01', 'admin', '2018-10-20 14:29:38', '订单加油票价格(元)');
INSERT INTO `tsys_config` VALUES (25, 'ticket', 'invalid_time', '30', 'admin', '2018-10-20 14:29:38', '订单失效时长（分钟）');
INSERT INTO `tsys_config` VALUES (26, 'ticket', 'return_money', '0.01', 'admin', '2018-10-20 14:29:38', '首次分享返现金额(元)');
INSERT INTO `tsys_config` VALUES (27, 'dividend', 'plat_rate', '0.2', 'admin', '2018-10-20 14:29:38', '平台加油分成比例');
INSERT INTO `tsys_config` VALUES (28, 'dividend', 'business_rate', '0.8', 'admin', '2018-10-20 14:29:38', '品牌方加油分成比例');
INSERT INTO `tsys_config` VALUES (29, 'withdraw', 'fee_rate', '0.01', 'admin', '2018-10-20 14:29:38', '取现费率');
INSERT INTO `tsys_config` VALUES (30, 'sys_txt', 'first_chat', '欢迎使用客服,有什么可以帮助到您', 'admin', '2018-10-20 14:29:38', '创建会话第一条消息');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_dict
-- ----------------------------
BEGIN;
INSERT INTO `tsys_dict` VALUES (110, '0', NULL, 'role_level', '角色等级', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (111, '1', 'role_level', '1', '运维', 'admin', '2018-10-21 16:50:20', '');
INSERT INTO `tsys_dict` VALUES (112, '1', 'role_level', '2', '运营', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (113, '1', 'role_level', '3', '客户', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (114, '0', NULL, 'user_status', '用户状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (115, '1', 'user_status', '0', '正常', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (116, '1', 'user_status', '1', '程序锁定', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (117, '1', 'user_status', '2', '人工锁定', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (118, '0', NULL, 'pay_type', '支付方式', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (119, '1', 'pay_type', '1', '余额支付', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (120, '1', 'pay_type', '5', '微信h5', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (121, '0', NULL, 'channel_type', '渠道类型', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (122, '1', 'channel_type', '0', '内部账', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (123, '1', 'channel_type', '90', '人工线下', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (124, '1', 'channel_type', '35', '微信H5支付', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (125, '0', NULL, 'jour_status', '流水状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (126, '1', 'jour_status', '1', '待对账', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (127, '1', 'jour_status', '3', '已对账且账已平', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (128, '1', 'jour_status', '4', '帐不平待调账审批', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (129, '1', 'jour_status', '5', '已对账且账不平', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (130, '1', 'jour_status', '6', '无需对账', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (131, '0', NULL, 'sms_status', '公告状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (132, '1', 'sms_status', '0', '草稿', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (133, '1', 'sms_status', '1', '已发送', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (134, '1', 'sms_status', '2', '已撤回', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (135, '0', NULL, 'approve_status', '认证审核状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (136, '1', 'approve_status', '0', '待审核', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (137, '1', 'approve_status', '1', '审核通过', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (138, '1', 'approve_status', '2', '审核不通过', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (139, '0', NULL, 'banner_location', 'banner位置', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (140, '1', 'banner_location', 'index_banner', '首页', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (141, '0', NULL, 'player_status', '选手状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (142, '1', 'player_status', '0', '草稿', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (143, '1', 'player_status', '1', '待审批', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (144, '1', 'player_status', '2', '审批不通过', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (145, '1', 'player_status', '3', '待上架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (146, '1', 'player_status', '4', '已上架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (147, '1', 'player_status', '5', '已下架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (148, '0', NULL, 'event_status', '赛事状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (149, '1', 'event_status', '南京赛区', '草稿', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (150, '1', 'event_status', '1', '待审核', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (151, '1', 'event_status', '2', '审核不通过', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (152, '1', 'event_status', '3', '待上架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (153, '1', 'event_status', '4', '已上架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (154, '1', 'event_status', '5', '已下架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (155, '0', NULL, 'match', '赛区', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (156, '1', 'match', '1', '南京赛区', 'admin', '2018-10-21 16:50:20', '');
INSERT INTO `tsys_dict` VALUES (157, '0', NULL, 'ticket_order_status', '加油订单状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (158, '1', 'ticket_order_status', '0', '待支付', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (159, '1', 'ticket_order_status', '1', '已支付', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (160, '1', 'ticket_order_status', '2', '超时取消', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (161, '1', 'ticket_order_status', '3', '已取消', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (162, '0', NULL, 'comment_status', '评论状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (163, '1', 'comment_status', 'A', '待审核', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (164, '1', 'comment_status', 'B', '审核通过', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (165, '1', 'comment_status', 'C', '审核不通过', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (166, '1', 'comment_status', 'D', '已发布', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (167, '1', 'comment_status', 'G', '已删除', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (168, '0', NULL, 'answer_status', '回复模版状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (169, '1', 'answer_status', '0', '草稿', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (170, '1', 'answer_status', '1', '已提交待审批', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (171, '1', 'answer_status', '2', '审批不通过', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (172, '1', 'answer_status', '3', '已审批待上架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (173, '1', 'answer_status', '4', '已上架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (174, '1', 'answer_status', '5', '已下架', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (175, '0', NULL, 'account_type', '账户类型', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (176, '1', 'account_type', 'C', 'C端用户', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (177, '1', 'account_type', 'B', 'B端用户', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (178, '1', 'account_type', 'P', '平台用户', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (179, '0', NULL, 'id_kind', '证件类型', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (180, '1', 'id_kind', '1', '身份证', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (181, '0', NULL, 'account_status', '账户状态', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (182, '1', 'account_status', '0', '正常', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (183, '1', 'account_status', '1', '程序锁定', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (184, '1', 'account_status', '2', '人工锁定', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (185, '0', NULL, 'currency', '币种', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (186, '1', 'currency', 'CNY', '人民币', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (187, '0', NULL, 'jour_type', NULL, NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (188, '1', 'jour_type', '0', '余额流水', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (189, '1', 'jour_type', '1', '冻结金额流水', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (190, '0', NULL, 'bankcard_status', '银行卡状态', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (191, '1', 'bankcard_status', '0', '未启用', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (192, '1', 'bankcard_status', '1', '启用', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (193, '0', NULL, 'charge_status', '充值状态', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (194, '1', 'charge_status', '1', '待支付', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (195, '1', 'charge_status', '2', '支付失败', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (196, '1', 'charge_status', '3', '支付成功', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (197, '0', NULL, 'withdraw_status', '取现状态', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (198, '1', 'withdraw_status', '1', '待审批', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (199, '1', 'withdraw_status', '2', '审批不通过', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (200, '1', 'withdraw_status', '3', '审批通过待支付', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (201, '1', 'withdraw_status', '5', '支付失败', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (202, '1', 'withdraw_status', '6', '支付成功', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (203, '0', NULL, 'biz_type', '流水业务类型', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (204, '1', 'biz_type', 'dividend', '加油分成', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (205, '1', 'biz_type', 'first_share', '首次分享送钱', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (206, '1', 'biz_type', 'supply', '补给', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (207, '1', 'biz_type', 'withdraw', '取现', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (208, '1', 'biz_type', 'withdraw_fee', '取现手续费', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (209, '1', 'biz_type', 'pay_channel', '支付通道费', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (210, '1', 'biz_type', 'ticket', '加油订单', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (211, '1', 'biz_type', 'charge', '充值', NULL, '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (212, '0', NULL, 'com_location', '通用的位置', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (213, '1', 'com_location', '0', '首页', 'admin', '2018-10-21 16:50:20', NULL);
INSERT INTO `tsys_dict` VALUES (214, '0', 'read_status', '赛事阅读状态', NULL, NULL, NULL, NULL);
INSERT INTO `tsys_dict` VALUES (215, '1', '0', '待阅读', NULL, NULL, NULL, NULL);
INSERT INTO `tsys_dict` VALUES (216, '1', '1', '已阅读', NULL, NULL, NULL, NULL);
INSERT INTO `tsys_dict` VALUES (217, '1', '2', '已删除', NULL, NULL, NULL, NULL);
COMMIT;

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
-- Records of tsys_menu
-- ----------------------------
BEGIN;
INSERT INTO `tsys_menu` VALUES ('CD201809281458013408308', 'COINSM201612071021105964', '平台账户', '1', '#', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281458246867562', 'COINSM201612071021105964', '充值管理', '1', '#', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281458530008193', 'COINSM201612071021105964', '线下取现', '1', '#', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281504013431237', 'CD201809281458013408308', '账户查询', '1', '/platform/account.htm', 5, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281504368347161', 'CD201809281458013408308', '流水查询', '1', '/platform/flows.htm', 6, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281507086522935', 'CD201809281458246867562', '线下充值', '1', '/recharge/recharges.htm', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281507479361586', 'CD201809281458246867562', '充值查询', '1', '/recharge/records.htm', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281509063426888', 'CD201809281458530008193', '取现规则', '1', '/withdraw/rules.htm', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281509450622929', 'CD201809281458530008193', '线下取现', '1', '/withdraw/withdraw.htm', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201809281510256651980', 'CD201809281458530008193', '取现查询', '1', '/withdraw/records.htm', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810050104422483297', 'CD201809281507086522935', '待申请', '2', '/add', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810050105195526885', 'CD201809281507086522935', '详情', '2', '/detail', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810050105538468486', 'CD201809281507086522935', '审核', '2', '/check', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810050106121877523', 'CD201809281507086522935', '导出', '2', '/export', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810050154234414919', 'CD201809281507479361586', '详情', '2', '/detail', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810050154373965588', 'CD201809281507479361586', '导出', '2', '/export', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810051837019382814', 'CD201809281509063426888', '修改', '2', '/edit', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810061158329263282', 'CD201809281509450622929', '审核', '2', '/check', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810061200553476264', 'CD201809281509450622929', '打款回录', '2', '/enter', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810061201298954721', 'CD201809281510256651980', '详情', '2', '/detail', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810061201456666951', 'CD201809281510256651980', '导出', '2', '/export', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121842229668485', 'COINSM201700001000000002', '数据字典管理', '1', '/system/dataDict.htm', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121843007858513', 'CD201810121842229668485', '新增', '2', '/add', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121843175244898', 'CD201810121842229668485', '修改', '2', '/edit', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121916519372324', 'COINSM201708241036442974134', '用户管理', '1', '#', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121917429845492', 'COINSM201708241036442974134', '赛事管理', '1', '#', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121918346737635', 'COINSM201708241036442974134', '客服管理', '1', '#', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121919298833683', 'COINSM201708241036442974134', '评论管理', '1', '#', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121922124697535', 'CD201810121916519372324', '会员管理', '1', '/user/users.htm', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121922511425308', 'CD201810121916519372324', '分享记录', '1', '/user/shares.htm', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121923539243299', 'CD201810121916519372324', '足迹记录', '1', '/user/footmark.htm', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121924458689782', 'CD201810121916519372324', '关注记录', '1', '/user/follow.htm', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121925244817607', 'CD201810121916519372324', '加油订单查询', '1', '/user/orders.htm', 5, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121926156378556', 'CD201810121916519372324', '流水查询', '1', '/user/flows.htm', 7, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121927115959711', 'CD201810121922124697535', '账户查询', '2', '/accounts', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121927329202713', 'CD201810121922124697535', '激活', '2', '/active', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121927536639700', 'CD201810121922124697535', '注销', '2', '/rock', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121928269616076', 'CD201810121922124697535', '详情', '2', '/detail', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121928434749777', 'CD201810121922124697535', '导出', '2', '/export', 5, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121929067945387', 'CD201810121922511425308', '导出', '2', '/export', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121929265826282', 'CD201810121923539243299', '导出', '2', '/export', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121929532369633', 'CD201810121925244817607', '详情', '2', '/detail', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121930146804566', 'CD201810121925244817607', '导出', '2', '/export', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121931077922031', 'CD201809281504013431237', '流水查询', '2', '/flows', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121931276972672', 'CD201809281504013431237', '导出', '2', '/export', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121932478717240', 'CD201810121926156378556', '详情', '2', '/detail', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121933033345103', 'CD201810121926156378556', '导出', '2', '/export', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121934261473135', 'CD201810121917429845492', '赛事信息管理', '1', '/game/infos.htm', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121935038614673', 'CD201810121917429845492', '选手管理', '1', '/game/players.htm', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121935583599708', 'CD201810121917429845492', '总榜管理', '1', '/game/ranking.htm', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121937291076592', 'CD201810121917429845492', '飙升榜管理', '1', '/game/soaring.htm', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121937481066102', 'CD201810121934261473135', '新增', '2', '/add', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121938096934413', 'CD201810121934261473135', '修改', '2', '/edit', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121938286482068', 'CD201810121934261473135', '审批', '2', '/check', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121938457323540', 'CD201810121934261473135', '上架', '2', '/up', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121939078841561', 'CD201810121934261473135', '下架', '2', '/down', 5, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121939249947233', 'CD201810121934261473135', '详情', '2', '/detail', 6, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121941015015580', 'CD201810121935038614673', '新增', '2', '/add', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121941142082187', 'CD201810121935038614673', '修改', '2', '/edit', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121941299117309', 'CD201810121935038614673', '审批', '2', '/check', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121941478257959', 'CD201810121935038614673', '上架', '2', '/up', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121942099449133', 'CD201810121935038614673', '下架', '2', '/down', 5, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121942285361952', 'CD201810121935038614673', '详情', '2', '/detail', 6, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121942566739319', 'CD201810121935583599708', '人工调节', '2', '/adjust', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121943199691182', 'CD201810121935583599708', '详情', '2', '/detail', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121943460856990', 'CD201810121937291076592', '人工调节', '2', '/adjust', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121944021205126', 'CD201810121937291076592', '详情', '2', '/detail', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121946185283265', 'CD201810121918346737635', '待回复消息', '1', '/service/unread.htm', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121946481923232', 'CD201810121918346737635', '消息查询', '1', '/service/messages.htm', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121947359159984', 'CD201810121918346737635', '回复模版', '1', '/service/template.htm', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121948219653901', 'CD201810121946185283265', '回复', '2', '/reply', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121949254277591', 'CD201810121946481923232', '详情', '2', '/detail', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121949468396367', 'CD201810121947359159984', '新增', '2', '/add', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121950061402481', 'CD201810121947359159984', '修改', '2', '/edit', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121950193818892', 'CD201810121947359159984', '删除', '2', '/delete', 3, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121951119121635', 'CD201810121947359159984', '详情', '2', '/detail', 4, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121952062771760', 'CD201810121919298833683', '关键字设置', '1', '/comment/keywords.htm', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121952293923697', 'CD201810121919298833683', '评论审核', '1', '/comment/check.htm', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121953152201939', 'CD201810121919298833683', '评论查询', '1', '/comment/list.htm', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121953307858741', 'CD201810121952062771760', '新增', '2', '/add', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121953309098143', 'CD201810121952062771760', '修改', '2', '/edit', 2, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121955029358419', 'CD201810121952062771760', '删除', '2', '/delete', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121955202915122', 'CD201810121952062771760', '导入', '2', '/import', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121955393392765', 'CD201810121952293923697', '审核', '2', '/check', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121956101801874', 'CD201810121953152201939', '详情', '2', '/detail', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121957172842697', 'COINSM2017033020005366333', '撤下', '2', '/down', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810121959550577450', 'CD201810121916519372324', '账户查询', '1', '/user/accounts.htm', 6, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810122001591962717', 'CD201810121959550577450', '流水查询', '2', '/flows', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810131047538286284', 'CD201810121924458689782', '导出', '2', '/export', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810182259538052129', 'CD201809281458246867562', '线下充值帐号', '1', '/recharge/accounts.htm', 0, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810182300338676052', 'CD201810182259538052129', '新增', '2', '/add', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810182300585245762', 'CD201810182259538052129', '启用', '2', '/up', 2, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810182301361166056', 'CD201810182259538052129', '未启用', '2', '/down', 3, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810182301539934985', 'CD201810182259538052129', '详情', '2', '/detail', 4, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810212105457834711', 'COINSM201700001000000002', '系统参数管理', '1', '/system/sysPara.htm', 3, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810212106099496012', 'CD201810212105457834711', '修改', '2', '/edit', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('CD201810212309012784091', 'CD201809281509450622929', '待申请', '2', '/apply', 0, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201612071021105964', 'COINSM201700000000000000', '财务管理', '1', '#', 4, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201700000000000000', '', '根目录', '1', '#', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201700001000000001', 'COINSM201700000000000000', '系统管理', '1', '#', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201700001000000002', 'COINSM201700001000000001', '运维管理', '1', '#', 2, 'admin', '2018-10-21 16:55:44', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201700001000000003', 'COINSM201700001000000002', '菜单管理', '1', '/system/menu.htm', 1, 'admin', '2018-10-21 16:55:44', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201700001000000004', 'COINSM201700001000000003', '新增', '2', '/add', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017032911200961325', 'COINSM201700001000000003', '修改', '2', '/edit', 2, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017033020005366333', 'COINSM201707251006045006005', 'banner管理', '1', '/public/banner.htm', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017033020015631166', 'COINSM2017033020005366333', '新增', '2', '/add', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017033020021094115', 'COINSM2017033020005366333', '修改', '2', '/edit', 2, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017033020022649991', 'COINSM2017033020005366333', '发布', '2', '/up', 3, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017033020024827112', 'COINSM2017033020005366333', '详情', '2', '/detail', 5, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201707251006045006005', 'COINSM201708241036442974134', '广告位管理', '1', '#', 5, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201708241024194086655', 'COINSM201700001000000003', '删除', '2', '/delete', 3, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM201708241036442974134', 'COINSM201700000000000000', '业务管理', '1', '#', 2, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101716241339082', 'COINSM201700001000000001', '运营管理', '1', '#', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101716253866426', 'COINSM2017101716241339082', '角色管理', '1', '/system/role.htm', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101716261754674', 'COINSM2017101716241339082', '用户管理', '1', '/system/user.htm', 2, 'admin', '2018-10-21 16:55:44', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101716450533995', 'COINSM2017101716253866426', '分配菜单', '2', '/change', 4, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101717551955993', 'COINSM2017101716253866426', '新增', '2', '/add', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101717560118734', 'COINSM2017101716253866426', '修改', '2', '/edit', 2, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101717563661357', 'COINSM2017101716253866426', '删除', '2', '/delete', 3, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101719082391126', 'COINSM2017101716261754674', '新增', '2', '/add', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101719094151894', 'COINSM2017101716261754674', '重置密码', '2', '/reset', 2, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101719100760088', 'COINSM2017101716261754674', '激活 / 注销', '2', '/rock', 4, 'admin', '2018-10-21 16:55:44', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017101719110981215', 'COINSM2017101716261754674', '设置角色', '2', '/assign', 5, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('COINSM2017121215543215610', 'COINSM201700001000000001', '文章管理', '1', '#', 4, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('SM201711081400404895487', 'SM201711081111547852084', '修改', '2', '/edit', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('SM201711181220013316605', 'COINSM2017121215543215610', '注册协议', '1', '/public/registrationAgreement.htm', 1, 'UCOIN201700000000000001', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('SM201711232222591772487', 'SM201711231353203735078', '修改', '2', '/edit', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('SM201711232223319261758', 'SM201711231354531532054', '修改', '2', '/edit', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
INSERT INTO `tsys_menu` VALUES ('SM201711251639362545256', 'SM201711251639118496237', '修改', '2', '/edit', 1, 'admin', '2018-10-21 16:55:44', '', 'CD-MISS000030');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=509 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_menu_role
-- ----------------------------
BEGIN;
INSERT INTO `tsys_menu_role` VALUES (381, 'COINSR201700000000000000', 'CD201810182259538052129', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (382, 'COINSR201700000000000000', 'CD201809281458013408308', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (383, 'COINSR201700000000000000', 'CD201809281509063426888', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (384, 'COINSR201700000000000000', 'CD201810050104422483297', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (385, 'COINSR201700000000000000', 'CD201810050154234414919', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (386, 'COINSR201700000000000000', 'CD201810051837019382814', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (387, 'COINSR201700000000000000', 'CD201810061158329263282', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (388, 'COINSR201700000000000000', 'CD201810061201298954721', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (389, 'COINSR201700000000000000', 'CD201810121843007858513', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (390, 'COINSR201700000000000000', 'CD201810121916519372324', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (391, 'COINSR201700000000000000', 'CD201810121922124697535', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (392, 'COINSR201700000000000000', 'CD201810121927115959711', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (393, 'COINSR201700000000000000', 'CD201810121929067945387', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (394, 'COINSR201700000000000000', 'CD201810121929265826282', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (395, 'COINSR201700000000000000', 'CD201810121929532369633', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (396, 'COINSR201700000000000000', 'CD201810121931077922031', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (397, 'COINSR201700000000000000', 'CD201810121932478717240', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (398, 'COINSR201700000000000000', 'CD201810121934261473135', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (399, 'COINSR201700000000000000', 'CD201810121937481066102', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (400, 'COINSR201700000000000000', 'CD201810121941015015580', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (401, 'COINSR201700000000000000', 'CD201810121942566739319', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (402, 'COINSR201700000000000000', 'CD201810121943460856990', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (403, 'COINSR201700000000000000', 'CD201810121946185283265', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (404, 'COINSR201700000000000000', 'CD201810121948219653901', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (405, 'COINSR201700000000000000', 'CD201810121949254277591', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (406, 'COINSR201700000000000000', 'CD201810121949468396367', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (407, 'COINSR201700000000000000', 'CD201810121952062771760', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (408, 'COINSR201700000000000000', 'CD201810121953307858741', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (409, 'COINSR201700000000000000', 'CD201810121955393392765', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (410, 'COINSR201700000000000000', 'CD201810121956101801874', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (411, 'COINSR201700000000000000', 'CD201810122001591962717', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (412, 'COINSR201700000000000000', 'CD201810131047538286284', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (413, 'COINSR201700000000000000', 'CD201810182300338676052', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (414, 'COINSR201700000000000000', 'CD201810212106099496012', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (415, 'COINSR201700000000000000', 'COINSM201700000000000000', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (416, 'COINSR201700000000000000', 'COINSM201700001000000001', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (417, 'COINSR201700000000000000', 'COINSM201700001000000003', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (418, 'COINSR201700000000000000', 'COINSM201700001000000004', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (419, 'COINSR201700000000000000', 'COINSM2017033020005366333', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (420, 'COINSR201700000000000000', 'COINSM2017033020015631166', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (421, 'COINSR201700000000000000', 'COINSM2017101716241339082', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (422, 'COINSR201700000000000000', 'COINSM2017101716253866426', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (423, 'COINSR201700000000000000', 'COINSM2017101717551955993', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (424, 'COINSR201700000000000000', 'COINSM2017101719082391126', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (425, 'COINSR201700000000000000', 'SM201711181220013316605', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (426, 'COINSR201700000000000000', 'SM201711232222591772487', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (427, 'COINSR201700000000000000', 'SM201711232223319261758', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (428, 'COINSR201700000000000000', 'SM201711251639362545256', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (429, 'COINSR201700000000000000', 'CD201809281458246867562', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (430, 'COINSR201700000000000000', 'CD201809281507086522935', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (431, 'COINSR201700000000000000', 'CD201809281509450622929', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (432, 'COINSR201700000000000000', 'CD201810050105538468486', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (433, 'COINSR201700000000000000', 'CD201810050154373965588', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (434, 'COINSR201700000000000000', 'CD201810061200553476264', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (435, 'COINSR201700000000000000', 'CD201810061201456666951', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (436, 'COINSR201700000000000000', 'CD201810121842229668485', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (437, 'COINSR201700000000000000', 'CD201810121843175244898', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (438, 'COINSR201700000000000000', 'CD201810121917429845492', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (439, 'COINSR201700000000000000', 'CD201810121922511425308', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (440, 'COINSR201700000000000000', 'CD201810121927329202713', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (441, 'COINSR201700000000000000', 'CD201810121930146804566', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (442, 'COINSR201700000000000000', 'CD201810121931276972672', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (443, 'COINSR201700000000000000', 'CD201810121933033345103', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (444, 'COINSR201700000000000000', 'CD201810121935038614673', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (445, 'COINSR201700000000000000', 'CD201810121938096934413', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (446, 'COINSR201700000000000000', 'CD201810121941142082187', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (447, 'COINSR201700000000000000', 'CD201810121943199691182', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (448, 'COINSR201700000000000000', 'CD201810121944021205126', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (449, 'COINSR201700000000000000', 'CD201810121946481923232', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (450, 'COINSR201700000000000000', 'CD201810121950061402481', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (451, 'COINSR201700000000000000', 'CD201810121952293923697', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (452, 'COINSR201700000000000000', 'CD201810121953309098143', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (453, 'COINSR201700000000000000', 'CD201810182300585245762', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (454, 'COINSR201700000000000000', 'COINSM201700001000000002', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (455, 'COINSR201700000000000000', 'COINSM2017032911200961325', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (456, 'COINSR201700000000000000', 'COINSM2017033020021094115', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (457, 'COINSR201700000000000000', 'COINSM201708241036442974134', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (458, 'COINSR201700000000000000', 'COINSM2017101716261754674', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (459, 'COINSR201700000000000000', 'COINSM2017101717560118734', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (460, 'COINSR201700000000000000', 'COINSM2017101719094151894', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (461, 'COINSR201700000000000000', 'CD201809281458530008193', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (462, 'COINSR201700000000000000', 'CD201809281507479361586', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (463, 'COINSR201700000000000000', 'CD201809281510256651980', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (464, 'COINSR201700000000000000', 'CD201810050105195526885', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (465, 'COINSR201700000000000000', 'CD201810121918346737635', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (466, 'COINSR201700000000000000', 'CD201810121923539243299', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (467, 'COINSR201700000000000000', 'CD201810121927536639700', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (468, 'COINSR201700000000000000', 'CD201810121935583599708', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (469, 'COINSR201700000000000000', 'CD201810121938286482068', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (470, 'COINSR201700000000000000', 'CD201810121941299117309', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (471, 'COINSR201700000000000000', 'CD201810121947359159984', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (472, 'COINSR201700000000000000', 'CD201810121950193818892', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (473, 'COINSR201700000000000000', 'CD201810121953152201939', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (474, 'COINSR201700000000000000', 'CD201810121955029358419', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (475, 'COINSR201700000000000000', 'CD201810182301361166056', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (476, 'COINSR201700000000000000', 'CD201810212105457834711', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (477, 'COINSR201700000000000000', 'COINSM2017033020022649991', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (478, 'COINSR201700000000000000', 'COINSM201708241024194086655', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (479, 'COINSR201700000000000000', 'COINSM2017101717563661357', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (480, 'COINSR201700000000000000', 'CD201810050106121877523', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (481, 'COINSR201700000000000000', 'CD201810121919298833683', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (482, 'COINSR201700000000000000', 'CD201810121924458689782', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (483, 'COINSR201700000000000000', 'CD201810121928269616076', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (484, 'COINSR201700000000000000', 'CD201810121937291076592', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (485, 'COINSR201700000000000000', 'CD201810121938457323540', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (486, 'COINSR201700000000000000', 'CD201810121941478257959', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (487, 'COINSR201700000000000000', 'CD201810121951119121635', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (488, 'COINSR201700000000000000', 'CD201810121955202915122', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (489, 'COINSR201700000000000000', 'CD201810121957172842697', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (490, 'COINSR201700000000000000', 'CD201810182301539934985', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (491, 'COINSR201700000000000000', 'COINSM201612071021105964', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (492, 'COINSR201700000000000000', 'COINSM2017101716450533995', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (493, 'COINSR201700000000000000', 'COINSM2017101719100760088', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (494, 'COINSR201700000000000000', 'COINSM2017121215543215610', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (495, 'COINSR201700000000000000', 'CD201809281504013431237', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (496, 'COINSR201700000000000000', 'CD201810121925244817607', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (497, 'COINSR201700000000000000', 'CD201810121928434749777', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (498, 'COINSR201700000000000000', 'CD201810121939078841561', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (499, 'COINSR201700000000000000', 'CD201810121942099449133', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (500, 'COINSR201700000000000000', 'COINSM2017033020024827112', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (501, 'COINSR201700000000000000', 'COINSM201707251006045006005', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (502, 'COINSR201700000000000000', 'COINSM2017101719110981215', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (503, 'COINSR201700000000000000', 'CD201809281504368347161', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (504, 'COINSR201700000000000000', 'CD201810121939249947233', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (505, 'COINSR201700000000000000', 'CD201810121942285361952', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (506, 'COINSR201700000000000000', 'CD201810121959550577450', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (507, 'COINSR201700000000000000', 'CD201810121926156378556', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
INSERT INTO `tsys_menu_role` VALUES (508, 'COINSR201700000000000000', 'CD201810212309012784091', 'UCOIN201700000000000001', '2018-10-21 16:57:41', NULL, 'CD-MISS000030');
COMMIT;

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
-- Records of tsys_role
-- ----------------------------
BEGIN;
INSERT INTO `tsys_role` VALUES ('COINSR201700000000000000', '超级管理员', 'admin', '2018-10-25 23:36:31', '', 'CD-MISS000030');
COMMIT;

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

-- ----------------------------
-- Records of tsys_user
-- ----------------------------
BEGIN;
INSERT INTO `tsys_user` VALUES ('UCOIN201700000000000001', 'COINSR201700000000000000', NULL, NULL, NULL, NULL, 'admin', '21218cca77804d2ba1922c33e0151105', '1', '2018-10-25 23:36:31', '0', 'admin', NULL, '管理端系统方', 'CD-MISS000030');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
