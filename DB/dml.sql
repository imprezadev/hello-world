USE moneytrack;

INSERT INTO transaction (id, date, amount, currency) VALUES ('1', '2017-2-27', '22', 'PEN');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES ('1', 'DEBIT', 'IMPREZA_CLEAN', 'lavada y encerada del impreza');
INSERT INTO transaction (id, date, amount, currency) VALUES ('2', '2017-03-17', '51.9', 'PEN');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES ('2', 'CREDIT', 'FUN_TASTE', 'almuerzo en Caplina Sta Cruz');
INSERT INTO transaction (id, date, amount, currency) VALUES ('3', '2017-03-17', '5.50', 'PEN');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES ('3', 'CASH', 'FUN_TASTE', 'pastel en Vlady Miraflores');
INSERT INTO transaction (id, date, amount, currency) VALUES ('4', '2017-03-11', '2023.45', 'PEN');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES ('4', 'CREDIT', 'DIEGO_SCHOOL', 'ULima Diego - semestre verano');
INSERT INTO transaction (id, date, amount, currency) VALUES ('5', '2017-03-07 21:30', '18.50', 'PEN');
INSERT INTO transaction (id, date, amount, currency) VALUES ('6', '2017-2-01 00:30', '9.90', 'USD');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES ('6', 'DEBIT', 'HOME_SERVICE', 'NETFLIX');
INSERT INTO transaction (id, date, amount, currency) VALUES ('7', '2017-03-23 10:37', '176.59', 'PEN');
INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES ('7', 'CREDIT', 'IMPREZA_FUEL', 'G-Prix, 12.6Gl, grifo JPrado');
