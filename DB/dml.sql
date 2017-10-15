USE moneytrack;

INSERT INTO money_movement (id, date, amount, currency) VALUES (1, '2017-2-27', 22, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (2, '2017-03-17', 51.9, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (3, '2017-03-17', 5.50, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (4, '2017-03-11', 2023.45, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (5, '2017-03-07 21:30', 18.50, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (6, '2017-2-01 00:30', 9.90, 'USD');
INSERT INTO money_movement (id, date, amount, currency) VALUES (7, '2017-03-23 10:37', 176.59, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (8, '2017-1-12 15:40', 80, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (9, '2017-2-27 12:15', 7560.76, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (10, '2017-1-20 8:40', 50, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (11, '2017-1-31 13:40', 4560.98, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (12, '2017-1-31 23:30', 60.00, 'PEN');
INSERT INTO money_movement (id, date, amount, currency) VALUES (13, '2017-8-16 23:30', 879.00, 'PEN');

INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (1, 'DEBIT', 'IMPREZA_CLEAN', 'lavada y encerada del impreza');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (2, 'CREDIT_CARD', 'FUN_TASTE', 'almuerzo en Caplina Sta Cruz');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (3, 'CASH', 'FUN_TASTE', 'pastel en Vlady Miraflores');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (4, 'CREDIT_CARD', 'DIEGO_SCHOOL', 'ULima Diego - semestre verano');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (5, 'CASH', 'FUN_TASTE', 'Chifa San Luis: chaufa especial + inka kola');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (6, 'DEBIT', 'HOME_SERVICE', 'NETFLIX');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (7, 'CREDIT_CARD', 'IMPREZA_FUEL', 'G-Prix, 12.6Gl, grifo JPrado');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (12, 'CASH', 'HOME_SERVICE', 'Limpieza casa');
INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (13, 'BANK_TRANSFER', 'CAREER_DEV', 'BSGrupo, Big Data Implementation');

INSERT INTO bank_movement (id_money_movement, operation, remarks) VALUES (1, 'DEBIT', 'EXPENSE');
INSERT INTO bank_movement (id_money_movement, operation, remarks) VALUES (6, 'DEBIT', 'EXPENSE');
INSERT INTO bank_movement (id_money_movement, operation, remarks) VALUES (8, 'WITHDRAWAL', 'TIPICAL WITHDRAWAL');
INSERT INTO bank_movement (id_money_movement, operation, remarks) VALUES (9, 'TRANSFER_IN', 'SALARY FEB2017');
INSERT INTO bank_movement (id_money_movement, operation, remarks) VALUES (10, 'TRANSFER_OUT', 'DIEGO - ULIMA Gastos semana 20-24ENE2017');
INSERT INTO bank_movement (id_money_movement, operation, remarks) VALUES (13, 'TRANSFER_OUT', 'EXPENSE');

INSERT INTO credit_card_movement (id_money_movement, operation, remarks) VALUES (2, 'CREDIT', 'EXPENSE');
INSERT INTO credit_card_movement (id_money_movement, operation, remarks) VALUES (4, 'CREDIT', 'EXPENSE');
INSERT INTO credit_card_movement (id_money_movement, operation, remarks) VALUES (7, 'CREDIT', 'EXPENSE');
INSERT INTO credit_card_movement (id_money_movement, operation, remarks) VALUES (11, 'PAYMENT', 'TOTAL FACTURADO 2908.76');

SELECT * FROM money_movement;
SELECT * FROM expense;
SELECT * FROM bank_movement;
SELECT * FROM credit_card_movement;
