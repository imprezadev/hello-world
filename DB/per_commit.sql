USE moneytrack;

CREATE TABLE moneytrack.credit_card_movement (
  id_transaction  INT NOT NULL,
  operation       VARCHAR(20) NOT NULL,
  remarks         VARCHAR(40) NULL,
  INDEX transaction__credit_card_movement__FK_idx (id_transaction ASC),
  UNIQUE INDEX id_transaction_UQ_idx (id_transaction ASC),
  CONSTRAINT transaction__credit_card_movement__FK
    FOREIGN KEY (id_transaction) REFERENCES moneytrack.transaction (id)
);

INSERT INTO transaction (id, type, date, amount, currency) VALUES (4000, 'CREDIT_CARD_MOVEMENT', '2017-1-31 13:40', 4560.98, 'PEN');
INSERT INTO credit_card_movement (id_transaction, operation) VALUES (4000, 'PAYMENT');

INSERT INTO transaction (id, type, date, amount, currency) VALUES (4001, 'CREDIT_CARD_MOVEMENT', '2017-1-31 23:30', 60.00, 'PEN');
INSERT INTO credit_card_movement (id_transaction, operation) VALUES (4001, 'CREDIT');
