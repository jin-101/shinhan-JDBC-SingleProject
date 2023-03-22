-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema asset
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema asset
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `asset` DEFAULT CHARACTER SET utf8mb3 ;
USE `asset` ;

-- -----------------------------------------------------
-- Table `asset`.`departments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`departments` (
  `DEPARTMENT_ID` INT NOT NULL AUTO_INCREMENT COMMENT '부서 id, primary key',
  `DEPARTMENT_NAME` VARCHAR(30) NULL DEFAULT NULL COMMENT '부서명',
  PRIMARY KEY (`DEPARTMENT_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`jobs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`jobs` (
  `JOB_ID` VARCHAR(10) NOT NULL COMMENT '직급id, primary key',
  `JOB_TITLE` VARCHAR(35) NOT NULL COMMENT '직급명',
  `MIN_SALARY` INT NULL DEFAULT NULL COMMENT '직급별 최소 급여',
  `MAX_SALARY` INT NULL DEFAULT NULL COMMENT '직급별 최대 급여',
  PRIMARY KEY (`JOB_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`position` (
  `POSITION_ID` INT NOT NULL COMMENT '직책ID(100팀원/200파트장/300팀장/400실장/500사장), primary key',
  `POSITION_TITLE` VARCHAR(35) NOT NULL COMMENT '직책명(팀원/파트장/팀장/실장/사장)',
  `ALLOWANCE` DECIMAL(2,2) NULL DEFAULT NULL COMMENT '직책수당(팀원0/파트장0.1/팀장0.2/실장0.3/사장0.5)',
  PRIMARY KEY (`POSITION_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`subpart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`subpart` (
  `PART_NO` INT NOT NULL COMMENT '파트 번호',
  `PART_NAME` VARCHAR(45) NOT NULL COMMENT '파트이름',
  `departments_DEPARTMENT_ID` INT NOT NULL COMMENT '부서id',
  PRIMARY KEY (`PART_NO`),
  INDEX `fk_subpart_departments1_idx` (`departments_DEPARTMENT_ID` ASC) VISIBLE,
  CONSTRAINT `fk_subpart_departments1`
    FOREIGN KEY (`departments_DEPARTMENT_ID`)
    REFERENCES `asset`.`departments` (`DEPARTMENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`employees` (
  `EMPLOYEE_ID` INT NOT NULL AUTO_INCREMENT COMMENT '직원 id, primary key',
  `FIRST_NAME` VARCHAR(20) NOT NULL COMMENT '성씨',
  `LAST_NAME` VARCHAR(25) NOT NULL COMMENT '이름',
  `EMAIL` VARCHAR(25) NULL DEFAULT NULL COMMENT '이메일',
  `PHONE_NUMBER` VARCHAR(20) NULL DEFAULT NULL COMMENT '휴대폰 번호',
  `HIRE_DATE` DATE NULL DEFAULT NULL COMMENT '입사일',
  `jobs_JOB_ID` VARCHAR(10) NOT NULL COMMENT '직급 id',
  `SALARY` INT NULL DEFAULT NULL COMMENT '급여',
  `subpart_PART_NO` INT NULL,
  `position_POSITION_ID` INT NULL COMMENT '직책 id',
  PRIMARY KEY (`EMPLOYEE_ID`),
  INDEX `fk_employees_jobs_idx` (`jobs_JOB_ID` ASC) VISIBLE,
  INDEX `fk_employees_position1_idx` (`position_POSITION_ID` ASC) VISIBLE,
  INDEX `fk_employees_subpart1_idx` (`subpart_PART_NO` ASC) VISIBLE,
  CONSTRAINT `fk_employees_jobs`
    FOREIGN KEY (`jobs_JOB_ID`)
    REFERENCES `asset`.`jobs` (`JOB_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_position1`
    FOREIGN KEY (`position_POSITION_ID`)
    REFERENCES `asset`.`position` (`POSITION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_subpart1`
    FOREIGN KEY (`subpart_PART_NO`)
    REFERENCES `asset`.`subpart` (`PART_NO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`equipmentscompany`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`equipmentscompany` (
  `CO_ID` VARCHAR(10) NOT NULL COMMENT '장비 판매사 id',
  `CO_NAME` VARCHAR(45) NOT NULL COMMENT '장비 판매 회사명\\n',
  PRIMARY KEY (`CO_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`equipmentstype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`equipmentstype` (
  `TYPE_ID` VARCHAR(10) NOT NULL COMMENT '장비유형 id',
  `TYPE_NAME` VARCHAR(20) NOT NULL COMMENT '장비유형(pc,모니터, 등)',
  PRIMARY KEY (`TYPE_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`equipments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`equipments` (
  `EQUIPMENT_ID` INT NOT NULL AUTO_INCREMENT COMMENT '장비번호',
  `equipmentstype_TYPE_ID` VARCHAR(10) NOT NULL COMMENT '장비유형',
  `equipmentscompany_CO_ID` VARCHAR(10) NOT NULL COMMENT '장비 판매 회사',
  `MODEL` VARCHAR(30) NOT NULL COMMENT '장비 모델명',
  `SERIAL_NO` VARCHAR(45) NOT NULL COMMENT '장비 S/N',
  `PURCHASE_DATE` DATE NULL DEFAULT NULL COMMENT '장비 구매일',
  `PRICE` INT NULL DEFAULT NULL COMMENT '장비 가격',
  PRIMARY KEY (`EQUIPMENT_ID`),
  INDEX `fk_equipments_equipmentscompany1_idx` (`equipmentscompany_CO_ID` ASC) VISIBLE,
  INDEX `fk_equipments_equipmentstype1_idx` (`equipmentstype_TYPE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_equipments_equipmentscompany1`
    FOREIGN KEY (`equipmentscompany_CO_ID`)
    REFERENCES `asset`.`equipmentscompany` (`CO_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_equipments_equipmentstype1`
    FOREIGN KEY (`equipmentstype_TYPE_ID`)
    REFERENCES `asset`.`equipmentstype` (`TYPE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `asset`.`rental`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `asset`.`rental` (
  `RENT_ID` INT NOT NULL AUTO_INCREMENT COMMENT '대여 코드 id',
  `equipments_EQUIPMENT_ID` INT NOT NULL UNIQUE COMMENT '대여 장비 id',
  `employees_EMPLOYEE_ID` INT NOT NULL COMMENT '대여 직원 id',
  `START_DATE` DATE NULL DEFAULT NULL COMMENT '대여 시작일',
  `END_DATE` DATE NULL DEFAULT NULL COMMENT '대여 종료일',
  `STATUS` VARCHAR(45) NULL DEFAULT NULL COMMENT '현재 대여 상태\\n',
  PRIMARY KEY (`RENT_ID`),
  INDEX `fk_rental_employees1_idx` (`employees_EMPLOYEE_ID` ASC) VISIBLE,
  INDEX `fk_rental_equipments1_idx` (`equipments_EQUIPMENT_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rental_employees1`
    FOREIGN KEY (`employees_EMPLOYEE_ID`)
    REFERENCES `asset`.`employees` (`EMPLOYEE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_equipments1`
    FOREIGN KEY (`equipments_EQUIPMENT_ID`)
    REFERENCES `asset`.`equipments` (`EQUIPMENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `asset`.`account` (
  `ID` VARCHAR(20) NOT NULL COMMENT '관리자 로그인 아이디',
  `PASSWORD` VARCHAR(20) NOT NULL COMMENT '관리자 로그인 비밀번호',
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
