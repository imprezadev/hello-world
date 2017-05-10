package com.smartware.service;

import java.util.List;

import com.smartware.domain.MoneyMovement;
import com.smartware.persistence.MoneyMovementDAO;

public class MoneyMovementService {

	MoneyMovementDAO moneyMovementDAO = new MoneyMovementDAO();

	public MoneyMovement getMoneyMovement(long id) {
		return moneyMovementDAO.getMoneyMovement(id);
	}

	public List<MoneyMovement> getMoneyMovements() {
		return moneyMovementDAO.getMoneyMovements();
	}

	public long insertMoneyMovement(MoneyMovement moneyMovement) {
		return moneyMovementDAO.insertMoneyMovement(moneyMovement);
	}

}
