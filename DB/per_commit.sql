USE moneytrack;

CREATE TABLE moneytrack.bank_movement (
  id_transaction  INT NOT NULL,
  operation       VARCHAR(20) NOT NULL,
  remarks         VARCHAR(40) NULL,
  INDEX transaction__bank_movement__FK_idx (id_transaction ASC),
  UNIQUE INDEX id_transaction_UQ_idx (id_transaction ASC),
  CONSTRAINT transaction__bank_movement__FK
    FOREIGN KEY (id_transaction) REFERENCES moneytrack.transaction (id)
);

INSERT INTO transaction (id, date, amount, currency) VALUES (1000, '2017-1-12 15:40', 80, 'PEN');
INSERT INTO bank_movement (id_transaction, operation) VALUES (1000, 'WITHDRAWAL');

INSERT INTO transaction (id, date, amount, currency) VALUES (1001, '2017-2-27 12:15', 7560.76, 'PEN');
INSERT INTO bank_movement (id_transaction, operation, remarks) VALUES (1001, 'DEPOSIT', 'SALARY FEB2017');

INSERT INTO transaction (id, date, amount, currency) VALUES (1002, '2017-1-20 8:40', 50, 'PEN');
INSERT INTO bank_movement (id_transaction, operation, remarks) VALUES (1002, 'TRANSFER_OUT', 'DIEGO - ULIMA Gastos semana 20-24ENE2017');