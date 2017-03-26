USE moneytrack;

ALTER TABLE expense
	ADD COLUMN detail VARCHAR(100) NULL AFTER category;

UPDATE expense e
 INNER JOIN transaction t ON t.id = e.id_transaction
   SET e.detail = t.concept;

ALTER TABLE expense 
	CHANGE COLUMN detail detail VARCHAR(100) NOT NULL;

ALTER TABLE transaction
	DROP COLUMN concept;