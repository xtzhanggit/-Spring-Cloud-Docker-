CREATE DATABASE house;
use house;

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city_name` varchar(11) NOT NULL DEFAULT '' COMMENT '城市名称',
  `city_code` varchar(10) NOT NULL DEFAULT '' COMMENT '城市编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `city` (`id`, `city_name`, `city_code`)
VALUES
	(1,'北京市','110000');

DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city_code` varchar(11) NOT NULL DEFAULT '' COMMENT '城市编码',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '小区名称',
  `city_name` varchar(11) NOT NULL DEFAULT '' COMMENT '城市名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `community` (`id`, `city_code`, `name`, `city_name`)
VALUES
	(1,'110000','西山华府','北京市'),
	(2,'110000','万柳书苑','北京市'),
	(3,'110000','太阳公元','北京市'),
	(4,'110000','橡树湾','北京市'),
	(5,'110000','阳光丽景','北京市'),
	(6,'110000','紫苑华府','北京市'),
	(7,'110000','北街嘉园','北京市');

DROP TABLE IF EXISTS `house`;
CREATE TABLE `house` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '房产名称',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1:销售，2:出租',
  `price` int(11) NOT NULL COMMENT '单元元',
  `images` varchar(1024) NOT NULL DEFAULT '' COMMENT '图片地址',
  `area` int(11) NOT NULL DEFAULT '0' COMMENT '面积',
  `beds` int(11) NOT NULL DEFAULT '0' COMMENT '卧室数量',
  `baths` int(11) NOT NULL DEFAULT '0' COMMENT '卫生间数量',
  `rating` double NOT NULL DEFAULT '0' COMMENT '评级',
  `remarks` varchar(512) NOT NULL DEFAULT '' COMMENT '房产描述',
  `properties` varchar(512) NOT NULL DEFAULT '' COMMENT '属性',
  `floor_plan` varchar(250) NOT NULL DEFAULT '' COMMENT '户型图',
  `tags` varchar(20) NOT NULL DEFAULT '' COMMENT '标签',
  `create_time` date NOT NULL COMMENT '创建时间',
  `city_id` int(11) NOT NULL DEFAULT '0' COMMENT '城市名称',
  `community_id` int(11) NOT NULL DEFAULT '0' COMMENT '小区名称',
  `address` varchar(20) NOT NULL DEFAULT '' COMMENT '房产地址',
  `state` tinyint(1) DEFAULT '1' COMMENT '1-上架，2-下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `house` (`id`, `name`, `type`, `price`, `images`, `area`, `beds`, `baths`, `rating`, `remarks`, `properties`, `floor_plan`, `tags`, `create_time`, `city_id`, `community_id`, `address`, `state`)
