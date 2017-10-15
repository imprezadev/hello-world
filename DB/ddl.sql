DROP SCHEMA IF EXISTS moneytrack;

CREATE SCHEMA moneytrack;

CREATE TABLE moneytrack.money_movement (
  id        INT NOT NULL AUTO_INCREMENT,
  date      DATETIME NOT NULL,
  amount    DECIMAL(10,2) NOT NULL,
  currency  CHAR(3) NOT NULL,
  CONSTRAINT PK__money_movement PRIMARY KEY(id)
);

CREATE TABLE moneytrack.expense (
  id_money_movement INT NOT NULL,
  payment_type      VARCHAR(15) NOT NULL,
  category          VARCHAR(24) NOT NULL,
  detail            VARCHAR(100) NOT NULL,
  CONSTRAINT FK__expense__money_movement FOREIGN KEY(id_money_movement) REFERENCES moneytrack.money_movement(id),
  UNIQUE INDEX UQ__id_money_movement(id_money_movement ASC)
);

CREATE TABLE moneytrack.bank_movement (
  id_money_movement INT NOT NULL,
  operation         VARCHAR(20) NOT NULL,
  remarks           VARCHAR(40) NULL,
  CONSTRAINT FK__bank_movement__money_movement FOREIGN KEY(id_money_movement) REFERENCES moneytrack.money_movement(id),
  UNIQUE INDEX UQ__id_money_movement(id_money_movement ASC)
);

CREATE TABLE moneytrack.credit_card_movement (
  id_money_movement INT NOT NULL,
  operation         VARCHAR(20) NOT NULL,
  remarks           VARCHAR(40) NULL,
  CONSTRAINT FK__credit_card_movement__money_movement FOREIGN KEY(id_money_movement) REFERENCES moneytrack.money_movement(id),
  UNIQUE INDEX UQ__id_money_movement(id_money_movement ASC)
);
