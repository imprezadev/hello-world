CREATE SCHEMA `moneytrack`;

CREATE TABLE `moneytrack`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NULL,
  `amount` DECIMAL(10) NOT NULL,
  `concept` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `moneytrack`.`transaction` 
  CHANGE COLUMN `amount` `amount` DECIMAL(10,2) NOT NULL;

ALTER TABLE `moneytrack`.`transaction` 
  CHANGE COLUMN `date` `date` DATETIME NOT NULL ,
  CHANGE COLUMN `concept` `concept` VARCHAR(100) NOT NULL;

ALTER TABLE `moneytrack`.`transaction` 
  ADD COLUMN `currency` CHAR(3) NOT NULL DEFAULT 'PEN' AFTER `amount`;

CREATE TABLE `moneytrack`.`expense` (
  `id_transaction` INT NOT NULL,
  `payment_type` VARCHAR(8) NOT NULL,
  `category` VARCHAR(24) NOT NULL,
  INDEX `id_idx` (`id_transaction` ASC),
  CONSTRAINT `FK_transaction_expense`
    FOREIGN KEY (`id_transaction`) REFERENCES `moneytrack`.`transaction` (`id`)
);

ALTER TABLE `moneytrack`.`expense` 
  ADD UNIQUE INDEX `id_transaction_UNIQUE` (`id_transaction` ASC);