VALUES
	(22,'西山华府 120平',1,600,'/1493370993/property-07.jpg,/1493370999/property-08.jpg',120,2,12,5,'西山华府 120平西山华府 120平西山华府 120平西山华府 120平西山华府 120平','得房率高,户型好,落地窗','','','2017-04-28 00:00:00',1,1,'西山华府',1),
	(23,'万柳书苑 180平 南北通透',1,800,'/1493381459/property-detail-01.jpg,/1493381460/property-detail-02.jpg,/1493381462/property-detail-03.jpg',120,2,2,4.5,'万柳书苑 180平 南北通透','满五年,采光好,价格合理,税少,学区房','','','2017-04-28 00:00:00',1,2,'清河中街',1),
	(24,'阳光丽景 三面采光 高楼层',1,140,'/1493432771/property-11.jpg,/1493432771/property-12.jpg,/1493432771/property-13.jpg',140,2,2,2.5,'阳光丽景 三面采光 高楼层','南北通透,环境好,带阳台','/1493432771/floor-plan-01.jpg,/1493432771/floor-plan-02.jpg','','2017-04-29 00:00:00',1,5,'西城区',1),
	(25,'阳光丽景 全南 高楼层',1,140,'/1493432771/property-11.jpg,/1493432771/property-12.jpg,/1493432771/property-13.jpg',140,2,2,0,'阳光丽景 三面采光 高楼层','南北通透,环境好,带阳台','/1493432771/floor-plan-01.jpg,/1493432771/floor-plan-02.jpg','','2017-04-29 00:00:00',1,5,'西城区',1),
	(26,'北街嘉园 全南向 南北通透',1,800,'/1493381459/property-detail-01.jpg,/1493381460/property-detail-02.jpg,/1493381462/property-detail-03.jpg',120,2,2,0,'万柳书苑 180平 南北通透','满五年,采光好,价格合理,税少,学区房','','','2017-04-28 00:00:00',1,2,'清河中街',1),
	(27,'橡树湾 全南向 南北通透',2,1,'/1493381459/property-detail-01.jpg,/1493381460/property-detail-02.jpg,/1493381462/property-detail-03.jpg',120,2,2,4,'生活方便','采光好','','','2017-04-28 00:00:00',1,2,'清河中街',1),
	(30,'南北通透好三居',1,200,'/1500796444/property-12.jpg,/1500796444/property-13.jpg',2,3,2,0,'南北通透好三居','环境好,带阳台,临地铁','/1500796444/floor-plan-02.jpg','','2017-07-23 00:00:00',1,6,'紫苑华府',1),
	(31,'好三居采光充足',1,200,'/1500800727/property-12.jpg,/1500800727/property-13.jpg',100,3,2,0,'好三居采光充足','','/1500800727/floor-plan-02.jpg','','2017-07-23 00:00:00',1,7,'清河中街',1),
	(32,'好三居采光充足',1,200,'/1500800766/property-04.jpg,/1500800766/property-05.jpg,/1500800766/property-06.jpg',100,3,2,0,'好三居采光充足','','/1500800766/floor-plan-01.jpg','','2017-07-23 00:00:00',1,7,'清河中街',1),
	(33,'好三居采光充足',1,200,'/1500800883/property-09.jpg,/1500800883/property-10.jpg',100,3,2,0,'好三居采光充足','','/1500800883/floor-plan-02.jpg','','2017-07-23 00:00:00',1,7,'清河中街',1),
	(34,'南北通透好三居',1,200,'/1500800967/property-10.jpg,/1500800967/property-11.jpg',100,3,2,0,'南北通透好三居','','/1500800967/floor-plan-02.jpg','','2017-07-23 00:00:00',1,6,'清河中街',1),
	(35,'南北通透好三居',1,200,'/1500801115/property-09.jpg,/1500801115/property-10.jpg,/1500801115/property-11.jpg',100,3,2,0,'南北通透好三居','','/1500801115/floor-plan-01.jpg','','2017-07-23 00:00:00',1,6,'清河中街',1),
	(36,'南北通透好三居',1,200,'/1500801204/property-10.jpg,/1500801204/property-11.jpg',100,3,2,0,'111','','/1500801204/floor-plan-01.jpg','','2017-07-23 00:00:00',1,7,'22',1),
	(37,'南北通透好三居',1,300,'/1500801346/property-10.jpg',100,3,2,0,'撒的发达','','/1500801346/floor-plan-02.jpg','','2017-07-23 00:00:00',1,6,'撒发达',1),
	(38,'新增房产 阳光充足',1,200,'/1500801594/property-06.jpg,/1500801594/property-07.jpg',100,3,2,0,'新增房产新增房产新增房产','','/1500801594/floor-plan-02.jpg','','2017-07-23 00:00:00',1,4,'清河中街',1),
	(39,'南北通透好三居 采光充足',1,300,'/1500803086/property-13.jpg',100,3,2,5,'1111','','/1500803086/floor-plan-02.jpg','','2017-07-23 17:44:47',1,4,'清河中街',1),
	(40,'中央花园大三居',1,200,'/1514722627/property-09.jpg,/1514722627/property-08.jpg,/1514722627/property-07.jpg',200,3,3,0,'中央花园大三居，阳光好房','满两年,采光好,价格合理,楼龄新,税少,户型好','/1514722627/floor-plan-02.jpg','','2017-12-31 20:17:07',1,6,'抚顺路北大街',1),
	(41,'阳光花园大四居',1,300,'/1514727258/property-06.jpg,/1514727258/property-05.jpg,/1514727258/property-04.jpg',200,4,4,3,'阳光花园大四居阳光花园大四居阳光花园大四居','带阳台,临地铁,没有遮挡,精装修','/1514727258/floor-plan-01.jpg','','2017-12-31 21:34:18',1,7,'北新家园101',1),
	(42,'阳光花园大四居',1,300,'/1514727307/property-06.jpg,/1514727307/property-05.jpg,/1514727307/property-04.jpg',200,4,4,4,'阳光花园大四居阳光花园大四居阳光花园大四居','带阳台,临地铁,没有遮挡,精装修','/1514727307/floor-plan-01.jpg','','2017-12-31 21:35:08',1,7,'北新家园101',1),
	(43,'阳光花园大四居',1,300,'/1514727520/property-10.jpg,/1514727520/property-09.jpg,/1514727520/property-08.jpg',200,4,3,0,'阳光花园大四居阳光花园大四居阳光花园大四居','满五年,楼龄新,税少,落地窗','/1514727520/floor-plan-01.jpg','','2017-12-31 21:38:41',1,7,'北新家园101',1),
	(44,'枫丹丽舍大三居',1,300,'/1515216506/property-08.jpg,/1515216506/property-07.jpg,/1515216506/property-06.jpg',200,3,3,0,'枫丹丽舍大三居枫丹丽舍大三居','满五年,满两年,采光好,高楼层,价格合理,楼龄新,税少,得房率高','/1515216506/floor-plan-02.jpg','','2018-01-06 13:28:27',1,1,'西门大街58号',1),
	(45,'万柳书院一楼',1,400,'/1515217056/property-03.jpg,/1515217056/property-02.jpg,/1515217056/property-01.jpg',200,4,4,0,'万柳书院一楼','户型好,没有遮挡,落地窗,精装修','/1515217057/floor-plan-big.jpg','','2018-01-06 13:37:39',1,0,'万柳书院一楼',1);


