package com.smartware.service;

import java.util.List;

import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.TransactionType;
import com.smartware.persistence.CreditCardMovementDAO;
import com.smartware.persistence.MoneyMovementDAO;

public class CreditCardMovementService {

	
	CreditCardMovementDAO creditCardMovementDAO = new CreditCardMovementDAO();
	MoneyMovementDAO moneyMovementDAO = new MoneyMovementDAO();

	public CreditCardMovement getCreditCardMovement(long id) {
	  return creditCardMovementDAO.getCreditCardMovement(id);
	}
	
	public List<CreditCardMovement> getCreditCardMovements() {
		return creditCardMovementDAO.getCreditCardMovements();
	}

	public long insertCreditCardMovement(CreditCardMovement creditCardMovement) {
		long id = -1;

		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setType(TransactionType.CREDIT_CARD_MOVEMENT);
		moneyMovement.setDate(creditCardMovement.getDate());
		moneyMovement.setAmount(creditCardMovement.getAmount());
		moneyMovement.setCurrency(creditCardMovement.getCurrency());

		id = moneyMovementDAO.insertMoneyMovement(moneyMovement);

		creditCardMovement.setId(id);
		creditCardMovementDAO.insertCreditCardMovement(creditCardMovement);

		return id;
	}

}
