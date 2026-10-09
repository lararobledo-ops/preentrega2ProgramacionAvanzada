-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `editorial` DEFAULT CHARACTER SET utf8 ;
USE `editorial` ;

-- -----------------------------------------------------
-- Table `mydb`.`editor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editorial`.`editor` (
  `ideditor` INT UNSIGNED NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `mail` VARCHAR(45) NOT NULL,
  `contrasenia` VARCHAR(45) NOT NULL,
  `dni` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ideditor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`libro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editorial`.`libro` (
  `idlibro` INT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `genero` VARCHAR(45) NOT NULL,
  `precio` DECIMAL(10,2) NOT NULL,
  `stock` INT NOT NULL,
  `editor_ideditor` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idlibro`),
  INDEX `fk_libro_editor1_idx` (`editor_ideditor`),
  CONSTRAINT `fk_libro_editor1`
    FOREIGN KEY (`editor_ideditor`)
    REFERENCES `editorial`.`editor` (`ideditor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`escritor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editorial`.`escritor` (
  `id` INT UNSIGNED NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `dni` INT UNSIGNED NOT NULL,
  `mail` VARCHAR(45) NOT NULL,
  `contrasenia` VARCHAR(45) NOT NULL,
  `libro_idlibro` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC),
  UNIQUE INDEX `mail_UNIQUE` (`mail` ASC),
  INDEX `fk_escritor_libro_idx` (`libro_idlibro` ASC),
  CONSTRAINT `fk_escritor_libro`
    FOREIGN KEY (`libro_idlibro`)
    REFERENCES `editorial`.`libro` (`idlibro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



USE editorial;
SHOW TABLES;

SELECT * FROM editor;
SELECT * FROM libro;
SELECT * FROM escritor;

USE editorial;

ALTER TABLE editor
MODIFY contrasenia VARCHAR(60) NOT NULL;

ALTER TABLE escritor
MODIFY contrasenia VARCHAR(60) NOT NULL;

SELECT ideditor FROM editor;
SELECT id FROM escritor;
SELECT idlibro FROM libro;

USE editorial;

SELECT ideditor FROM editor;
SELECT id FROM escritor;
SELECT idlibro FROM libro;

USE editorial;

ALTER TABLE editor
MODIFY ideditor INT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE escritor
MODIFY id INT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE libro
MODIFY idlibro INT NOT NULL AUTO_INCREMENT;

SELECT VERSION();

SELECT id, nombre, apellido, mail, contrasenia
FROM editorial.escritor;

USE editorial;

SHOW COLUMNS FROM escritor;

USE editorial;

ALTER TABLE escritor
MODIFY libro_idlibro INT NULL;

SELECT * FROM editorial.escritor;

USE editorial;






USE editorial;
SELECT * FROM editor;

USE editorial;

INSERT INTO libro
    (titulo, genero, precio, stock, editor_ideditor)
SELECT
    'Libro de prueba JOIN',
    'Fantasia',
    15000,
    10,
    ideditor
FROM editor
ORDER BY ideditor
LIMIT 1;

SET @id_libro_prueba = LAST_INSERT_ID();

INSERT INTO escritor
    (nombre, apellido, dni, mail, contrasenia, libro_idlibro)
VALUES
    ('Ana', 'Lopez', 499219991,
     'ana.join.prueba@gmail.com',
     'solo_prueba',
     @id_libro_prueba);
     
     SELECT
    e.nombre AS editor,
    l.titulo AS libro,
    es.nombre AS escritor
FROM editor e
INNER JOIN libro l
    ON e.ideditor = l.editor_ideditor
INNER JOIN escritor es
    ON l.idlibro = es.libro_idlibro;
     
     USE editorial;

SELECT
    e.nombre AS editor,
    l.titulo AS libro,
    es.nombre AS escritor
FROM editor e
INNER JOIN libro l
    ON e.ideditor = l.editor_ideditor
INNER JOIN escritor es
    ON l.idlibro = es.libro_idlibro;
	
USE editorial;

INSERT INTO editor (nombre, apellido, mail, contrasenia, dni)
VALUES ('Marta', 'Gomez', 'marta.editor@gmail.com',
        '$2a$10$yY8i9Gja5CCsN.GJfmMhVuGZzq9hejv0dqt4bvi3B1CqoQY0IExW6', 30111222);

INSERT INTO libro (titulo, genero, precio, stock, editor_ideditor)
SELECT 'El libro de prueba', 'Fantasia', 15000, 10, ideditor
FROM editor WHERE nombre = 'Marta' LIMIT 1;

UPDATE escritor
SET libro_idlibro = (SELECT idlibro FROM libro WHERE titulo = 'El libro de prueba' LIMIT 1)
WHERE nombre = 'Paula';
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
