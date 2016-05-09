# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localhost (MySQL 5.7.12)
# Database: partnerboerse2000
# Generation Time: 2016-05-03 10:56:29 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Alternative
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Alternative`;

CREATE TABLE `Alternative` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Text` varchar(20) DEFAULT NULL,
  `Auswahl_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Auswahl` (`Auswahl_id`),
  CONSTRAINT `FK_Auswahl` FOREIGN KEY (`Auswahl_id`) REFERENCES `Auswahl` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Alternative` WRITE;
/*!40000 ALTER TABLE `Alternative` DISABLE KEYS */;

INSERT INTO `Alternative` (`id`, `Text`, `Auswahl_id`)
VALUES
	(5,'Döner',1),
	(6,'Pizza',1),
	(7,'Salat',1);

/*!40000 ALTER TABLE `Alternative` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Auswahl
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Auswahl`;

CREATE TABLE `Auswahl` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Eig_A` FOREIGN KEY (`id`) REFERENCES `Eigenschaft` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Auswahl` WRITE;
/*!40000 ALTER TABLE `Auswahl` DISABLE KEYS */;

INSERT INTO `Auswahl` (`id`)
VALUES
	(1);

/*!40000 ALTER TABLE `Auswahl` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Beschreibung
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Beschreibung`;

CREATE TABLE `Beschreibung` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Eig_B` FOREIGN KEY (`id`) REFERENCES `Eigenschaft` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Beschreibung` WRITE;
/*!40000 ALTER TABLE `Beschreibung` DISABLE KEYS */;

INSERT INTO `Beschreibung` (`id`)
VALUES
	(2);

/*!40000 ALTER TABLE `Beschreibung` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Eigenschaft
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Eigenschaft`;

CREATE TABLE `Eigenschaft` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Beschreibungstext` varchar(30) DEFAULT NULL,
  `Name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Eigenschaft` WRITE;
/*!40000 ALTER TABLE `Eigenschaft` DISABLE KEYS */;

INSERT INTO `Eigenschaft` (`id`, `Beschreibungstext`, `Name`)
VALUES
	(1,'Was ist dein Lieblingsessen','Essen'),
	(2,'Was machst du am Liebsten','Liebsten');

/*!40000 ALTER TABLE `Eigenschaft` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Info`;

CREATE TABLE `Info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Text` varchar(30) DEFAULT NULL,
  `Profil_id` int(11) unsigned NOT NULL,
  `Eigenschaft_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Profil` (`Profil_id`),
  KEY `FK_Eig` (`Eigenschaft_id`),
  CONSTRAINT `FK_Eig` FOREIGN KEY (`Eigenschaft_id`) REFERENCES `Eigenschaft` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_Profil` FOREIGN KEY (`Profil_id`) REFERENCES `Profil` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Kontaktsperre
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Kontaktsperre`;

CREATE TABLE `Kontaktsperre` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Sperrer_id` int(11) DEFAULT NULL,
  `Gesperrter_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Merkzettel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Merkzettel`;

CREATE TABLE `Merkzettel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Germerkter_id` int(11) DEFAULT NULL,
  `Merker_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Profil2` (`Merker_id`),
  CONSTRAINT `FK_Profil2` FOREIGN KEY (`Merker_id`) REFERENCES `Profil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Profil
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Profil`;

CREATE TABLE `Profil` (
  `Geburtsdatum` varchar(50) DEFAULT NULL,
  `Vorname` varchar(50) DEFAULT NULL,
  `Nachname` varchar(50) DEFAULT NULL,
  `Erstelldatum` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `Email` varchar(50) DEFAULT NULL,
  `Haarfarbe` varchar(50) DEFAULT NULL,
  `Koerpergroesse` int(3) DEFAULT NULL,
  `Raucher` varchar(30) DEFAULT NULL,
  `Religion` varchar(50) DEFAULT NULL,
  `Geschlecht` varchar(20) DEFAULT NULL,
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Profil` WRITE;
/*!40000 ALTER TABLE `Profil` DISABLE KEYS */;

INSERT INTO `Profil` (`Geburtsdatum`, `Vorname`, `Nachname`, `Erstelldatum`, `Email`, `Haarfarbe`, `Koerpergroesse`, `Raucher`, `Religion`, `Geschlecht`, `id`)
VALUES
	(NULL,'Daniel','Volz','2016-05-03 00:38:01','volz@mail.de','braun',175,'ja','keine','m',16),
	('05.03.1234','Peter','Cool','2016-05-03 01:09:21','test@example.com','blau',189,'nein','rk','m',17);

/*!40000 ALTER TABLE `Profil` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Profilbesuch
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Profilbesuch`;

CREATE TABLE `Profilbesuch` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Besucher_id` int(11) DEFAULT NULL,
  `Besuchter_id` int(11) DEFAULT NULL,
  `Zeitstempel` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Suchprofil
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Suchprofil`;

CREATE TABLE `Suchprofil` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(11) DEFAULT NULL,
  `Profil_id` int(11) unsigned DEFAULT NULL,
  `Eigenschaft_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Profil3` (`Profil_id`),
  CONSTRAINT `FK_Profil3` FOREIGN KEY (`Profil_id`) REFERENCES `Profil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
