package com.smartware.app;

import com.smartware.domain.Transaction;
import com.smartware.service.TransactionService;

public class App {

	public static void main(String[] args) {
		TransactionService transactionService = new TransactionService();

		System.out.println("Transaction: " + transactionService.getTransaction(3L));
		
		for (Transaction trans: transactionService.getTransactions()) {
			System.out.println("Transaction: " + trans);
		}

	}

}