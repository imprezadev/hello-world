DELETE FROM `moneytrack`.`expense` WHERE id_transaction > 0;
DELETE FROM `moneytrack`.`transaction` WHERE id > 0;

INSERT INTO `moneytrack`.`transaction` (`id`, `date`, `amount`, `concept`) VALUES ('1', '2017-2-27', '22', 'lavada y encerada del impreza');
INSERT INTO `moneytrack`.`expense` (`id_transaction`, `payment_type`, `category`) VALUES ('1', 'DEBIT', 'IMPREZA_CLEAN');
INSERT INTO `moneytrack`.`transaction` (`id`, `date`, `amount`, `concept`) VALUES ('2', '2017-03-17', '51.9', 'almuerzo en Caplina Sta Cruz');
INSERT INTO `moneytrack`.`expense` (`id_transaction`, `payment_type`, `category`) VALUES ('2', 'CREDIT', 'FUN_TASTE');
INSERT INTO `moneytrack`.`transaction` (`id`, `date`, `amount`, `concept`) VALUES ('3', '2017-03-17', '5.50', 'pastel en Vlady Miraflores');
INSERT INTO `moneytrack`.`expense` (`id_transaction`, `payment_type`, `category`) VALUES ('3', 'CASH', 'FUN_TASTE');
INSERT INTO `moneytrack`.`transaction` (`id`, `date`, `amount`, `concept`) VALUES ('4', '2017-03-11', '2023.45', 'ULima Diego - semestre verano');
INSERT INTO `moneytrack`.`expense` (`id_transaction`, `payment_type`, `category`) VALUES ('4', 'CREDIT', 'DIEGO_SCHOOL');
INSERT INTO `moneytrack`.`transaction` (`id`, `date`, `amount`, `concept`) VALUES ('5', '2017-03-07 21:30', '18.50', 'Chifa especial + inca kola, chifa San Luis');
INSERT INTO `moneytrack`.`transaction` (`id`, `date`, `amount`, `currency`, `concept`) VALUES ('6', '2017-2-01 00:30', '9.90', 'USD', 'NETFLIX');
INSERT INTO `moneytrack`.`expense` (`id_transaction`, `payment_type`, `category`) VALUES ('6', 'DEBIT', 'HOME_SERVICE');
INSERT INTO `moneytrack`.`transaction` (`id`, `date`, `amount`, `currency`, `concept`) VALUES ('7', '2017-03-23 10:37', '176.59', 'USD', 'G-Prix, 12.6Gl, grifo JPrado');
INSERT INTO `moneytrack`.`expense` (`id_transaction`, `payment_type`, `category`) VALUES ('7', 'CREDIT', 'IMPREZA_FUEL');
