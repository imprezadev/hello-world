package com.smartware.service;

import java.util.List;

import com.smartware.domain.Transaction;
import com.smartware.persistence.TransactionDAO;

public class TransactionService {

	TransactionDAO transactionDAO = new TransactionDAO();

	public Transaction getTransaction(long id) {
		return transactionDAO.getTransaction(id);
	}

	public List<Transaction> getTransactions() {
		return transactionDAO.getTransactions();
	}

	public long insertTransaction(Transaction transaction) {
		return transactionDAO.insertTransaction(transaction);
	}

}
