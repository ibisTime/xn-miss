ALTER TABLE `tstd_user` 
CHANGE COLUMN `nickname` `nickname` VARCHAR(255) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL COMMENT '昵称' ;

ALTER TABLE `thqxj_read` 
CHANGE COLUMN `code` `id` BIGINT(32) NOT NULL AUTO_INCREMENT COMMENT 'Id' ;

UPDATE `tsys_dict` SET `dkey` = '南京赛区' WHERE (`id` = '149');


