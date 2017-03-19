CREATE SCHEMA `moneytrack`;

CREATE TABLE `moneytrack`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NULL,
  `amount` DECIMAL(10) NOT NULL,
  `concept` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
);