DROP TABLE IF EXISTS `house_msg`;
CREATE TABLE `house_msg` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `msg` varchar(512) NOT NULL DEFAULT '' COMMENT '消息',
  `create_time` date NOT NULL COMMENT '创建时间',
  `agent_id` bigint(20) NOT NULL COMMENT '经纪人id',
  `house_id` bigint(20) NOT NULL COMMENT '房屋id',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `house_msg` (`id`, `msg`, `create_time`, `agent_id`, `house_id`, `user_name`)
VALUES
	(1,'spring_boot@163.com','2017-02-12',13,5,'sadfasd'),
	(2,'d','2017-03-27',13,9,'111'),
	(3,'1','2017-04-29',7,24,'11'),
	(4,'1','2017-06-29',7,24,'11'),
	(5,'dafsd','2017-07-23',15,39,'hello'),
	(6,'1111','2017-07-23',15,39,'hello'),
	(7,'1111','2018-01-06',15,43,'hello'),
	(8,'111','2018-01-06',15,41,'wwww'),
	(9,'111111','2018-01-06',15,39,'111');

DROP TABLE IF EXISTS `house_user`;
CREATE TABLE `house_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `house_id` bigint(20) NOT NULL COMMENT '房屋id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_time` date NOT NULL COMMENT '创建时间',
  `type` tinyint(1) NOT NULL COMMENT '1-售卖，2-收藏',
  PRIMARY KEY (`id`),
  UNIQUE KEY `house_id_user_id_type` (`house_id`,`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `house_user` (`id`, `house_id`, `user_id`, `create_time`, `type`)
VALUES
	(1,22,7,'2017-02-26',1),
	(11,43,7,'2017-04-28',1),
	(13,42,7,'2017-04-28',1),
	(17,24,7,'2017-04-29',1),
	(18,24,14,'2017-04-29',2),
	(19,23,7,'2017-04-29',2),
	(22,27,7,'2017-04-29',1),
	(23,26,7,'2017-04-29',1),
	(27,30,7,'2017-07-23',1),
	(28,36,7,'2017-07-23',1),
	(29,37,7,'2017-07-23',1),
	(30,38,7,'2017-07-23',1),
	(31,39,7,'2017-07-23',1),
	(33,40,7,'2017-12-31',1),
	(34,23,7,'2017-12-31',1),
	(35,42,7,'2018-01-06',2),
	(39,41,7,'2017-12-31',1),
	(90,25,7,'2017-04-29',1),
	(91,31,7,'2017-07-23',1),
	(92,32,7,'2017-07-23',1),
	(93,33,7,'2017-07-23',1),
	(94,34,7,'2017-07-23',1),
	(95,35,7,'2017-07-23',1),
	(96,44,7,'2018-01-06',1),
	(97,45,7,'2018-01-06',1),
	(101,44,24,'2018-01-06',2),
	(102,45,7,'2018-01-06',2);


