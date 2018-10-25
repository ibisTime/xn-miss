ALTER TABLE `tstd_user` 
CHANGE COLUMN `nickname` `nickname` VARCHAR(255) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL COMMENT '昵称' ;

ALTER TABLE `thqxj_read` 
CHANGE COLUMN `code` `id` BIGINT(32) NOT NULL AUTO_INCREMENT COMMENT 'Id' ;

UPDATE `tsys_dict` SET `dkey` = '南京赛区' WHERE (`id` = '149');


INSERT INTO `tsys_config` (`type`, `ckey`, `cvalue`, `updater`, `update_datetime`, `remark`) VALUES ('sys_txt', 'first_chat', '欢迎使用客服,有什么可以帮助到您', 'admin', '2018-10-20 14:29:38', '创建会话第一条消息');


