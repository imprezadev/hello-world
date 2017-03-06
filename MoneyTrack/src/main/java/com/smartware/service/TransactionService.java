package com.smartware.service;

import com.smartware.domain.Transaction;
import com.smartware.persistence.TransactionDAO;

public class TransactionService {

	TransactionDAO trnsactionDAO = new TransactionDAO();

	public Transaction getTransaction() {
		return trnsactionDAO.getTransaction();
	}

}
