USE moneytrack;

ALTER TABLE moneytrack.expense 
  CHANGE COLUMN payment_type payment_type VARCHAR(15) NOT NULL;
