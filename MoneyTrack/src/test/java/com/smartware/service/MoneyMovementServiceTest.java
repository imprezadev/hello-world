package com.smartware.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.TransactionType;

import junit.framework.TestCase;

public class MoneyMovementServiceTest extends TestCase {

	private static final Logger logger = Logger.getLogger(MoneyMovementServiceTest.class.getName());

	private final Date            testDate            = new GregorianCalendar(2017, Calendar.JANUARY, 22, 15, 25).getTime();
	private final Float           testAmount          = 80f;
	private final Currency        testCurrency        = Currency.PEN;
	private final TransactionType testTransactionType = TransactionType.EXPENSE;  

	private MoneyMovement getTestMoneyMovement() {
		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setDate(testDate);
		moneyMovement.setAmount(testAmount);
		moneyMovement.setCurrency(testCurrency);
		moneyMovement.setType(testTransactionType);

 		return moneyMovement;
	}

	private boolean compareToTestMoneyMovement(MoneyMovement moneyMovement) {
		return      (moneyMovement.getDate().compareTo(testDate) == 0)
				&&	(moneyMovement.getAmount().compareTo(testAmount) == 0)
				&&	(moneyMovement.getCurrency().compareTo(testCurrency) == 0)
				&& 	(moneyMovement.getType().compareTo(testTransactionType) == 0);
	}

	public void testGetMoneyMovements() {
		MoneyMovementService moneyMovementService = new MoneyMovementService();
		boolean notEmpty = !moneyMovementService.getMoneyMovements().isEmpty();
		assertTrue(notEmpty);
	}

	public void testInsertMoneyMovement() {
		MoneyMovementService moneyMovementService = new MoneyMovementService();

		MoneyMovement newMoneyMovement = getTestMoneyMovement();
		long id = moneyMovementService.insertMoneyMovement(newMoneyMovement);

		MoneyMovement insertedMoneyMovement = moneyMovementService.getMoneyMovement(id);
		logger.info(insertedMoneyMovement.toString());

		boolean testResult = compareToTestMoneyMovement(insertedMoneyMovement);
		assertTrue(testResult);
	}

}
