package com.smartware.service;

import java.util.List;

import com.smartware.domain.BankMovement;
import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.TransactionType;
import com.smartware.persistence.BankMovementDAO;
import com.smartware.persistence.CreditCardMovementDAO;
import com.smartware.persistence.TransactionDAO;

public class CreditCardMovementService {

	
	CreditCardMovementDAO creditCardMovementDAO = new CreditCardMovementDAO();
	TransactionDAO transactionDAO = new TransactionDAO();

	public CreditCardMovement getCreditCardMovement(long id) {
	  return creditCardMovementDAO.getCreditCardMovement(id);
	}
	
	public List<CreditCardMovement> getCreditCardMovements() {
		return creditCardMovementDAO.getCreditCardMovements();
	}

	public long insertCreditCardMovement(CreditCardMovement creditCardMovement) {
		long id = -1;

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.CREDIT_CARD_MOVEMENT);
		transaction.setDate(creditCardMovement.getDate());
		transaction.setAmount(creditCardMovement.getAmount());
		transaction.setCurrency(creditCardMovement.getCurrency());

		id = transactionDAO.insertTransaction(transaction);

		creditCardMovement.setId(id);
		creditCardMovementDAO.insertCreditCardMovement(creditCardMovement);

		return id;
	}

}
