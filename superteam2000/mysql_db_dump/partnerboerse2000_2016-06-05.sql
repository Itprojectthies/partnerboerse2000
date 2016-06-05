# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localhost (MySQL 5.7.12)
# Database: partnerboerse2000
# Generation Time: 2016-06-05 13:23:22 +0000
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
  KEY `FK` (`Auswahl_id`),
  CONSTRAINT `FK` FOREIGN KEY (`Auswahl_id`) REFERENCES `Eigenschaft` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Alternative` WRITE;
/*!40000 ALTER TABLE `Alternative` DISABLE KEYS */;

INSERT INTO `Alternative` (`id`, `Text`, `Auswahl_id`)
VALUES
	(5,'Döner',1),
	(6,'Pizza',1),
	(7,'Salat',1),
	(8,'Fussball',3),
	(9,'Handball',3),
	(10,'Basketball',3),
	(11,'Ja',4),
	(12,'Nein',4),
	(13,'blond',5),
	(14,'schwarz',5),
	(15,'brünett',5),
	(16,'dunkel-blond',5),
	(17,'rot',5),
	(18,'römisch-katholisch',6),
	(19,'römisch-orthodox',6),
	(20,'muslimisch',6),
	(21,'jüdisch',6),
	(22,'evangelisch',6),
	(23,'männlich',7),
	(24,'weiblich',7),
	(29,'Pils',13),
	(30,'Weizen',13),
	(31,'Export',13),
	(32,'Mac',14),
	(33,'PC',14);

/*!40000 ALTER TABLE `Alternative` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Eigenschaft
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Eigenschaft`;

CREATE TABLE `Eigenschaft` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Beschreibungstext` varchar(100) DEFAULT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `e_typ` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Eigenschaft` WRITE;
/*!40000 ALTER TABLE `Eigenschaft` DISABLE KEYS */;

INSERT INTO `Eigenschaft` (`id`, `Beschreibungstext`, `Name`, `e_typ`)
VALUES
	(1,'Was ist dein Lieblingsessen','Essen','a'),
	(2,'Was machst du am Liebsten','Liebsten','b'),
	(3,'Was ist dein Lieblingssport?','Sport','a'),
	(4,'Rauchst du?','Raucher','p_a'),
	(5,'Welche Haarfabe hast du?','Haarfarbe','p_a'),
	(6,'Welche Religionszugehörigkeit hast du?','Religion','p_a'),
	(7,'Welches Geschlecht hast du?','Geschlecht','p_a'),
	(8,'Wie ist dein Vorname?','Vorname','p_b'),
	(9,'Wie ist dein Nachname?','Nachname','p_b'),
	(10,'Wie Groß bist du?','Körpergröße','p_ab'),
	(11,'Wann bist du geboren?','Geburtstag','p_ab'),
	(13,'Was ist deine Lieblingsbiersorte?','Biersorte','a'),
	(14,'Nutzt du Mac oder PC?','PC oder Mac','a');

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
  KEY `FK_Eigen` (`Eigenschaft_id`),
  CONSTRAINT `FK_Eigen` FOREIGN KEY (`Eigenschaft_id`) REFERENCES `Eigenschaft` (`id`),
  CONSTRAINT `FK_Profil` FOREIGN KEY (`Profil_id`) REFERENCES `Profil` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Info` WRITE;
/*!40000 ALTER TABLE `Info` DISABLE KEYS */;

INSERT INTO `Info` (`id`, `Text`, `Profil_id`, `Eigenschaft_id`)
VALUES
	(75,'Export',23,13),
	(77,'Weizen',26,13),
	(78,'Weizen',24,13);

/*!40000 ALTER TABLE `Info` ENABLE KEYS */;
UNLOCK TABLES;


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
  `Gemerkter_id` int(11) DEFAULT NULL,
  `Merker_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Profil2` (`Merker_id`),
  CONSTRAINT `FK_Profil2` FOREIGN KEY (`Merker_id`) REFERENCES `Profil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Profil
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Profil`;

CREATE TABLE `Profil` (
  `Geburtsdatum` date DEFAULT NULL,
  `Vorname` varchar(50) DEFAULT NULL,
  `Nachname` varchar(50) DEFAULT NULL,
  `Erstelldatum` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Email` varchar(50) DEFAULT NULL,
  `Haarfarbe` varchar(50) DEFAULT NULL,
  `Koerpergroesse` int(3) DEFAULT NULL,
  `Raucher` varchar(30) DEFAULT NULL,
  `Religion` varchar(50) DEFAULT NULL,
  `Geschlecht` varchar(20) DEFAULT NULL,
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Aenderungsdatum` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Profil` WRITE;
/*!40000 ALTER TABLE `Profil` DISABLE KEYS */;

