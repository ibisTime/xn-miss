/*
-- Query: SELECT `code`,`question`,`answer`, `status`,now() as `create_datetime`,updater,now() as `update_datetime`,`remark` FROM thqxj_answer
LIMIT 0, 1000

-- Date: 2018-10-22 00:33
*/
INSERT INTO `thqxj_answer` (`code`,`question`,`answer`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('AN201810211958503324015','如何充值','打开系统，点击“我的----账户管理”打开界面，点击“充值”，按步骤完成微信充值。','4','2018-10-21 16:33:09','admin','2018-10-21 16:33:09','上架回复模版');
INSERT INTO `thqxj_answer` (`code`,`question`,`answer`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('AN201810212000552262875','为什么要绑定手机号','用户绑定手机号，可以让平台更快更精准的了解用户，提高服务质量。','4','2018-10-21 16:33:09','admin','2018-10-21 16:33:09','上架回复模版');
INSERT INTO `thqxj_answer` (`code`,`question`,`answer`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('AN201810212002055296316','什么是交易密码','交易密码，是在动用平台用户账户余额时，必须输入的密码。它是保障用户资金安全的重要环节。请牢记且勿告知他人。','4','2018-10-21 16:33:09','admin','2018-10-21 16:33:09','上架回复模版');


/*
-- Query: SELECT `code`,`title`,`content`,`status`, now() as `create_datetime`,`updater`, now() as `update_datetime`,`remark` FROM thqxj_event
LIMIT 0, 1000

-- Date: 2018-10-22 00:36
*/
INSERT INTO `thqxj_event` (`code`,`title`,`content`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('EV201810211948322402724','环球小姐投票系统隆重上线','<p>金秋十月，环球小姐投票系统隆重上线。欢迎第一批用户试用。</p>','4','2018-10-21 16:36:26','admin','2018-10-21 16:36:26','初始化数据');
INSERT INTO `thqxj_event` (`code`,`title`,`content`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('EV201810211951323342776','赛事信息测试数据','<p>我是<span style=\"font-family: &quot;Chinese Quote&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif;\">赛事信息测试数据，快来点击我</span></p>','4','2018-10-21 16:36:26','admin','2018-10-21 16:36:26','初始化数据');


/*
-- Query: SELECT `word`,`level`,`reaction`,`updater`,now() as `update_datetime`,`remark` FROM thqxj_keyword
LIMIT 0, 1000

-- Date: 2018-10-22 00:39
*/
INSERT INTO `thqxj_keyword` (`word`,`level`,`reaction`,`updater`,`update_datetime`,`remark`) VALUES ('政治','0','3','admin','2018-10-21 16:39:11','初始化数据');
INSERT INTO `thqxj_keyword` (`word`,`level`,`reaction`,`updater`,`update_datetime`,`remark`) VALUES ('经济','0','3','admin','2018-10-21 16:39:11','初始化数据');
INSERT INTO `thqxj_keyword` (`word`,`level`,`reaction`,`updater`,`update_datetime`,`remark`) VALUES ('习近平','0','3','admin','2018-10-21 16:39:11','初始化数据');


/*
-- Query: SELECT * FROM test_xn_miss.thqxj_player
LIMIT 0, 1000

-- Date: 2018-10-22 00:41
*/
INSERT INTO `thqxj_player` (`code`,`match`,`match_play_code`,`cname`,`ename`,`native_place`,`height`,`weight`,`xwei`,`ywei`,`twei`,`description`,`list_pic`,`banner_pics`,`pics`,`location`,`order_no`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`,`ticket_sum`,`attention_sum`,`share_sum`,`scan_sum`) VALUES ('P201810212016459583633','1','0001','罗紫琳','Roseline','上海','175','45','120','70','90','罗紫琳征战巴西,在圣保罗举办的第60届环球小姐全球总决 赛上获得第5名,作为中国小姐,罗紫琳被数亿网友评为 “最具人气佳丽”。','FuSuzpzDdCZeTety1owUkjCT0vgB','FuSuzpzDdCZeTety1owUkjCT0vgB','FuSuzpzDdCZeTety1owUkjCT0vgB','1',1,'4','2018-10-21 20:16:45','admin','2018-10-21 20:17:09','初始化数据',0,0,0,0);
INSERT INTO `thqxj_player` (`code`,`match`,`match_play_code`,`cname`,`ename`,`native_place`,`height`,`weight`,`xwei`,`ywei`,`twei`,`description`,`list_pic`,`banner_pics`,`pics`,`location`,`order_no`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`,`ticket_sum`,`attention_sum`,`share_sum`,`scan_sum`) VALUES ('P201810212021420536941','1','0002','许继丹','Diana','吉林辽源','180','50','34','25','36','许继丹参加61届美国拉斯维加斯总决赛获得“最佳民族服 饰奖”,现任环球小姐大赛中国区组委会执行经理。','FkLlvXjb-YeFbvTvWQa6wXD1axec','FkLlvXjb-YeFbvTvWQa6wXD1axec','FkLlvXjb-YeFbvTvWQa6wXD1axec','1',2,'4','2018-10-21 20:21:42','admin','2018-10-21 20:22:11','初始化数据',0,0,0,0);
INSERT INTO `thqxj_player` (`code`,`match`,`match_play_code`,`cname`,`ename`,`native_place`,`height`,`weight`,`xwei`,`ywei`,`twei`,`description`,`list_pic`,`banner_pics`,`pics`,`location`,`order_no`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`,`ticket_sum`,`attention_sum`,`share_sum`,`scan_sum`) VALUES ('P201810212025539515477','1','0007','秦美苏','Meisu Q','辽宁','178','50','34','25','36','学校与专业:加拿大阿尔伯塔大学 \n兴趣爱好:欧美爵士舞、健身(有氧运动&拳击)、流行歌曲、旅游\n过往经历:曾是健康明朗的Top.10足球宝贝;参加环姐之前曾经以模特 身份在加拿大,意大利米兰和韩国工作;2017年世界小姐中国区才艺单 项赛十强;超模单项赛十强;网络人气十强;BaByLiss时尚美发大使及 品牌全年代言人。','FuRAN4JDNjij3zBz47AOal5zine_','FuRAN4JDNjij3zBz47AOal5zine_','FuRAN4JDNjij3zBz47AOal5zine_','1',3,'4','2018-10-21 20:25:53','admin','2018-10-21 20:26:16','初始化数据',0,0,0,0);


/*
-- Query: SELECT * FROM test_xn_miss.tsys_cnavigate
LIMIT 0, 1000

-- Date: 2018-10-22 00:46
*/
INSERT INTO `tsys_cnavigate` (`code`,`name`,`type`,`url`,`pic`,`status`,`location`,`order_no`,`parent_code`,`remark`) VALUES ('DH201810211945216783706','中国美大使','2','','lhXGWhfN7mFgWwfDb5GeeoAV95JW','2','index_banner',1,'0','');


/*
-- Query: SELECT bank_code,bank_name,channel_type,status,channel_bank,max_order,order_amount,day_amount,month_amount,remark FROM tsys_channel_bank
-- Date: 2017-08-24 12:26
*/
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('CMBC','中国民生银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('ZJTLCB','浙江泰隆商业银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('ZJCZCB','浙江稠州商业银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('CMB','招商银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('SHB','上海银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('PAB','平安银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('SPDB','浦发银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('CIB','兴业银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('ICBC','中国工商银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('CEB','中国光大银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('CCB','中国建设银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('BCM','中国交通银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('ABC','中国农业银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('BOC','中国银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('PSBC','中国邮政储蓄银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('CITIC','中信银行','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('alipay','支付宝','40','1','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tsys_channel_bank` (`bank_code`,`bank_name`,`channel_type`,`status`,`channel_bank`,`max_order`,`order_amount`,`day_amount`,`month_amount`,`remark`) VALUES ('WeChat','微信','40','1',NULL,NULL,NULL,NULL,NULL,NULL);

/*
-- Query: SELECT `user_id`,`role_code`,`department_code`,`real_name`,`photo`,`mobile`,`login_name`,`login_pwd`,`login_pwd_strength`,`create_datetime`,`status`,`updater`, now() `update_datetime`,`remark`,`system_code` FROM tsys_user where user_id='UCOIN201700000000000001'
LIMIT 0, 1000

-- Date: 2018-09-12 15:16
*/
INSERT INTO `tsys_user` (`user_id`,`role_code`,`department_code`,`real_name`,`photo`,`mobile`,`login_name`,`login_pwd`,`login_pwd_strength`,`create_datetime`,`status`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('UCOIN201700000000000001','COINSR201700000000000000',NULL,NULL,NULL,NULL,'admin','21218cca77804d2ba1922c33e0151105','1',now(),'0','admin',NULL,'管理端系统方','CD-MISS000030');

/*
-- Query: SELECT `code`,`name`,`updater`, now() `update_datetime`,`remark`,`system_code` FROM tsys_role
LIMIT 0, 1000

-- Date: 2018-09-12 15:18
*/
INSERT INTO `tsys_role` (`code`,`name`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','超级管理员','admin',now(),'','CD-MISS000030');

/*
-- Query: SELECT `code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,now() `update_datetime`,`remark`,`system_code` FROM tsys_menu
LIMIT 0, 1000

-- Date: 2018-09-20 02:16
*/
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281458013408308','COINSM201612071021105964','平台账户','1','#',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281458246867562','COINSM201612071021105964','充值管理','1','#',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281458530008193','COINSM201612071021105964','线下取现','1','#',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281504013431237','CD201809281458013408308','账户查询','1','/platform/account.htm',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281504368347161','CD201809281458013408308','流水查询','1','/platform/flows.htm',6,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281507086522935','CD201809281458246867562','线下充值','1','/recharge/recharges.htm',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281507479361586','CD201809281458246867562','充值查询','1','/recharge/records.htm',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281509063426888','CD201809281458530008193','取现规则','1','/withdraw/rules.htm',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281509450622929','CD201809281458530008193','线下取现','1','/withdraw/withdraw.htm',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201809281510256651980','CD201809281458530008193','取现查询','1','/withdraw/records.htm',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810050104422483297','CD201809281507086522935','待申请','2','/add',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810050105195526885','CD201809281507086522935','详情','2','/detail',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810050105538468486','CD201809281507086522935','审核','2','/check',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810050106121877523','CD201809281507086522935','导出','2','/export',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810050154234414919','CD201809281507479361586','详情','2','/detail',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810050154373965588','CD201809281507479361586','导出','2','/export',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810051837019382814','CD201809281509063426888','修改','2','/edit',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810061158329263282','CD201809281509450622929','审核','2','/check',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810061200553476264','CD201809281509450622929','打款回录','2','/enter',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810061201298954721','CD201809281510256651980','详情','2','/detail',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810061201456666951','CD201809281510256651980','导出','2','/export',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121842229668485','COINSM201700001000000002','数据字典管理','1','/system/dataDict.htm',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121843007858513','CD201810121842229668485','新增','2','/add',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121843175244898','CD201810121842229668485','修改','2','/edit',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121916519372324','COINSM201708241036442974134','用户管理','1','#',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121917429845492','COINSM201708241036442974134','赛事管理','1','#',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121918346737635','COINSM201708241036442974134','客服管理','1','#',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121919298833683','COINSM201708241036442974134','评论管理','1','#',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121922124697535','CD201810121916519372324','会员管理','1','/user/users.htm',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121922511425308','CD201810121916519372324','分享记录','1','/user/shares.htm',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121923539243299','CD201810121916519372324','足迹记录','1','/user/footmark.htm',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121924458689782','CD201810121916519372324','关注记录','1','/user/follow.htm',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121925244817607','CD201810121916519372324','加油订单查询','1','/user/orders.htm',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121926156378556','CD201810121916519372324','流水查询','1','/user/flows.htm',7,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121927115959711','CD201810121922124697535','账户查询','2','/accounts',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121927329202713','CD201810121922124697535','激活','2','/active',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121927536639700','CD201810121922124697535','注销','2','/rock',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121928269616076','CD201810121922124697535','详情','2','/detail',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121928434749777','CD201810121922124697535','导出','2','/export',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121929067945387','CD201810121922511425308','导出','2','/export',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121929265826282','CD201810121923539243299','导出','2','/export',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121929532369633','CD201810121925244817607','详情','2','/detail',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121930146804566','CD201810121925244817607','导出','2','/export',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121931077922031','CD201809281504013431237','流水查询','2','/flows',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121931276972672','CD201809281504013431237','导出','2','/export',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121932478717240','CD201810121926156378556','详情','2','/detail',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121933033345103','CD201810121926156378556','导出','2','/export',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121934261473135','CD201810121917429845492','赛事信息管理','1','/game/infos.htm',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121935038614673','CD201810121917429845492','选手管理','1','/game/players.htm',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121935583599708','CD201810121917429845492','总榜管理','1','/game/ranking.htm',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121937291076592','CD201810121917429845492','飙升榜管理','1','/game/soaring.htm',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121937481066102','CD201810121934261473135','新增','2','/add',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121938096934413','CD201810121934261473135','修改','2','/edit',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121938286482068','CD201810121934261473135','审批','2','/check',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121938457323540','CD201810121934261473135','上架','2','/up',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121939078841561','CD201810121934261473135','下架','2','/down',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121939249947233','CD201810121934261473135','详情','2','/detail',6,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121941015015580','CD201810121935038614673','新增','2','/add',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121941142082187','CD201810121935038614673','修改','2','/edit',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121941299117309','CD201810121935038614673','审批','2','/check',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121941478257959','CD201810121935038614673','上架','2','/up',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121942099449133','CD201810121935038614673','下架','2','/down',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121942285361952','CD201810121935038614673','详情','2','/detail',6,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121942566739319','CD201810121935583599708','人工调节','2','/adjust',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121943199691182','CD201810121935583599708','详情','2','/detail',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121943460856990','CD201810121937291076592','人工调节','2','/adjust',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121944021205126','CD201810121937291076592','详情','2','/detail',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121946185283265','CD201810121918346737635','待回复消息','1','/service/unread.htm',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121946481923232','CD201810121918346737635','消息查询','1','/service/messages.htm',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121947359159984','CD201810121918346737635','回复模版','1','/service/template.htm',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121948219653901','CD201810121946185283265','回复','2','/reply',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121949254277591','CD201810121946481923232','详情','2','/detail',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121949468396367','CD201810121947359159984','新增','2','/add',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121950061402481','CD201810121947359159984','修改','2','/edit',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121950193818892','CD201810121947359159984','审批','2','/check',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121950357064231','CD201810121947359159984','上架','2','/up',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121950544603346','CD201810121947359159984','下架','2','/down',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121951119121635','CD201810121947359159984','详情','2','/detail',6,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121952062771760','CD201810121919298833683','关键字设置','1','/comment/keywords.htm',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121952293923697','CD201810121919298833683','评论审核','1','/comment/check.htm',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121953152201939','CD201810121919298833683','评论查询','1','/comment/list.htm',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121953307858741','CD201810121952062771760','新增','2','/add',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121953309098143','CD201810121952062771760','修改','2','/edit',2,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121955029358419','CD201810121952062771760','删除','2','/delete',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121955202915122','CD201810121952062771760','导入','2','/import',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121955393392765','CD201810121952293923697','审核','2','/check',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121956101801874','CD201810121953152201939','详情','2','/detail',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121957172842697','COINSM2017033020005366333','撤下','2','/down',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810121959550577450','CD201810121916519372324','账户查询','1','/user/accounts.htm',6,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810122001591962717','CD201810121959550577450','流水查询','2','/flows',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810131047538286284','CD201810121924458689782','导出','2','/export',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810182259538052129','CD201809281458246867562','线下充值帐号','1','/recharge/accounts.htm',0,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810182300338676052','CD201810182259538052129','新增','2','/add',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810182300585245762','CD201810182259538052129','启用','2','/up',2,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810182301361166056','CD201810182259538052129','未启用','2','/down',3,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('CD201810182301539934985','CD201810182259538052129','详情','2','/detail',4,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201612071021105964','COINSM201700000000000000','财务管理','1','#',4,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201700000000000000','','根目录','1','#',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201700001000000001','COINSM201700000000000000','系统管理','1','#',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201700001000000002','COINSM201700001000000001','运维管理','1','#',2,'admin','2018-10-18 16:23:15',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201700001000000003','COINSM201700001000000002','菜单管理','1','/system/menu.htm',1,'admin','2018-10-18 16:23:15',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201700001000000004','COINSM201700001000000003','新增','2','/add',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017032911200961325','COINSM201700001000000003','修改','2','/edit',2,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017033020005366333','COINSM201707251006045006005','banner管理','1','/public/banner.htm',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017033020015631166','COINSM2017033020005366333','新增','2','/add',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017033020021094115','COINSM2017033020005366333','修改','2','/edit',2,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017033020022649991','COINSM2017033020005366333','发布','2','/up',3,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017033020024827112','COINSM2017033020005366333','详情','2','/detail',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201707251006045006005','COINSM201708241036442974134','广告位管理','1','#',5,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201708241024194086655','COINSM201700001000000003','删除','2','/delete',3,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM201708241036442974134','COINSM201700000000000000','业务管理','1','#',2,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101716241339082','COINSM201700001000000001','运营管理','1','#',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101716253866426','COINSM2017101716241339082','角色管理','1','/system/role.htm',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101716261754674','COINSM2017101716241339082','用户管理','1','/system/user.htm',2,'admin','2018-10-18 16:23:15',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101716450533995','COINSM2017101716253866426','分配菜单','2','/change',4,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101717551955993','COINSM2017101716253866426','新增','2','/add',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101717560118734','COINSM2017101716253866426','修改','2','/edit',2,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101717563661357','COINSM2017101716253866426','删除','2','/delete',3,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101719082391126','COINSM2017101716261754674','新增','2','/add',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101719094151894','COINSM2017101716261754674','重置密码','2','/reset',2,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101719100760088','COINSM2017101716261754674','激活 / 注销','2','/rock',4,'admin','2018-10-18 16:23:15',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017101719110981215','COINSM2017101716261754674','设置角色','2','/assign',5,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSM2017121215543215610','COINSM201700001000000001','文章管理','1','#',4,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('SM201711081400404895487','SM201711081111547852084','修改','2','/edit',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('SM201711181220013316605','COINSM2017121215543215610','注册协议','1','/public/registrationAgreement.htm',1,'UCOIN201700000000000001','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('SM201711232222591772487','SM201711231353203735078','修改','2','/edit',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('SM201711232223319261758','SM201711231354531532054','修改','2','/edit',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');
INSERT INTO `tsys_menu` (`code`,`parent_code`,`name`,`type`,`url`,`order_no`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('SM201711251639362545256','SM201711251639118496237','修改','2','/edit',1,'admin','2018-10-18 16:23:15','','CD-MISS000030');


/*
-- Query: SELECT `role_code`,`menu_code`,`updater`,now() as `update_datetime`,`remark`,`system_code` FROM tsys_menu_role where system_code = 'CD-MISS000030'
LIMIT 0, 1000

-- Date: 2018-09-20 02:14
*/
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281458013408308','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281509063426888','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810050104422483297','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810050154234414919','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810051837019382814','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810061158329263282','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810061201298954721','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121843007858513','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121916519372324','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121922124697535','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121927115959711','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121929067945387','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121929265826282','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121929532369633','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121931077922031','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121932478717240','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121934261473135','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121937481066102','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121941015015580','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121942566739319','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121943460856990','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121946185283265','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121948219653901','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121949254277591','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121949468396367','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121952062771760','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121953307858741','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121955393392765','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121956101801874','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810122001591962717','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810131047538286284','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201700000000000000','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201700001000000001','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201700001000000003','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201700001000000004','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017033020005366333','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017033020015631166','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101716241339082','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101716253866426','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101717551955993','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101719082391126','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','SM201711181220013316605','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','SM201711232222591772487','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','SM201711232223319261758','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','SM201711251639362545256','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281458246867562','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281507086522935','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281509450622929','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810050105538468486','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810050154373965588','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810061200553476264','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810061201456666951','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121842229668485','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121843175244898','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121917429845492','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121922511425308','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121927329202713','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121930146804566','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121931276972672','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121933033345103','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121935038614673','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121938096934413','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121941142082187','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121943199691182','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121944021205126','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121946481923232','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121950061402481','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121952293923697','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121953309098143','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201700001000000002','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017032911200961325','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017033020021094115','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201708241036442974134','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101716261754674','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101717560118734','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101719094151894','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281458530008193','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281507479361586','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281510256651980','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810050105195526885','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121918346737635','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121923539243299','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121927536639700','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121935583599708','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121938286482068','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121941299117309','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121947359159984','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121950193818892','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121953152201939','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121955029358419','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017033020022649991','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201708241024194086655','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101717563661357','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810050106121877523','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121919298833683','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121924458689782','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121928269616076','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121937291076592','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121938457323540','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121941478257959','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121950357064231','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121955202915122','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121957172842697','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201612071021105964','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101716450533995','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101719100760088','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017121215543215610','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281504013431237','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121925244817607','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121928434749777','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121939078841561','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121942099449133','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121950544603346','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017033020024827112','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM201707251006045006005','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','COINSM2017101719110981215','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201809281504368347161','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121939249947233','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121942285361952','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121951119121635','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121959550577450','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810121926156378556','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810182259538052129','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810182300338676052','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810182300585245762','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810182301361166056','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');
INSERT INTO `tsys_menu_role` (`role_code`,`menu_code`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('COINSR201700000000000000','CD201810182301539934985','UCOIN201700000000000001','2018-10-18 16:25:36',NULL,'CD-MISS000030');

/*
-- Query: SELECT `type`,`ckey`,`cvalue`,`updater`,now() as `update_datetime`,`remark`FROM tsys_config
LIMIT 0, 1000

-- Date: 2018-09-11 13:29
*/
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('qiniu','qiniu_access_key','Dc0pMP8ImFm78-uk4iGsOPpB2-vHc64D07OsOQVi','admin','2018-10-20 14:29:38','七牛云key1');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('qiniu','qiniu_secret_key','3NP-tpZP9-5fH-R-FhvKTfYpPPVFNvjFF3JXmrcq','admin','2018-10-20 14:29:38','七牛云key1');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('qiniu','qiniu_bucket','test','admin','2018-10-20 14:29:38','存储空间');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('qiniu','qiniu_domain','ounm8iw2d.bkt.clouddn.com','admin','2018-10-20 14:29:38','访问域名');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('wx_h5','wx_h5_access_key','wx9c709bf30e60c07b\n','admin','2018-10-20 14:29:38',NULL);
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('wx_h5','wx_h5_secret_key','r2jdDFSdiikklwlllejlwjio3232451n','admin','2018-10-20 14:29:38',NULL);
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('sys_txt','REGISTRATION_AGREEMENT','注册协议','admin','2018-10-20 14:29:38','注册协议');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('sys_txt','invite_url','邀请好友链接','admin','2018-10-20 14:29:38','邀请好友链接');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('ticket','price','10','admin','2018-10-20 14:29:38','订单加油票价格(一分钱一票)');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('ticket','invalid_time','30','admin','2018-10-20 14:29:38','订单失效时长（分钟）');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('ticket','return_money','1000','admin','2018-10-20 14:29:38','首次分享返现金额');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('dividend','plat_rate','0.2','admin','2018-10-20 14:29:38','平台加油分成比例');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('dividend','business_rate','0.8','admin','2018-10-20 14:29:38','品牌方加油分成比例');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`) VALUES ('withdraw','fee_rate','0.01','admin','2018-10-20 14:29:38','取现费率');


/*
-- Query: SELECT `type`,`parent_key`,`dkey`,`dvalue`,`updater`,now() as `update_datetime`,`remark`FROM tsys_dict
LIMIT 0, 1000

-- Date: 2018-10-22 00:50
*/
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'role_level','角色等级','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','role_level','1','运维','admin','2018-10-21 16:50:20','');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','role_level','2','运营','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','role_level','3','客户','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'user_status','用户状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','user_status','0','正常','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','user_status','1','程序锁定','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','user_status','2','人工锁定','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'pay_type','支付方式','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','pay_type','1','余额支付','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','pay_type','5','微信h5','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'channel_type','渠道类型','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','channel_type','0','内部账','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','channel_type','90','人工线下','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','channel_type','35','微信H5支付','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'jour_status','流水状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','jour_status','1','待对账','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','jour_status','3','已对账且账已平','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','jour_status','4','帐不平待调账审批','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','jour_status','5','已对账且账不平','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','jour_status','6','无需对账','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'sms_status','公告状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','sms_status','0','草稿','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','sms_status','1','已发送','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','sms_status','2','已撤回','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'approve_status','认证审核状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','approve_status','0','待审核','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','approve_status','1','审核通过','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','approve_status','2','审核不通过','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'banner_location','banner位置','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','banner_location','index_banner','首页','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'player_status','选手状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','player_status','0','草稿','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','player_status','1','待审批','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','player_status','2','审批不通过','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','player_status','3','待上架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','player_status','4','已上架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','player_status','5','已下架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'event_status','赛事状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','event_status','0','草稿','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','event_status','1','待审核','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','event_status','2','审核不通过','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','event_status','3','待上架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','event_status','4','已上架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','event_status','5','已下架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'match','赛区','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','match','1','南京赛区','admin','2018-10-21 16:50:20','');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'ticket_order_status','加油订单状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','ticket_order_status','0','待支付','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','ticket_order_status','1','已支付','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','ticket_order_status','2','超时取消','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','ticket_order_status','3','已取消','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'comment_status','评论状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','comment_status','A','待审核','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','comment_status','B','审核通过','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','comment_status','C','审核不通过','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','comment_status','D','已发布','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','comment_status','G','已删除','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'answer_status','回复模版状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','answer_status','0','草稿','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','answer_status','1','已提交待审批','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','answer_status','2','审批不通过','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','answer_status','3','已审批待上架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','answer_status','4','已上架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','answer_status','5','已下架','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'account_type','账户类型','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','account_type','C','C端用户','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','account_type','B','B端用户','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','account_type','P','平台用户','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'id_kind','证件类型','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','id_kind','1','身份证','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'account_status','账户状态','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','account_status','0','正常',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','account_status','1','程序锁定',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','account_status','2','人工锁定',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'currency','币种',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','currency','CNY','人民币',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'jour_type',NULL,NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','jour_type','0','余额流水',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','jour_type','1','冻结金额流水',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'bankcard_status','银行卡状态',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','bankcard_status','0','未启用',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','bankcard_status','1','启用',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'charge_status','充值状态',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','charge_status','1','待支付',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','charge_status','2','支付失败',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','charge_status','3','支付成功',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'withdraw_status','取现状态',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','withdraw_status','1','待审批',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','withdraw_status','2','审批不通过',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','withdraw_status','3','审批通过待支付',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','withdraw_status','5','支付失败',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','withdraw_status','6','支付成功',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'biz_type','流水业务类型',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','dividend','加油分成',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','first_share','首次分享送钱',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','supply','补给',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','withdraw','取现',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','withdraw_fee','取现手续费',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','pay_channel','支付通道费',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','ticket','加油订单',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','biz_type','charge','充值',NULL,'2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('0',NULL,'com_location','通用的位置','admin','2018-10-21 16:50:20',NULL);
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`) VALUES ('1','com_location','0','首页','admin','2018-10-21 16:50:20',NULL);




/*
-- Query: SELECT * FROM dev_xn_miss.tstd_account
LIMIT 0, 1000

-- Date: 2018-10-17 10:30
*/
INSERT INTO `tstd_account` (`account_number`,`user_id`,`real_name`,`type`,`status`,`currency`,`amount`,`frozen_amount`,`md5`,`add_amount`,`in_amount`,`out_amount`,`create_datetime`,`last_order`,`system_code`,`company_code`) VALUES ('SYS_ACOUNT_B','B_USER','品牌方账户','B','0','CNY',0,0,NULL,0,0,0,now(),NULL,'CD-MISS000030','CD-MISS000030');
INSERT INTO `tstd_account` (`account_number`,`user_id`,`real_name`,`type`,`status`,`currency`,`amount`,`frozen_amount`,`md5`,`add_amount`,`in_amount`,`out_amount`,`create_datetime`,`last_order`,`system_code`,`company_code`) VALUES ('SYS_ACOUNT_CNY','SYS_USER','平台盈亏账户','P','0','CNY',0,0,'8f870b3c1d14e149f0985c74d8443db8',0,0,0,now(),'AJ201810182354209898560','CD-MISS000030','CD-MISS000030');
INSERT INTO `tstd_account` (`account_number`,`user_id`,`real_name`,`type`,`status`,`currency`,`amount`,`frozen_amount`,`md5`,`add_amount`,`in_amount`,`out_amount`,`create_datetime`,`last_order`,`system_code`,`company_code`) VALUES ('SYS_ACOUNT_OFFLINE_TG','SYS_USER','平台线下托管账户','P','0','CNY',0,0,'76c8527bc17bab8fd525cab5e5e4d354',0,0,0,now(),NULL,'CD-MISS000030','CD-MISS000030');
INSERT INTO `tstd_account` (`account_number`,`user_id`,`real_name`,`type`,`status`,`currency`,`amount`,`frozen_amount`,`md5`,`add_amount`,`in_amount`,`out_amount`,`create_datetime`,`last_order`,`system_code`,`company_code`) VALUES ('SYS_ACOUNT_WEIXIN_TG','SYS_USER','平台线下微信托管账户','P','0','CNY',0,0,'5f4f6ea5f603c1c13e45b44f2e26e273',0,0,0,now(),'AJ201810152205517925609','CD-MISS000030','CD-MISS000030');


INSERT INTO `tstd_company_channel` (`company_code`,`company_name`,`channel_type`,`status`,`channel_company`,`private_key1`,`private_key2`,`private_key3`,`private_key4`,`private_key5`,`page_url`,`error_url`,`back_url`,`fee`,`remark`,`system_code`) VALUES ('CD-MISS000030','环球小姐','35','1','1481010032','r2jdDFSdiikklwlllejlwjio3232451n','wx9c709bf30e60c07b','',NULL,NULL,NULL,NULL,'',NULL,NULL,'CD-MISS000030');