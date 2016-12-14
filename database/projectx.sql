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
INSERT INTO `friend` VALUES (0,15,12,1),(0,16,12,1);
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
) ENGINE=MyISAM AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geo`
--

LOCK TABLES `geo` WRITE;
/*!40000 ALTER TABLE `geo` DISABLE KEYS */;
INSERT INTO `geo` VALUES (120,59.8512329,30.2533511,12,'2016-12-14 19:32:37','Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36'),(121,59.866301,30.321161,15,'2016-12-10 08:22:13','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(122,59.868945,30.320709,15,'2016-12-10 08:23:01','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(123,59.878987,30.318219,15,'2016-12-10 08:24:44','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(124,59.891376,30.317961,15,'2016-12-10 08:26:11','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(125,59.906133,30.317454,15,'2016-12-10 08:28:11','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(126,59.916481,30.318538,15,'2016-12-10 08:29:54','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(127,59.926767,30.317126,15,'2016-12-10 08:31:07','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(128,59.934095,30.334524,15,'2016-12-10 08:32:27','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(129,59.956057,30.318683,15,'2016-12-10 08:35:22','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(130,59.956342,30.319259,15,'2016-12-10 08:36:22','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(131,59.956836,30.316126,15,'2016-12-10 08:37:22','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(132,59.956799,30.313619,15,'2016-12-10 08:38:22','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(133,59.957098,30.31345,15,'2016-12-10 08:39:22','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(134,59.958245,30.312755,15,'2016-12-10 08:40:22','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(135,59.957528,30.308026,15,'2016-12-10 08:41:22','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(136,59.903722,30.30071,16,'2016-12-09 17:09:31','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(137,59.907138,30.30027,16,'2016-12-09 17:10:31','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(138,59.907504,30.300045,16,'2016-12-09 17:11:31','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(139,59.916448,30.318802,16,'2016-12-09 17:13:07','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(140,59.920814,30.331601,16,'2016-12-09 17:14:42','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(141,59.927384,30.348028,16,'2016-12-09 17:16:02','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(142,59.927588,30.346687,16,'2016-12-09 17:17:02','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(143,59.926835,30.344294,16,'2016-12-09 17:18:02','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(144,59.92634,30.342481,16,'2016-12-09 17:19:02','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(145,59.926835,30.339669,16,'2016-12-09 17:20:02','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30'),(146,59.926755,30.338855,16,'2016-12-09 17:21:02','Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30');
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
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'xxx','2016-11-08 00:00:00',1,1,2),(2,'xxx','2016-11-08 00:00:00',1,1,2),(3,'xxx','2016-11-08 00:00:00',1,1,2),(4,'xxx','2016-11-08 00:00:00',1,2,1),(5,'xxx','2016-11-08 00:00:00',1,2,1),(6,'xxxqwd','2016-11-08 00:00:00',1,2,1),(7,'xxx','2016-11-08 00:00:00',1,1,2),(8,'aaa','2016-11-08 00:00:00',1,2,1),(9,'bbb','2016-11-08 00:00:00',1,1,2),(10,'qwe','2016-11-21 23:58:16',0,4,1),(11,'qwe','2016-11-21 23:58:18',1,4,2),(12,'qwe','2016-11-21 23:58:20',0,4,3),(13,'Roooot','2016-11-22 16:47:14',0,7,1),(14,'Colooor','2016-11-22 16:52:48',0,7,4),(15,'Color','2016-11-22 17:47:51',0,7,4),(16,'1','2016-11-23 15:51:22',0,9,1),(17,'1','2016-11-23 15:51:26',0,9,2),(18,'ghhghg','2016-11-24 16:46:35',1,10,1),(19,'gfgf','2016-11-24 16:46:45',1,1,10);
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
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (12,'root','63a9f0ea7bb98050796b649e85481845','root@root.ru',0,NULL,'F%h);ut(4P1CziP%-3Ye4CYE_Z0a6=5u','root','root','000000'),(13,'admin1','827ccb0eea8a706c4c34a16891f84e7b','slava@tihonov.ru',0,NULL,'K2y8F7x2RWMe8jV8UpySA5nOUeU;nbuP','Slava','Tihonov','0DD943'),(14,'admin2','827ccb0eea8a706c4c34a16891f84e7b','alex@bazanov.ru',0,NULL,'OJh6z?#Pmx-BPYoTXjLVcPOrbvN(t0=P','Alex','Bazanov','FF003C'),(15,'tester1','72a3dcef165d9122a45decf13ae20631','tester1@test.ru',0,NULL,';SmOxzb1w+0ylYAIYbYlMVzz_?++Hdk!','tester1','tester1','96530B'),(16,'tester2','2e9fcf8e3df4d415c96bcf288d5ca4ba','tester2@test.ru',0,NULL,'ujuoBXOoI+#RFA)-@5W=!bg0y;Gli%un','tester2','tester2','6BC2BD'),(17,'tester3','7effbf343c0f8ee164da2fe3ae01e8cb','tester3@test.ru',0,NULL,'S?Uv;aZZ9ZVo(O0mZTRClri#ZUm_uMy6','tester3','tester3','1215FC'),(18,'admin','827ccb0eea8a706c4c34a16891f84e7b','roman@patrikeyev.ru',0,NULL,'sqd_c71UXt8?Ae!fAEdh6y+FX5zg%RgP','Roman','Patrikeyev','9C0099'),(19,'tester4','d1af5dfeec69e0133969744c2cb8deac','tester4@test.ru',0,NULL,'2SupKnhQDCLVC8adPFqLYIO+nLVr8rzX','tester4','tester4','582398');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-14 20:33:50