INSERT INTO `Profil` (`Geburtsdatum`, `Vorname`, `Nachname`, `Erstelldatum`, `Email`, `Haarfarbe`, `Koerpergroesse`, `Raucher`, `Religion`, `Geschlecht`, `id`, `Aenderungsdatum`)
VALUES
	('1985-06-07','dani','vau','2016-05-18 18:05:30','test@example.com','brünett',172,'Nein','römisch-katholisch','männlich',23,'2016-06-05 12:52:07'),
	('1994-10-01','Hansi','Mueller','2016-05-19 14:35:45','test23@example.com','blond',184,'Nein','römisch-katholisch','männlich',24,'2016-06-03 11:01:49'),
	('1992-01-01','Peter','Schulze','2016-05-19 18:53:13','test1@example.com','brünett',201,'Ja','römisch-katholisch','männlich',25,'2016-06-03 18:37:08'),
	('1992-04-21','nargi','veri','2016-05-21 21:55:40','nargi@example.com','brünett',161,'Nein','jüdisch','weiblich',26,'2016-05-25 18:45:08');

/*!40000 ALTER TABLE `Profil` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Profilbesuch
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Profilbesuch`;

CREATE TABLE `Profilbesuch` (
  `Besucher_id` int(11) NOT NULL,
  `Besuchter_id` int(11) NOT NULL,
  `Zeitstempel` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Besucher_id`,`Besuchter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Suchprofil
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Suchprofil`;

CREATE TABLE `Suchprofil` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(40) DEFAULT NULL,
  `Profil_id` int(11) unsigned DEFAULT NULL,
  `Haarfarbe` varchar(30) DEFAULT 'Keine Angabe',
  `Raucher` varchar(30) DEFAULT 'Keine Angabe',
  `Religion` varchar(30) DEFAULT 'Keine Angabe',
  `Geschlecht` varchar(30) DEFAULT 'Keine Angabe',
  `Koerpergroesse_min` int(11) DEFAULT '0',
  `Koerpergroesse_max` int(11) DEFAULT '0',
  `Alter_min` int(11) DEFAULT '0',
  `Alter_max` int(11) DEFAULT '0',
  `Auswahl_text_1` varchar(30) DEFAULT 'Keine Angabe',
  `Auswahl_id_1` int(11) DEFAULT '0',
  `Auswahl_text_2` varchar(30) DEFAULT 'Keine Angabe',
  `Auswahl_id_2` int(11) DEFAULT '0',
  `Auswahl_text_3` varchar(30) DEFAULT 'Keine Angabe',
  `Auswahl_id_3` int(11) DEFAULT '0',
  `Auswahl_text_4` varchar(30) DEFAULT 'Keine Angabe',
  `Auswahl_id_4` int(11) DEFAULT '0',
  `Auswahl_text_5` varchar(30) DEFAULT 'Keine Angabe',
  `Auswahl_id_5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Profil3` (`Profil_id`),
  CONSTRAINT `FK_Profil3` FOREIGN KEY (`Profil_id`) REFERENCES `Profil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Suchprofil` WRITE;
/*!40000 ALTER TABLE `Suchprofil` DISABLE KEYS */;

INSERT INTO `Suchprofil` (`id`, `Name`, `Profil_id`, `Haarfarbe`, `Raucher`, `Religion`, `Geschlecht`, `Koerpergroesse_min`, `Koerpergroesse_max`, `Alter_min`, `Alter_max`, `Auswahl_text_1`, `Auswahl_id_1`, `Auswahl_text_2`, `Auswahl_id_2`, `Auswahl_text_3`, `Auswahl_id_3`, `Auswahl_text_4`, `Auswahl_id_4`, `Auswahl_text_5`, `Auswahl_id_5`)
VALUES
	(4,'test1',23,'Keine Angabe','Keine Angabe','Keine Angabe','Keine Angabe',0,0,0,0,'Pizza',1,'ja',12,'Mac',14,'Keine Angabe',0,'Keine Angabe',0),
	(5,'test2',23,'Keine Angabe','Keine Angabe','Keine Angabe','Keine Angabe',0,0,0,0,'Pizza',1,'nein',12,'PC',14,'Keine Angabe',0,'Keine Angabe',0),
	(6,'test3',23,'Keine Angabe','Keine Angabe','jüdisch','Keine Angabe',0,0,18,0,'Salat',1,'ja',12,'Mac',14,'Keine Angabe',0,'Keine Angabe',0),
	(7,'Wer mag Weizen?',24,'Keine Angabe','Keine Angabe','Keine Angabe','weiblich',0,0,0,0,'Weizen',13,'Keine Angabe',0,'Keine Angabe',0,'Keine Angabe',0,'Keine Angabe',0);

/*!40000 ALTER TABLE `Suchprofil` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
