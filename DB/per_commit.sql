USE moneytrack;

ALTER TABLE transaction
  RENAME TO money_movement;

ALTER TABLE expense
  DROP FOREIGN KEY FK_transaction_expense;

ALTER TABLE expense
  CHANGE COLUMN id_transaction id_money_movement INT(11) NOT NULL;

ALTER TABLE expense
  ADD CONSTRAINT FK__expense__money_movement FOREIGN KEY(id_money_movement) REFERENCES money_movement(id);

ALTER TABLE bank_movement
  CHANGE COLUMN id_transaction id_money_movement INT(11) NOT NULL;

ALTER TABLE bank_movement
  DROP FOREIGN KEY transaction__bank_movement__FK;

ALTER TABLE bank_movement
  ADD CONSTRAINT FK__bank_movement__money_movement FOREIGN KEY(id_money_movement) REFERENCES money_movement(id);

ALTER TABLE credit_card_movement
  CHANGE COLUMN id_transaction id_money_movement INT(11) NOT NULL;

ALTER TABLE credit_card_movement
  DROP FOREIGN KEY transaction__credit_card_movement__FK;

ALTER TABLE credit_card_movement
  ADD CONSTRAINT FK__credit_card_movement__money_movement FOREIGN KEY(id_money_movement) REFERENCES money_movement(id);
