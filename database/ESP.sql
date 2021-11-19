-- MySQL dump 10.13  Distrib 8.0.26, for macos11.4 (arm64)
--
-- Host: localhost    Database: esp
-- ------------------------------------------------------
-- Server version	8.0.26
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
--
-- Current Database: `esp`
--
CREATE DATABASE
/*!32312 IF NOT EXISTS*/
`esp`
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */
/*!80016 DEFAULT ENCRYPTION='N' */;
USE `esp`;
--
-- Table structure for table `organization_list`
--
DROP TABLE IF EXISTS `organization_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization_list` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(75) NOT NULL,
  `DbName` varchar(75) NOT NULL,
  `SecretKey` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `SecretKey` (`SecretKey`)
) ENGINE = InnoDB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `organization_list`
--
LOCK TABLES `organization_list` WRITE;
/*!40000 ALTER TABLE `organization_list` DISABLE KEYS */;
INSERT INTO
  `organization_list`
VALUES
  (
    1,
    'EventGo',
    'EventGo',
    'AB1234',
    '2021-10-07 00:41:06'
  );
  /*!40000 ALTER TABLE `organization_list` ENABLE KEYS */;
UNLOCK TABLES;
--
  -- Current Database: `EventGo`
  --
  CREATE DATABASE
  /*!32312 IF NOT EXISTS*/
  `EventGo`
  /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */
  /*!80016 DEFAULT ENCRYPTION='N' */;
USE `EventGo`;
--
  -- Table structure for table `Events`
  --
  DROP TABLE IF EXISTS `Events`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Events` (
    `EventId` int NOT NULL AUTO_INCREMENT,
    `EventName` varchar(20) DEFAULT NULL,
    `EventDesc` varchar(200) DEFAULT NULL,
    `capacity` int DEFAULT NULL,
    `Price` float(7, 2) DEFAULT NULL,
    PRIMARY KEY (`EventId`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 1002 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
  /*!40101 SET character_set_client = @saved_cs_client */;
--
  -- Dumping data for table `Events`
  --
  LOCK TABLES `Events` WRITE;
  /*!40000 ALTER TABLE `Events` DISABLE KEYS */;
INSERT INTO
  `Events`
VALUES
  (
    1000,
    'Karaoke',
    'Music system and person handling various soundtracks with mic allowing people to sing fav tracks',
    60,
    400.00
  ),(
    1001,
    'DJ',
    'Music system and person handling various soundtracks with stage to dance',
    100,
    850.00
  );
  /*!40000 ALTER TABLE `Events` ENABLE KEYS */;
UNLOCK TABLES;
-- table for customers
  DROP TABLE IF EXISTS `Customer`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Customer` (
    `CustomerId` int NOT NULL AUTO_INCREMENT,
    `Name` varchar(20) DEFAULT NULL,
    `Email` varchar(200) DEFAULT NULL,
    `UserName` varchar(200) DEFAULT NULL,
    `Password` varchar(200) DEFAULT NULL,
    `UserRole` varchar(20) DEFAULT NULL,
    `BillAddress` varchar(200) DEFAULT NULL,
    PRIMARY KEY (`CustomerId`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 100002 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
  /*!40101 SET character_set_client = @saved_cs_client */;
--
  -- Dumping data for table `Events`
  --
  LOCK TABLES `Customer` WRITE;
  /*!40000 ALTER TABLE `Events` DISABLE KEYS */;
INSERT INTO
  `Customer`
VALUES
  (
    100000,
    'Vivek Agrawal',
    'agrawal.vivek2017@gmail.com',
    'agrawal.vivek2017',
    'Pass@123',
    'Customer',
    '16500 Lauder Ln, Dallas'
  ),(
    100001,
    'Test Demo',
    'testdemotest@gmail.com',
    'temo.dest',
    'testetstest',
    'Customer',
    '3000 Test lane, Demotown'
  );
  /*!40000 ALTER TABLE `Events` ENABLE KEYS */;
UNLOCK TABLES;
-- table for Member of Staff
  DROP TABLE IF EXISTS `MemberOfStaff`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MemberOfStaff` (
    `userId` int NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NOT NULL,
    `email` varchar(200) UNIQUE NOT NULL,
    `password` varchar(200) UNIQUE NOT NULL,
    `userRole` varchar(20) DEFAULT 'MEMBER_OF_STAFF',
    `salary` double(6, 2) NOT NULL,
    PRIMARY KEY (`userId`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
  /*!40101 SET character_set_client = @saved_cs_client */;
--
  -- Dumping data for table `Events`
  --
  LOCK TABLES `MemberOfStaff` WRITE;
  /*!40000 ALTER TABLE `MemberOfStaff` DISABLE KEYS */;
INSERT INTO
  `MemberOfStaff`
VALUES
  (
    1,
    'Nihal B',
    'asdf123@gmail.com',
    'BadP4ssw0rd#199',
    'ADMIN',
    '1234.99'
  ),(
    2,
    'John Doe',
    'testing@gmail.com',
    'password#123',
    'MEMBER_OF_STAFF',
    '1000.00'
  );
  /*!40000 ALTER TABLE `MemberOfStaff` ENABLE KEYS */;
UNLOCK TABLES;
--
  -- Table structure for table `EventSchedule`
  --
  DROP TABLE IF EXISTS `EventSchedule`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EventSchedule` (
    `EventScheduleId` int NOT NULL AUTO_INCREMENT,
    `eventId` int NOT NULL,
    `location` varchar(200) DEFAULT NULL,
    `startDateTime` datetime DEFAULT NULL,
    `endDateTime` datetime DEFAULT NULL,
    PRIMARY KEY (`EventScheduleId`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 100003 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
  /*!40101 SET character_set_client = @saved_cs_client */;
--
  -- Dumping data for table `EventSchedule`
  --
  LOCK TABLES `EventSchedule` WRITE;
  /*!40000 ALTER TABLE `EventSchedule` DISABLE KEYS */;
INSERT INTO
  `EventSchedule`
VALUES
  (
    100000,
    1001,
    '2203, Chadwick, MapleShade, Texas 72323',
    '2021-10-29 09:30:00',
    '2021-10-29 13:30:00'
  ),(
    100001,
    1000,
    '2088, Boseman, Rick Cen,Texas 79023',
    '2021-10-26 19:30:00',
    '2021-10-26 21:30:00'
  ),(
    100002,
    1000,
    '2088, Boseman, Rick Cen,Texas 79023',
    '2021-10-27 15:30:00',
    '2021-10-27 21:30:00'
  );
  /*!40000 ALTER TABLE `EventSchedule` ENABLE KEYS */;
UNLOCK TABLES;
  /*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
  /*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
  /*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
  /*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
  /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
  /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
  /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
  /*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;