CREATE DATABASE  IF NOT EXISTS `forum_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `forum_db`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 192.168.190.131    Database: forum_db
-- ------------------------------------------------------
-- Server version	5.7.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_article`
--

DROP TABLE IF EXISTS `t_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `boardId` bigint(20) NOT NULL COMMENT '关联板块编号',
  `userId` bigint(20) NOT NULL COMMENT '发帖人，关联用户编号',
  `title` varchar(100) NOT NULL COMMENT '帖子标题',
  `content` text NOT NULL COMMENT '帖子正文',
  `visitCount` int(11) NOT NULL DEFAULT '0' COMMENT '访问量',
  `replyCount` int(11) NOT NULL DEFAULT '0' COMMENT '回复数',
  `likeCount` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article`
--

LOCK TABLES `t_article` WRITE;
/*!40000 ALTER TABLE `t_article` DISABLE KEYS */;
INSERT INTO `t_article` VALUES (2,1,2,'啊啊啊啊啊啊啊啊啊啊啊啊','啊啊啊啊啊啊啊啊啊啊啊啊',12,0,0,0,1,'2025-02-24 17:30:40','2025-02-25 20:45:33'),(3,1,2,'123123','123123',1,5,0,0,1,'2025-02-24 19:14:58','2025-02-28 16:54:35'),(4,2,2,'123','123',0,0,0,0,0,'2025-02-24 19:48:14','2025-02-24 19:48:14'),(5,2,2,'1233','1233',0,0,0,0,0,'2025-02-24 19:48:21','2025-02-24 19:48:21'),(6,3,2,'111','111',2,0,18,0,1,'2025-02-24 19:48:29','2025-02-25 21:27:44'),(7,4,2,'22212321','22212312312',2,0,0,0,0,'2025-02-24 19:48:36','2025-02-28 18:03:53'),(8,5,2,'333','333',0,0,0,0,0,'2025-02-24 19:48:43','2025-02-24 19:48:43'),(9,1,2,'哈哈哈哈','哈哈哈哈',2,0,0,0,0,'2025-02-24 20:05:50','2025-02-24 20:05:50'),(10,9,2,'灌水','灌水区',5,2,0,0,0,'2025-02-24 20:47:07','2025-02-28 16:59:04'),(11,1,2,'444','444',2,0,0,0,0,'2025-02-24 20:47:34','2025-02-24 20:47:34'),(12,1,2,'灌水灌水啊啊啊啊啊啊','啊啊啊啊啊啊啊啊啊啊啊啊啊啊',23,1,18,0,0,'2025-02-24 21:06:38','2025-02-26 17:36:58'),(13,1,3,'123123123123','123123',1,0,16,0,0,'2025-02-25 17:01:20','2025-02-25 21:27:37'),(14,9,3,'123123','123123',14,4,12,0,0,'2025-02-25 17:01:29','2025-02-28 16:58:43');
/*!40000 ALTER TABLE `t_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_reply`
--

DROP TABLE IF EXISTS `t_article_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `articleId` bigint(20) NOT NULL COMMENT '关联帖子编号',
  `postUserId` bigint(20) NOT NULL COMMENT '楼主用户，关联用户编号',
  `replyId` bigint(20) DEFAULT NULL COMMENT '关联回复编号，支持楼中楼',
  `replyUserId` bigint(20) DEFAULT NULL COMMENT '楼主下的回复用户编号，支持楼中楼',
  `content` varchar(500) NOT NULL COMMENT '回贴内容',
  `likeCount` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_reply`
--

LOCK TABLES `t_article_reply` WRITE;
/*!40000 ALTER TABLE `t_article_reply` DISABLE KEYS */;
INSERT INTO `t_article_reply` VALUES (1,12,2,NULL,NULL,'123',0,0,0,'2025-02-26 17:36:58','2025-02-26 17:36:58'),(2,14,2,NULL,NULL,'阿斯顿发射点发射点',0,0,0,'2025-02-26 17:38:14','2025-02-26 17:38:14'),(3,3,2,NULL,NULL,'哈哈哈哈',0,0,0,'2025-02-28 16:54:27','2025-02-28 16:54:27'),(4,3,2,NULL,NULL,'哈哈哈哈1',0,0,0,'2025-02-28 16:54:32','2025-02-28 16:54:32'),(5,3,2,NULL,NULL,'哈哈哈哈1',0,0,0,'2025-02-28 16:54:33','2025-02-28 16:54:33'),(6,3,2,NULL,NULL,'哈哈哈哈1',0,0,0,'2025-02-28 16:54:34','2025-02-28 16:54:34'),(7,3,2,NULL,NULL,'哈哈哈哈1',0,0,0,'2025-02-28 16:54:35','2025-02-28 16:54:35'),(8,14,2,NULL,NULL,'123123',0,0,0,'2025-02-28 16:58:33','2025-02-28 16:58:33'),(9,14,2,NULL,NULL,'123123',0,0,0,'2025-02-28 16:58:35','2025-02-28 16:58:35'),(10,14,2,NULL,NULL,'123123',0,0,0,'2025-02-28 16:58:37','2025-02-28 16:58:37'),(11,10,2,NULL,NULL,'不好看',0,0,0,'2025-02-28 16:58:52','2025-02-28 16:58:52'),(12,10,2,NULL,NULL,'你感觉好看不',0,0,0,'2025-02-28 16:59:04','2025-02-28 16:59:04');
/*!40000 ALTER TABLE `t_article_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_board`
--

DROP TABLE IF EXISTS `t_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `name` varchar(50) NOT NULL COMMENT '板块名',
  `articleCount` int(11) NOT NULL DEFAULT '0' COMMENT '帖子数量',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序优先级，升序',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_board`
--

LOCK TABLES `t_board` WRITE;
/*!40000 ALTER TABLE `t_board` DISABLE KEYS */;
INSERT INTO `t_board` VALUES (1,'Java',5,1,0,0,'2023-01-14 19:02:18','2025-02-28 18:03:44'),(2,'C++',2,2,0,0,'2023-01-14 19:02:41','2025-02-24 19:48:21'),(3,'前端技术',0,3,0,0,'2023-01-14 19:02:52','2025-02-28 18:03:37'),(4,'MySQL',1,4,0,0,'2023-01-14 19:03:02','2025-02-24 19:48:36'),(5,'面试宝典',1,5,0,0,'2023-01-14 19:03:24','2025-02-24 19:48:43'),(6,'经验分享',0,6,0,0,'2023-01-14 19:03:48','2023-01-14 19:03:48'),(7,'招聘信息',0,7,0,0,'2023-01-25 21:25:33','2023-01-25 21:25:33'),(8,'福利待遇',0,8,0,0,'2023-01-25 21:25:58','2023-01-25 21:25:58'),(9,'灌水区',2,9,0,0,'2023-01-25 21:26:12','2025-02-25 17:01:29');
/*!40000 ALTER TABLE `t_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_message`
--

DROP TABLE IF EXISTS `t_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `postUserId` bigint(20) NOT NULL COMMENT '发送者，关联用户编号',
  `receiveUserId` bigint(20) NOT NULL COMMENT '接收者，关联用户编号',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_message`
--

LOCK TABLES `t_message` WRITE;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;
INSERT INTO `t_message` VALUES (1,3,2,'123',2,0,'2025-03-01 16:25:37','2025-03-01 19:47:20'),(2,2,3,'123',2,0,'2025-03-01 16:30:31','2025-03-01 19:39:02'),(3,3,2,'123123',2,0,'2025-03-01 16:31:58','2025-03-01 19:38:54'),(4,2,2,'啊哈哈哈哈',2,0,'2025-03-01 19:38:41','2025-03-01 19:38:47'),(5,2,2,'哈哈',2,0,'2025-03-01 19:38:54','2025-03-01 19:46:33'),(6,3,3,'aaa',2,0,'2025-03-01 19:39:02','2025-03-01 19:46:45'),(7,2,2,'爱的色放',2,0,'2025-03-01 19:46:33','2025-03-01 19:47:11'),(8,3,3,'aaaa',0,0,'2025-03-01 19:46:45','2025-03-01 19:46:45'),(9,3,2,'asdfasdf',2,0,'2025-03-01 19:46:55','2025-03-01 19:47:06'),(10,2,3,'as',0,0,'2025-03-01 19:47:06','2025-03-01 19:47:06'),(11,2,3,'阿斯蒂芬',0,0,'2025-03-01 19:47:20','2025-03-01 19:47:20');
/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `username` varchar(20) NOT NULL COMMENT '用户名，唯一',
  `password` varchar(32) NOT NULL COMMENT '加密后的密码',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `phoneNum` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `gender` tinyint(4) NOT NULL DEFAULT '2' COMMENT '性别 0女，1男，2保密',
  `salt` varchar(32) NOT NULL COMMENT '为密码加盐',
  `avatarUrl` varchar(255) DEFAULT NULL COMMENT '用户头像路径',
  `articleCount` int(11) NOT NULL DEFAULT '0' COMMENT '发帖数量',
  `isAdmin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否管理员 0否，1是',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注，自我介绍',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁言',
  `deleteState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'bitboy','123456','小比\n特','',NULL,2,'123','avatar.png',0,1,NULL,0,0,'2022-12-13 22:30:10','2022-12-13 22:30:13'),(2,'zhangsan','0f4d6844c296ab603b7b5dbfe4a2edec','我是张三','123123123123123','1231231231231123123',2,'0db0ac8f1534479ab0dbfcee27d2d35a','avatar.png',8,0,'12312312312123123',0,0,'2025-02-21 16:11:31','2025-02-28 18:03:44'),(3,'lisi','e6a239302fc2b7fc27a36a7031580803','lisi',NULL,NULL,2,'210f2111db7a4b47bd494ac6af11e97f',NULL,2,0,NULL,0,0,'2025-02-21 16:30:12','2025-02-25 17:01:29'),(4,'wangwu','3e720a25e502f022da81a42fe550983b','wangwu',NULL,NULL,2,'f378e487e7b84aaea188e7eb0ef0c275',NULL,0,0,NULL,0,0,'2025-02-21 17:09:07','2025-02-21 17:09:07'),(5,'test111','af3b523daf32b12228d477348376fb9a','test111',NULL,NULL,2,'10061343e3ed46ba9ea7fd283f275097',NULL,0,0,NULL,0,0,'2025-02-21 17:10:13','2025-02-21 17:10:13'),(6,'zhaoliu','db154b7f64b57b629c19712ad41af258','zhaoliu',NULL,NULL,2,'ec13ece0928045db9442d4b835f3849a',NULL,0,0,NULL,0,0,'2025-02-22 21:48:06','2025-02-22 21:48:06'),(7,'<string>','48a2e171b28ac91606c6a72d251e4365','<string>',NULL,NULL,2,'7c84cf4a0ca94d4b9c46b71bfe3fb512',NULL,0,0,NULL,0,0,'2025-02-23 21:05:40','2025-02-23 21:05:40');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-01 19:54:09
