USE moneytrack;

INSERT INTO transaction (id, type, date, amount, currency) VALUES (1, 'EXPENSE', '2017-2-27', 22, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (2, 'EXPENSE', '2017-03-17', 51.9, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (3, 'EXPENSE', '2017-03-17', 5.50, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (4, 'EXPENSE', '2017-03-11', 2023.45, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (5, 'EXPENSE', '2017-03-07 21:30', 18.50, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (6, 'EXPENSE', '2017-2-01 00:30', 9.90, 'USD');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (7, 'EXPENSE', '2017-03-23 10:37', 176.59, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (8, 'BANK_MOVEMENT', '2017-1-12 15:40', 80, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (9, 'BANK_MOVEMENT', '2017-2-27 12:15', 7560.76, 'PEN');
INSERT INTO transaction (id, type, date, amount, currency) VALUES (10, 'BANK_MOVEMENT', '2017-1-20 8:40', 50, 'PEN');

INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (1, 'DEBIT', 'IMPREZA_CLEAN', 'lavada y encerada del impreza');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (2, 'CREDIT', 'FUN_TASTE', 'almuerzo en Caplina Sta Cruz');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (3, 'CASH', 'FUN_TASTE', 'pastel en Vlady Miraflores');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (4, 'CREDIT', 'DIEGO_SCHOOL', 'ULima Diego - semestre verano');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (5, 'CASH', 'FUN_TASTE', 'Chifa San Luis: chaufa especial + inka kola');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (6, 'DEBIT', 'HOME_SERVICE', 'NETFLIX');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (7, 'CREDIT', 'IMPREZA_FUEL', 'G-Prix, 12.6Gl, grifo JPrado');

INSERT INTO bank_movement (id_transaction, operation) VALUES (8, 'WITHDRAWAL');
INSERT INTO bank_movement (id_transaction, operation, remarks) VALUES (9, 'DEPOSIT', 'SALARY FEB2017');
INSERT INTO bank_movement (id_transaction, operation, remarks) VALUES (10, 'TRANSFER_OUT', 'DIEGO - ULIMA Gastos semana 20-24ENE2017');

SELECT * FROM transaction;
SELECT * FROM expense;
SELECT * FROM bank_movement;
