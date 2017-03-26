DROP SCHEMA IF EXISTS moneytrack;

CREATE SCHEMA moneytrack;

CREATE TABLE moneytrack.transaction (
  id        INT NOT NULL AUTO_INCREMENT,
  date      DATETIME NOT NULL,
  amount    DECIMAL(10,2) NOT NULL,
  currency  CHAR(3) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE moneytrack.expense (
  id_transaction  INT NOT NULL,
  payment_type    VARCHAR(8) NOT NULL,
  category        VARCHAR(24) NOT NULL,
  detail          VARCHAR(100) NOT NULL,
  INDEX id_idx (id_transaction ASC),
  CONSTRAINT FK_transaction_expense
    FOREIGN KEY (id_transaction) REFERENCES moneytrack.transaction (id),
  UNIQUE INDEX id_transaction_UNIQUE (id_transaction ASC)
);

CREATE TABLE moneytrack.bank_movement (
  id_transaction  INT NOT NULL,
  operation       VARCHAR(20) NOT NULL,
  remarks         VARCHAR(40) NULL,
  INDEX transaction__bank_movement__FK_idx (id_transaction ASC),
  UNIQUE INDEX id_transaction_UQ_idx (id_transaction ASC),
  CONSTRAINT transaction__bank_movement__FK
    FOREIGN KEY (id_transaction) REFERENCES moneytrack.transaction (id)
);
