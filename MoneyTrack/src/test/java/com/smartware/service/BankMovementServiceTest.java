package com.smartware.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import com.smartware.domain.BankMovement;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.TransactionType;

import junit.framework.TestCase;

public class BankMovementServiceTest extends TestCase {

	private final Logger logger = Logger.getLogger(BankMovementServiceTest.class.getName());

	private Date          testDate          = new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime();
	private Float         testAmount        = 10f;
	private Currency      testCurrency      = Currency.PEN;
	private BankOperation testBankOperation = BankOperation.DEBIT;

	private BankMovement getTestBankMovement() {
		BankMovement bankMovement = new BankMovement();
		bankMovement.setDate(testDate);
		bankMovement.setAmount(testAmount);
		bankMovement.setCurrency(testCurrency);
		bankMovement.setOperation(testBankOperation);

		return bankMovement;
	}

	private boolean compareToTestBankMovement(BankMovement bankMovement) {
		return      (bankMovement.getDate().compareTo(testDate) == 0) 
				&&	(bankMovement.getAmount().compareTo(testAmount) == 0) 
				&&	(bankMovement.getCurrency().compareTo(testCurrency) == 0)
				&&	(bankMovement.getOperation().compareTo(testBankOperation) == 0);
	}

	public void testGetBankMovements() {
		BankMovementService bankMovementService = new BankMovementService();
		boolean notEmpty = !bankMovementService.getBankMovements().isEmpty();
		assertTrue(notEmpty);
	}

	public void testInsertBankMovement() {
		BankMovementService bankMovementService = new BankMovementService();
		MoneyMovementService moneyMovementService = new MoneyMovementService();
	
		BankMovement newBankMovement = getTestBankMovement();
		long id = bankMovementService.insertBankMovement(newBankMovement);
	
		MoneyMovement insertedMoneyMovement = moneyMovementService.getMoneyMovement(id);
		logger.info(insertedMoneyMovement.toString());
	
		BankMovement insertedBankMovement = bankMovementService.getBankMovement(id);
		logger.info(insertedBankMovement.toString());
	
		boolean testResult = compareToTestBankMovement(newBankMovement) && insertedMoneyMovement.getType().equals(TransactionType.BANK_MOVEMENT);
		assertTrue(testResult);
	}

}
