-- MySQL dump 10.13  Distrib 5.7.11, for Win64 (x86_64)
--
-- Host: localhost    Database: projectx
-- ------------------------------------------------------
-- Server version	5.7.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend` (
  `id` int(11) DEFAULT NULL,
  `userId` int(6) unsigned DEFAULT NULL,
  `friendId` int(6) unsigned DEFAULT NULL,
  `accepted` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES (0,1,2,1),(0,1,3,1),(0,2,3,1),(0,4,1,0),(0,4,2,1),(0,4,3,0);
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `geo`
--

DROP TABLE IF EXISTS `geo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `geo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `device` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geo`
--

LOCK TABLES `geo` WRITE;
/*!40000 ALTER TABLE `geo` DISABLE KEYS */;
INSERT INTO `geo` VALUES (4,59.83126339999,30.224679699999996,1,'2016-11-21 23:25:55','Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36'),(5,59.851263399999,30.2346796999,1,'2016-11-21 23:26:00','PC'),(6,59.84126339999999,30.224679699999996,2,'2016-11-21 23:28:34','Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36'),(7,59.861263399999,30.2446796999,3,'2016-11-21 23:26:10','PC'),(8,59.861263399999,30.2646796999,3,'2016-11-21 23:26:10','PC'),(9,59.861263399999,30.2846796999,4,'2016-11-21 23:26:20','PC');
/*!40000 ALTER TABLE `geo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(1024) NOT NULL,
  `datemessage` datetime NOT NULL,
  `isread` tinyint(1) NOT NULL,
  `idfrom` int(11) NOT NULL,
  `idto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idfrom` (`idfrom`),
  KEY `idto` (`idto`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'xxx','2016-11-08 00:00:00',0,1,2),(2,'xxx','2016-11-21 23:58:18',0,1,2),(3,'xxx','2016-12-12 20:43:19',0,1,2),(4,'xxx','2016-12-12 20:43:21',1,2,1),(5,'xxx','2016-12-12 20:43:23',1,2,1),(6,'xxxqwd','2016-12-12 20:43:24',1,2,1),(7,'xxx','2016-12-12 20:43:26',0,1,2),(8,'aaa','2016-12-12 20:43:28',1,2,1),(9,'bbb','2016-12-12 20:43:30',0,1,2),(10,'qwe','2016-11-21 23:58:16',0,4,1),(11,'qwe','2016-11-21 23:58:18',0,4,2),(12,'qwe','2016-11-21 23:58:20',0,4,3);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `loggedIn` tinyint(1) NOT NULL DEFAULT '0',
  `sessionId` varchar(16) DEFAULT NULL,
  `ref` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `surname` varchar(32) DEFAULT NULL,
  `color` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'root','63a9f0ea7bb98050796b649e85481845','root@root.root',0,NULL,'YlK+sv@9y68?t1o7(t7LPdbft!8#Hb_s','rnd','toor','111111'),(2,'admin','63a9f0ea7bb98050796b649e85481845','admin@root.admin',1,'1456105400553121','1bjnObjO4Zu)kRcTgNQ8(PskoXDF!ize','rnd','nimda','555555'),(3,'test','098f6bcd4621d373cade4e832627b4f6','test@test.te',0,NULL,'_-BYU%SIYa9F-srT)9q_6rfza4WzVScZ','rnd','tset','FF0000'),(4,'color','70dda5dfb8053dc6d1c492574bce9bfd','color!color.ro',0,NULL,'N5O(IX;Sa@b7ds%t-Jw)5d)QLPp)Bdkn','color','color','718146');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxx`
--

DROP TABLE IF EXISTS `xxx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxx` (
  `xxx` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxx`
--

LOCK TABLES `xxx` WRITE;
/*!40000 ALTER TABLE `xxx` DISABLE KEYS */;
/*!40000 ALTER TABLE `xxx` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-22  0:01:45
