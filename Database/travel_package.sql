-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: travel
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `extraDetails` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `noOfPeople` int DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `destination_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtq1gn2h0fe7r8ddc34f6tlit1` (`destination_id`),
  KEY `FKccrkbpdejkopmpkfi582awamv` (`user_id`),
  CONSTRAINT `FKccrkbpdejkopmpkfi582awamv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtq1gn2h0fe7r8ddc34f6tlit1` FOREIGN KEY (`destination_id`) REFERENCES `destination` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (20,'Short city Break','Cluj',21,'2022-03-15',33,'IN_PROGRESS',2,NULL),(21,'Just don\'t','Dej',10,'2022-03-15',5,'NOT_BOOKED',2,NULL),(22,'Capital of Romania','Bucharest',20,'2022-03-17',30,'NOT_BOOKED',2,NULL),(23,'At your own risk','Vaslui',1,'2022-03-22',5,'NOT_BOOKED',2,NULL),(24,'Capital of Germany','Berlin',25,'2022-03-30',125,'NOT_BOOKED',1,NULL),(25,'City of Love, Great for couples','Paris',15,'2022-03-22',300,'NOT_BOOKED',5,NULL),(26,'The best football is played here','Madrid',60,'2022-04-26',250,'NOT_BOOKED',6,NULL),(28,'Please don\'t go here, just a test for deletion','Moscow',5,'2022-03-29',5,'NOT_BOOKED',15,NULL),(30,'For beer enjoyers','Hamburg',60,'2022-05-03',350,'NOT_BOOKED',1,NULL);
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-13 22:16:57
