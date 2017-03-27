package com.smartware.app;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import com.smartware.domain.BankMovement;
import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.Expense;
import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;
import com.smartware.service.BankMovementService;
import com.smartware.service.CreditCardMovementService;
import com.smartware.service.ExpenseService;
import com.smartware.service.TransactionService;

public class App {
	final static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		long id;

		for (CreditCardMovement creditCardMovement: getCreditCardMovementsFromDB()) {
			logger.info("CREDIT CARD MOVEMENTS:");
			logger.info(creditCardMovement.toString());
		}

		CreditCardMovement creditCardMovement = buildCreditCardMovement();
		logger.info("BUILDED CREDIT CARD  MOVEMENT:");
		logger.info(creditCardMovement.toString());

		id = insertCreditCardMovement(creditCardMovement);

		CreditCardMovement creditCardMovementFromDB = getCreditCardMovementFromDB(id);
		logger.info("BUILDED CREDIT CARD  MOVEMENT FROM DB:");
		logger.info(creditCardMovementFromDB.toString());
	}

	private static List<Transaction> getTransactionsFromDB() {
		TransactionService transactionService = new TransactionService();
		return transactionService.getTransactions();
	}

	private static Transaction buildTransaction() {
		Transaction transaction = new Transaction();
		transaction.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 22, 15, 25).getTime());
		transaction.setAmount(80f);
		transaction.setCurrency(Currency.PEN);
 		
 		return transaction;
	}

	private static long insertTransaction(Transaction transaction) {
		TransactionService transactionService = new TransactionService();
		return transactionService.insertTransaction(transaction);
	}

	private static Transaction getTransactionFromDB(long id) {
		TransactionService transactionService = new TransactionService();
		return transactionService.getTransaction(id);
	}

	private static List<Expense> getExpensesFromDB() {
		ExpenseService expenseService = new ExpenseService();
		return expenseService.getExpenses();
	}

	private static Expense buildExpense() {
		Expense expense = new Expense();
		expense.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		expense.setAmount(10f);
 		expense.setCurrency(Currency.PEN);
 		expense.setPaymenType(PaymentType.CASH);
 		expense.setDetail("Altoque Menu Delivery");
 		expense.setCategory(ExpenseCategory.LUNCH);
 		
 		return expense;
	}

	private static long insertExpense(Expense expense) {
		ExpenseService expenseService = new ExpenseService();
		return expenseService.insertExpense(expense);

	}

	private static Expense getExpenseFromDB(long id) {
		ExpenseService expenseService = new ExpenseService();
		return expenseService.getExpense(id);
	}

	private static List<BankMovement> getBankMovementsFromDB() {
		BankMovementService bankMovementService = new BankMovementService();
		return bankMovementService.getBankMovements();
	}

	private static BankMovement buildBankMovement() {
		BankMovement bankMovement = new BankMovement();
		bankMovement.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		bankMovement.setAmount(10f);
		bankMovement.setCurrency(Currency.PEN);
		bankMovement.setOperation(BankOperation.DEBIT);
 		
 		return bankMovement;
	}

	private static long insertBankMovement(BankMovement bankMovement) {
		BankMovementService bankMovementService = new BankMovementService();
		return bankMovementService.insertBankMovement(bankMovement);

	}

	private static BankMovement getBankMovementFromDB(long id) {
		BankMovementService bankMovementService = new BankMovementService();
		return bankMovementService.getBankMovement(id);
	}

	private static List<CreditCardMovement> getCreditCardMovementsFromDB() {
		CreditCardMovementService creditCardMovementService = new CreditCardMovementService();
		return creditCardMovementService.getCreditCardMovements();
	}

	private static CreditCardMovement buildCreditCardMovement() {
		CreditCardMovement creditCardMovement = new CreditCardMovement();
		creditCardMovement.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		creditCardMovement.setAmount(2000f);
		creditCardMovement.setCurrency(Currency.PEN);
		creditCardMovement.setOperation(CreditCardOperation.PAYMENT);

 		return creditCardMovement;
	}

	private static long insertCreditCardMovement(CreditCardMovement bankMovement) {
		CreditCardMovementService creditCardMovementService = new CreditCardMovementService();
		return creditCardMovementService.insertCreditCardMovement(bankMovement);
	}

	private static CreditCardMovement getCreditCardMovementFromDB(long id) {
		CreditCardMovementService creditCardMovementService = new CreditCardMovementService();
		return creditCardMovementService.getCreditCardMovement(id);
	}

}