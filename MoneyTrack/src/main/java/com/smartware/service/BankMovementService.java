package com.smartware.service;

import java.util.List;

import com.smartware.domain.BankMovement;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.TransactionType;
import com.smartware.persistence.BankMovementDAO;
import com.smartware.persistence.MoneyMovementDAO;

public class BankMovementService {
	
	BankMovementDAO bankMovementDAO = new BankMovementDAO();
	MoneyMovementDAO moneyMovementDAO = new MoneyMovementDAO();

	public BankMovement getBankMovement(long id) {
	  return bankMovementDAO.getBankMovement(id);
	}
	
	public List<BankMovement> getBankMovements() {
		return bankMovementDAO.getBankMovements();
	}

	public long insertBankMovement(BankMovement bankMovement) {
		long id = -1;

		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setType(TransactionType.BANK_MOVEMENT);
		moneyMovement.setDate(bankMovement.getDate());
		moneyMovement.setAmount(bankMovement.getAmount());
		moneyMovement.setCurrency(bankMovement.getCurrency());

		id = moneyMovementDAO.insertMoneyMovement(moneyMovement);

		bankMovement.setId(id);
		bankMovementDAO.insertBankMovement(bankMovement);

		return id;
	}

}
