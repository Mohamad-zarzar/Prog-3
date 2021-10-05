SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema projektverwaltung
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projektverwaltung
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projektverwaltung` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `projektverwaltung` ;




-- -----------------------------------------------------
-- Table `projektverwaltung`.`modul`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`modul` (
  `Id` BIGINT(20) NOT NULL,
  `Image` VARCHAR(1000) NULL DEFAULT NULL,
  `Name` VARCHAR(255) NULL DEFAULT NULL,
  `Notiz` VARCHAR(6500) NULL DEFAULT NULL,
  `Semester` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `projektverwaltung`.`projektthema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`projektthema` (
  `id` BIGINT(20) NOT NULL,
  `Beschreibung` VARCHAR(6500) NULL DEFAULT NULL,
  `Name` VARCHAR(255) NULL DEFAULT NULL,
  `Notiz` VARCHAR(6500) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `projektverwaltung`.`modul_thema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`modul_thema` (
  `modul_id` BIGINT(20) NOT NULL,
  `projektthema_id` BIGINT(20) NOT NULL,
  INDEX `FKg5ew7fwsrowl4lqqjy7qcr9sn` (`projektthema_id` ASC) VISIBLE,
  INDEX `FKilaxy5pge3qndqg1w6sug3m3x` (`modul_id` ASC) VISIBLE,
  CONSTRAINT `FKg5ew7fwsrowl4lqqjy7qcr9sn`
    FOREIGN KEY (`projektthema_id`)
    REFERENCES `projektverwaltung`.`projektthema` (`id`),
  CONSTRAINT `FKilaxy5pge3qndqg1w6sug3m3x`
    FOREIGN KEY (`modul_id`)
    REFERENCES `projektverwaltung`.`modul` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `projektverwaltung`.`projekt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`projekt` (
  `id` BIGINT(20) NOT NULL,
  `isActive` BIT(1) NULL DEFAULT NULL,
  `MainUrl` VARCHAR(2000) NULL DEFAULT NULL,
  `Name` VARCHAR(255) NULL DEFAULT NULL,
  `Notizen` VARCHAR(6500) NULL DEFAULT NULL,
  `Semester` TINYINT(4) NULL DEFAULT NULL,
  `Projektmodul_id` BIGINT(20) NOT NULL,
  `Projektthema_id` BIGINT(20) NULL DEFAULT NULL,
  `Note` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKhl0y94l6rfi81hue07b7o44xb` (`Projektmodul_id` ASC) VISIBLE,
  INDEX `FKc6xd7nfh5ht9mbkqwchoki65l` (`Projektthema_id` ASC) VISIBLE,
  CONSTRAINT `FKc6xd7nfh5ht9mbkqwchoki65l`
    FOREIGN KEY (`Projektthema_id`)
    REFERENCES `projektverwaltung`.`projektthema` (`id`),
  CONSTRAINT `FKhl0y94l6rfi81hue07b7o44xb`
    FOREIGN KEY (`Projektmodul_id`)
    REFERENCES `projektverwaltung`.`modul` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `projektverwaltung`.`sprints`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`sprints` (
  `Id` BIGINT(20) NOT NULL,
  `Notizen` VARCHAR(6500) NULL DEFAULT NULL,
  `SprintAnfang` DATE NULL DEFAULT NULL,
  `SprintBeschreibung` VARCHAR(6500) NULL DEFAULT NULL,
  `SprintEnde` DATE NULL DEFAULT NULL,
  `SprintNumber` INT(11) NULL DEFAULT NULL,
  `Projekt_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `FK6xgfsigwhwvq1e193hdlqfj3i` (`Projekt_id` ASC) VISIBLE,
  CONSTRAINT `FK6xgfsigwhwvq1e193hdlqfj3i`
    FOREIGN KEY (`Projekt_id`)
    REFERENCES `projektverwaltung`.`projekt` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `projektverwaltung`.`studenten`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`studenten` (
  `Matrikelnummer` INT(11) NOT NULL,
  `Name` VARCHAR(255) NULL DEFAULT NULL,
  `Note` DOUBLE NULL DEFAULT NULL,
  `Vorname` VARCHAR(255) NULL DEFAULT NULL,
  `Projekt_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`Matrikelnummer`),
  INDEX `FKl0ftjwfur2gm1f14yuyw26gwx` (`Projekt_id` ASC) VISIBLE,
  CONSTRAINT `FKl0ftjwfur2gm1f14yuyw26gwx`
    FOREIGN KEY (`Projekt_id`)
    REFERENCES `projektverwaltung`.`projekt` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `projektverwaltung`.`termin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`termin` (
  `id` BIGINT(20) NOT NULL,
  `comment` VARCHAR(6500) NULL DEFAULT NULL,
  `date` VARCHAR(255) NULL DEFAULT NULL,
  `finished` BIT(1) NOT NULL,
  `time` TIME NULL DEFAULT NULL,
  `title` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `projektverwaltung`.`urls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektverwaltung`.`urls` (
  `id` BIGINT(20) NOT NULL,
  `Name` VARCHAR(500) NULL DEFAULT NULL,
  `URL` VARCHAR(3000) NOT NULL ,
  `Projekt_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKfq93889kt20besiwr3htr22ny` (`Projekt_id` ASC) VISIBLE,
  CONSTRAINT `FKfq93889kt20besiwr3htr22ny`
    FOREIGN KEY (`Projekt_id`)
    REFERENCES `projektverwaltung`.`projekt` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
