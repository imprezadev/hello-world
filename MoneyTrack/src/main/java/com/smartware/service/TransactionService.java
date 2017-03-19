package com.smartware.service;

import java.util.List;

import com.smartware.domain.Transaction;
import com.smartware.persistence.TransactionDAO;

public class TransactionService {

	TransactionDAO trnsactionDAO = new TransactionDAO();

	public Transaction getTransaction(long id) {
		return trnsactionDAO.getTransaction(id);
	}

	public List<Transaction> getTransactions() {
		return trnsactionDAO.getTransactions();
	}
}
