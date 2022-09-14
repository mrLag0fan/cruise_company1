-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cruise_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cruise_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cruise_company` DEFAULT CHARACTER SET utf8 ;
USE `cruise_company` ;

-- -----------------------------------------------------
-- Table `cruise_company`.`language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`language` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`language` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(45) NOT NULL,
  `short_name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `full_name_UNIQUE` (`full_name` ASC) VISIBLE,
  UNIQUE INDEX `short_name_UNIQUE` (`short_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise_company`.`port_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`port_status` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`port_status` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `language_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_port_status_language1_idx` (`language_id` ASC) VISIBLE,
  CONSTRAINT `fk_port_status_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `cruise_company`.`language` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `cruise_company`.`port`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`port` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`port` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `port_status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_port_port_status1_idx` (`port_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_port_port_status1`
    FOREIGN KEY (`port_status_id`)
    REFERENCES `cruise_company`.`port_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise_company`.`liner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`liner` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`liner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `passenger_capacity` INT UNSIGNED NOT NULL,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `duration_in_days` INT UNSIGNED NOT NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  `visited_ports` INT UNSIGNED NOT NULL,
  `start` INT NOT NULL,
  `end` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_liner_port1_idx` (`start` ASC) VISIBLE,
  INDEX `fk_liner_port2_idx` (`end` ASC) VISIBLE,
  CONSTRAINT `fk_liner_port1`
    FOREIGN KEY (`start`)
    REFERENCES `cruise_company`.`port` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_liner_port2`
    FOREIGN KEY (`end`)
    REFERENCES `cruise_company`.`port` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise_company`.`route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`route` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`route` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `from` INT NOT NULL,
  `to` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_route_port1_idx` (`to` ASC) VISIBLE,
  INDEX `fk_route_port2_idx` (`from` ASC) VISIBLE,
  CONSTRAINT `fk_route_port1`
    FOREIGN KEY (`to`)
    REFERENCES `cruise_company`.`port` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_route_port2`
    FOREIGN KEY (`from`)
    REFERENCES `cruise_company`.`port` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise_company`.`liner_has_route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`liner_has_route` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`liner_has_route` (
  `liner_id` INT NOT NULL,
  `route_id` INT NOT NULL,
  PRIMARY KEY (`liner_id`, `route_id`),
  INDEX `fk_liner_has_route_route1_idx` (`route_id` ASC) INVISIBLE,
  INDEX `fk_liner_has_route_route2_idx` (`liner_id` ASC) VISIBLE,
  CONSTRAINT `fk_linar_has_route_linar1`
    FOREIGN KEY (`liner_id`)
    REFERENCES `cruise_company`.`liner` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_linar_has_route_route1`
    FOREIGN KEY (`route_id`)
    REFERENCES `cruise_company`.`route` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise_company`.`personal_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`personal_role` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`personal_role` (
  `iid` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `language_id` INT NOT NULL,
  PRIMARY KEY (`iid`),
  INDEX `fk_personal_role_language1_idx` (`language_id` ASC) VISIBLE,
  CONSTRAINT `fk_personal_role_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `cruise_company`.`language` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `cruise_company`.`personal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`personal` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`personal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(16) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(32) NULL DEFAULT 'NO PHONE',
  `experience` INT UNSIGNED NOT NULL,
  `linar_id` INT NOT NULL,
  `personal_role_iid` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_personal_linar1_idx` (`linar_id` ASC) VISIBLE,
  INDEX `fk_personal_personal_role1_idx` (`personal_role_iid` ASC) VISIBLE,
  CONSTRAINT `fk_personal_linar1`
    FOREIGN KEY (`linar_id`)
    REFERENCES `cruise_company`.`liner` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_personal_personal_role1`
    FOREIGN KEY (`personal_role_iid`)
    REFERENCES `cruise_company`.`personal_role` (`iid`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `cruise_company`.`receipt_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`receipt_status` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`receipt_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `language_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_receipt_status_language1_idx` (`language_id` ASC) VISIBLE,
  CONSTRAINT `fk_receipt_status_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `cruise_company`.`language` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise_company`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`user_role` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise_company`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`user` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `user_role_id` INT NOT NULL,
  `balance` DECIMAL(10,2) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_user_user_role1_idx` (`user_role_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_role1`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `cruise_company`.`user_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `cruise_company`.`receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cruise_company`.`receipt` ;

CREATE TABLE IF NOT EXISTS `cruise_company`.`receipt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `documents` VARCHAR(255) NOT NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  `receipt_status_id` INT NOT NULL,
  `liner_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_receipt_receipt_status1_idx` (`receipt_status_id` ASC) VISIBLE,
  INDEX `fk_receipt_linar1_idx` (`liner_id` ASC) VISIBLE,
  INDEX `fk_receipt_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_receipt_receipt_status1`
    FOREIGN KEY (`receipt_status_id`)
    REFERENCES `cruise_company`.`receipt_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receipt_linar1`
    FOREIGN KEY (`liner_id`)
    REFERENCES `cruise_company`.`liner` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receipt_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cruise_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO language (full_name, short_name) VALUES ("ENGLISH", "en");
INSERT INTO language (full_name, short_name) VALUES ("UKRAINIAN", "uk_UA");

INSERT INTO port_status (id, name, language_id) VALUES (1, "Open", 1);
INSERT INTO port_status (id, name, language_id) VALUES (2, "Closed", 1);
INSERT INTO port_status (id, name, language_id) VALUES (3, "Відкритий", 2);
INSERT INTO port_status (id, name, language_id) VALUES (4, "Закритий", 2);

INSERT INTO personal_role (name, language_id) VALUES ("Captain", 1);
INSERT INTO personal_role (name, language_id) VALUES ("Cabin boy", 1);
INSERT INTO personal_role (name, language_id) VALUES ("Капітан", 2);
INSERT INTO personal_role (name, language_id) VALUES ("Юнга", 2);

INSERT INTO receipt_status (name, language_id) VALUES ("Без документів", 2);
INSERT INTO receipt_status (name, language_id) VALUES ("З документами", 2);
INSERT INTO receipt_status (name, language_id) VALUES ("Підтверджене", 2);
INSERT INTO receipt_status (name, language_id) VALUES ("Не оплачене", 2);
INSERT INTO receipt_status (name, language_id) VALUES ("Оплачене", 2);
INSERT INTO receipt_status (name, language_id) VALUES ("Завершене", 2);
INSERT INTO receipt_status (name, language_id) VALUES ("Without documents", 1);
INSERT INTO receipt_status (name, language_id) VALUES ("With documents", 1);
INSERT INTO receipt_status (name, language_id) VALUES ("Confirmed", 1);
INSERT INTO receipt_status (name, language_id) VALUES ("Not paid", 1);
INSERT INTO receipt_status (name, language_id) VALUES ("Paid", 1);
INSERT INTO receipt_status (name, language_id) VALUES ("Completed", 1);

INSERT INTO user_role (name) VALUES ("admin");
INSERT INTO user_role (name) VALUES ("customer");

INSERT INTO user (email, password, user_role_id) VALUES ("admin@mail.com", "admin", 1);