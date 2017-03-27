package com.smartware.service;

import java.util.List;

import com.smartware.domain.BankMovement;
import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.TransactionType;
import com.smartware.persistence.BankMovementDAO;
import com.smartware.persistence.TransactionDAO;

public class BankMovementService {
	
	BankMovementDAO bankMovementDAO = new BankMovementDAO();
	TransactionDAO transactionDAO = new TransactionDAO();

	public BankMovement getBankMovement(long id) {
	  return bankMovementDAO.getBankMovement(id);
	}
	
	public List<BankMovement> getBankMovements() {
		return bankMovementDAO.getBankMovements();
	}

	public long insertBankMovement(BankMovement bankMovement) {
		long id = -1;

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.BANK_MOVEMENT);
		transaction.setDate(bankMovement.getDate());
		transaction.setAmount(bankMovement.getAmount());
		transaction.setCurrency(bankMovement.getCurrency());

		id = transactionDAO.insertTransaction(transaction);

		bankMovement.setId(id);
		bankMovementDAO.insertBankMovement(bankMovement);

		return id;
	}

}
