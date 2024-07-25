CREATE DATABASE  IF NOT EXISTS `reports_api` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `reports_api`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: reports_api
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `dateCreated` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` enum('BEST_PRACTICES','CORPORATE_INITIATIVES','REGULATIONS') DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES ('2024-06-13 19:27:43.647793',1,'REGULATIONS','Updated information on the UK government’s framework to create UK Sustainability Reporting Standards (UK SRS).','https://www.gov.uk/guidance/updated-uk-sustainability-disclosure-standards'),('2024-06-13 20:00:31.502800',2,'BEST_PRACTICES','Sustainability and public health: a guide to good practice. Guidance and advice on sustainable development and environmental management for the benefit of public health.','https://www.gov.uk/government/collections/sustainability-and-public-health-a-guide-to-good-practice'),('2024-06-13 18:00:00.000000',3,'BEST_PRACTICES','Make a start and reduce the impact of rising energy costs','https://businessclimatehub.uk/reduce-the-impact-of-rising-energy-costs/'),('2023-08-02 00:00:00.000000',4,'REGULATIONS','Information on the UK government’s framework to create UK Sustainability Reporting Standards (UK SRS) by assessing and endorsing the global corporate reporting baseline of IFRS Sustainability Disclosure Standards.','https://www.gov.uk/guidance/uk-sustainability-disclosure-standards'),('2023-11-08 00:00:00.000000',5,'CORPORATE_INITIATIVES','Global lessons in incentivising EV smart charging. Electric vehicle (EV) smart charging has the potential to avoid billions in electricity system costs over the coming decades.','https://www.kaluza.com/global-lessons-in-incentivising-ev-smart-charging/'),('2023-03-20 00:00:00.000000',6,'BEST_PRACTICES','Updated description:  What Does \'Net-Zero Emissions\' Mean? Net-zero emissions, or “net zero,” will be achieved when all emissions released by human activities are counterbalanced by removing carbon from the atmosphere in a process known as carbon removal.','https://www.wri.org/insights/net-zero-ghg-emissions-questions-answered');
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports_backup`
--

DROP TABLE IF EXISTS `reports_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports_backup` (
  `dateCreated` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL DEFAULT '0',
  `category` enum('BEST_PRACTICES','CORPORATE_INITIATIVES','REGULATIONS') DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports_backup`
--

LOCK TABLES `reports_backup` WRITE;
/*!40000 ALTER TABLE `reports_backup` DISABLE KEYS */;
INSERT INTO `reports_backup` VALUES ('2024-06-13 19:27:43.647793',1,'REGULATIONS','Updated information on the UK government’s framework to create UK Sustainability Reporting Standards (UK SRS).','https://www.gov.uk/guidance/updated-uk-sustainability-disclosure-standards'),('2024-06-13 20:00:31.502800',3,'BEST_PRACTICES','Sustainability and public health: a guide to good practice. Guidance and advice on sustainable development and environmental management for the benefit of public health.','https://www.gov.uk/government/collections/sustainability-and-public-health-a-guide-to-good-practice'),('2024-06-13 18:00:00.000000',5,'BEST_PRACTICES','Make a start and reduce the impact of rising energy costs','https://businessclimatehub.uk/reduce-the-impact-of-rising-energy-costs/'),('2023-08-02 00:00:00.000000',6,'REGULATIONS','Information on the UK government’s framework to create UK Sustainability Reporting Standards (UK SRS) by assessing and endorsing the global corporate reporting baseline of IFRS Sustainability Disclosure Standards.','https://www.gov.uk/guidance/uk-sustainability-disclosure-standards'),('2023-11-08 00:00:00.000000',7,'CORPORATE_INITIATIVES','Global lessons in incentivising EV smart charging. Electric vehicle (EV) smart charging has the potential to avoid billions in electricity system costs over the coming decades.','https://www.kaluza.com/global-lessons-in-incentivising-ev-smart-charging/'),('2023-03-20 00:00:00.000000',9,'BEST_PRACTICES','What Does \'Net-Zero Emissions\' Mean? Net-zero emissions, or “net zero,” will be achieved when all emissions released by human activities are counterbalanced by removing carbon from the atmosphere in a process known as carbon removal.','https://www.wri.org/insights/net-zero-ghg-emissions-questions-answered');
/*!40000 ALTER TABLE `reports_backup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-25 21:58:29
