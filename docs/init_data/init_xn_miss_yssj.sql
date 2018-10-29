/*
-- Query: SELECT `code`,`question`,`answer`, `status`,now() as `create_datetime`,updater,now() as `update_datetime`,`remark` FROM thqxj_answer
LIMIT 0, 1000

-- Date: 2018-10-22 00:33
*/
INSERT INTO `thqxj_answer` (`code`,`question`,`answer`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('AN201810211958503324015','如何充值','打开系统，点击“我的----账户管理”打开界面，点击“充值”，按步骤完成微信充值。','1','2018-10-21 16:33:09','admin','2018-10-21 16:33:09','上架回复模版');
INSERT INTO `thqxj_answer` (`code`,`question`,`answer`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('AN201810212000552262875','为什么要绑定手机号','用户绑定手机号，可以让平台更快更精准的了解用户，提高服务质量。','1','2018-10-21 16:33:09','admin','2018-10-21 16:33:09','上架回复模版');
INSERT INTO `thqxj_answer` (`code`,`question`,`answer`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`) VALUES ('AN201810212002055296316','什么是交易密码','交易密码，是在动用平台用户账户余额时，必须输入的密码。它是保障用户资金安全的重要环节。请牢记且勿告知他人。','1','2018-10-21 16:33:09','admin','2018-10-21 16:33:09','上架回复模版');



/*
-- Query: SELECT `word`,`level`,`reaction`,`updater`,now() as `update_datetime`,`remark` FROM thqxj_keyword
LIMIT 0, 1000

-- Date: 2018-10-22 00:39
*/
INSERT INTO `thqxj_keyword` (`word`,`level`,`reaction`,`updater`,`update_datetime`,`remark`) VALUES ('政治','0','3','admin','2018-10-21 16:39:11','初始化数据');
INSERT INTO `thqxj_keyword` (`word`,`level`,`reaction`,`updater`,`update_datetime`,`remark`) VALUES ('经济','0','3','admin','2018-10-21 16:39:11','初始化数据');
INSERT INTO `thqxj_keyword` (`word`,`level`,`reaction`,`updater`,`update_datetime`,`remark`) VALUES ('习近平','0','3','admin','2018-10-21 16:39:11','初始化数据');


/*
-- Query: SELECT * FROM xn_miss.thqxj_player
-- Date: 2018-10-29 21:22
*/
INSERT INTO `thqxj_player` (`code`,`match`,`match_play_code`,`cname`,`ename`,`native_place`,`height`,`weight`,`xwei`,`ywei`,`twei`,`description`,`list_pic`,`banner_pics`,`pics`,`location`,`order_no`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`,`ticket_sum`,`attention_sum`,`share_sum`,`scan_sum`) VALUES ('P201810212016459583633','南京赛区','0001','罗紫琳','Roseline','上海','175','45','120','70','90','罗紫琳征战巴西,在圣保罗举办的第60届环球小姐全球总决 赛上获得第5名,作为中国小姐,罗紫琳被数亿网友评为 “最具人气佳丽”。','FuSuzpzDdCZeTety1owUkjCT0vgB','FuSuzpzDdCZeTety1owUkjCT0vgB','FuSuzpzDdCZeTety1owUkjCT0vgB','0',1,'4','2018-10-21 20:16:45','admin','2018-10-21 20:17:09','初始化数据',0,0,0,0);
INSERT INTO `thqxj_player` (`code`,`match`,`match_play_code`,`cname`,`ename`,`native_place`,`height`,`weight`,`xwei`,`ywei`,`twei`,`description`,`list_pic`,`banner_pics`,`pics`,`location`,`order_no`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`,`ticket_sum`,`attention_sum`,`share_sum`,`scan_sum`) VALUES ('P201810212021420536941','南京赛区','0002','许继丹','Diana','吉林辽源','180','50','34','25','36','许继丹参加61届美国拉斯维加斯总决赛获得“最佳民族服 饰奖”,现任环球小姐大赛中国区组委会执行经理。','FkLlvXjb-YeFbvTvWQa6wXD1axec','FkLlvXjb-YeFbvTvWQa6wXD1axec','FkLlvXjb-YeFbvTvWQa6wXD1axec','0',2,'4','2018-10-21 20:21:42','admin','2018-10-21 20:22:11','初始化数据',0,0,0,0);
INSERT INTO `thqxj_player` (`code`,`match`,`match_play_code`,`cname`,`ename`,`native_place`,`height`,`weight`,`xwei`,`ywei`,`twei`,`description`,`list_pic`,`banner_pics`,`pics`,`location`,`order_no`,`status`,`create_datetime`,`updater`,`update_datetime`,`remark`,`ticket_sum`,`attention_sum`,`share_sum`,`scan_sum`) VALUES ('P201810212025539515477','南京赛区','0007','秦美苏','Meisu Q','辽宁','178','50','34','25','36','学校与专业:加拿大阿尔伯塔大学 \n兴趣爱好:欧美爵士舞、健身(有氧运动&拳击)、流行歌曲、旅游\n过往经历:曾是健康明朗的Top.10足球宝贝;参加环姐之前曾经以模特 身份在加拿大,意大利米兰和韩国工作;2017年世界小姐中国区才艺单 项赛十强;超模单项赛十强;网络人气十强;BaByLiss时尚美发大使及 品牌全年代言人。','FuRAN4JDNjij3zBz47AOal5zine_','FuRAN4JDNjij3zBz47AOal5zine_','FuRAN4JDNjij3zBz47AOal5zine_','0',3,'4','2018-10-21 20:25:53','admin','2018-10-21 20:26:16','初始化数据',0,0,0,0);

/*
-- Query: SELECT * FROM test_xn_miss.tsys_cnavigate
LIMIT 0, 1000

-- Date: 2018-10-22 00:46
*/
INSERT INTO `tsys_cnavigate` (`code`,`name`,`type`,`url`,`pic`,`status`,`location`,`order_no`,`parent_code`,`remark`) VALUES ('DH201810211945216783706','中国美大使','2','','lhXGWhfN7mFgWwfDb5GeeoAV95JW','1','index_banner',1,'0','');

/*
-- Query: SELECT * FROM xn_miss.tstd_bankcard
-- Date: 2018-10-29 01:31
*/
INSERT INTO `tstd_bankcard` (`code`,`bankcard_number`,`bank_code`,`bank_name`,`subbranch`,`bind_mobile`,`user_id`,`real_name`,`type`,`status`,`currency`,`amount`,`frozen_amount`,`md5`,`create_datetime`,`remark`,`last_order`,`system_code`) VALUES ('BC201810290130510678755','622202221323222','CITIC','中信银行','杭州支行',NULL,'SYS_USER','环球小姐','1','1','CNY',10000,0,NULL,now(),'',NULL,'CD-MISS000030');

