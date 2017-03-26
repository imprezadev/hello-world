USE moneytrack;

ALTER TABLE transaction
  ADD COLUMN type VARCHAR(20) NULL AFTER id;

INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (5, 'CASH', 'FUN_TASTE', 'Chifa San Luis: chaufa especial + inka kola');

UPDATE transaction
   SET type = 'EXPENSE'
 WHERE id IN (SELECT id_transaction FROM expense);
 
UPDATE transaction
   SET type = 'BANK_MOVEMENT'
 WHERE id IN (SELECT id_transaction FROM bank_movement);
 
ALTER TABLE transaction 
  CHANGE COLUMN type type VARCHAR(40) NOT NULL;