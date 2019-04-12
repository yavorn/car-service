-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema car_service
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema car_service
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `car_service` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `car_service` ;

-- -----------------------------------------------------
-- Table `car_service`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`users` ;

CREATE TABLE IF NOT EXISTS `car_service`.`users` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(68) NOT NULL,
  `enabled` TINYINT(4) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `car_service`.`authorities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`authorities` ;

CREATE TABLE IF NOT EXISTS `car_service`.`authorities` (
  `authority_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`authority_id`),
  CONSTRAINT `FK__users`
    FOREIGN KEY (`username`)
    REFERENCES `car_service`.`users` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `username_authority` ON `car_service`.`authorities` (`username` ASC, `authority` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `car_service`.`car_event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`car_event` ;

CREATE TABLE IF NOT EXISTS `car_service`.`car_event` (
  `car_event_id` INT(11) NOT NULL,
  `date` DATE NOT NULL,
  `total_price` DOUBLE NOT NULL,
  `finalized` TINYINT(4) NOT NULL,
  PRIMARY KEY (`car_event_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `car_service`.`make`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`make` ;

CREATE TABLE IF NOT EXISTS `car_service`.`make` (
  `make_id` INT(11) NOT NULL AUTO_INCREMENT,
  `make_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`make_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `car_service`.`models`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`models` ;

CREATE TABLE IF NOT EXISTS `car_service`.`models` (
  `model_id` INT(11) NOT NULL AUTO_INCREMENT,
  `make_id` INT(11) NOT NULL,
  `model_name` VARCHAR(45) NOT NULL,
  `year` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  CONSTRAINT `fk_cars_make`
    FOREIGN KEY (`make_id`)
    REFERENCES `car_service`.`make` (`make_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1023
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `fk_cars_make_idx` ON `car_service`.`models` (`make_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `car_service`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`customer` ;

CREATE TABLE IF NOT EXISTS `car_service`.`customer` (
  `customer_id` INT(11) NOT NULL AUTO_INCREMENT,
  `phone` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`customer_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id_UNIQUE` ON `car_service`.`customer` (`customer_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `car_service`.`cars`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`cars` ;

CREATE TABLE IF NOT EXISTS `car_service`.`cars` (
  `car_id` INT(11) NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `model_id` INT(11) NOT NULL,
  `car_event_id` INT NOT NULL,
  `license_plate` VARCHAR(45) NOT NULL,
  `VIN` VARCHAR(17) NULL DEFAULT NULL,
  PRIMARY KEY (`car_id`),
  CONSTRAINT `fk_cars_models`
    FOREIGN KEY (`model_id`)
    REFERENCES `car_service`.`models` (`model_id`),
  CONSTRAINT `fk_cars_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `car_service`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cars_car_event`
    FOREIGN KEY (`car_event_id`)
    REFERENCES `car_service`.`car_event` (`car_event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `fk_cars_models_idx` ON `car_service`.`cars` (`model_id` ASC) VISIBLE;

CREATE INDEX `fk_cars_customer_idx` ON `car_service`.`cars` (`customer_id` ASC) VISIBLE;

CREATE INDEX `fk_cars_car_event_idx` ON `car_service`.`cars` (`car_event_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `car_service`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`service` ;

CREATE TABLE IF NOT EXISTS `car_service`.`service` (
  `service_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`service_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `name_UNIQUE` ON `car_service`.`service` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `car_service`.`service_visit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service`.`service_visit` ;

CREATE TABLE IF NOT EXISTS `car_service`.`service_visit` (
  `service_visit_id` INT NOT NULL AUTO_INCREMENT,
  `car_event_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  PRIMARY KEY (`service_visit_id`),
  CONSTRAINT `fk_service_visit_car_event`
    FOREIGN KEY (`car_event_id`)
    REFERENCES `car_service`.`car_event` (`car_event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_visit_service`
    FOREIGN KEY (`service_id`)
    REFERENCES `car_service`.`service` (`service_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `fk_service_visit_car_event_idx` ON `car_service`.`service_visit` (`car_event_id` ASC) VISIBLE;

CREATE INDEX `fk_service_visit_service_idx` ON `car_service`.`service_visit` (`service_id` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
