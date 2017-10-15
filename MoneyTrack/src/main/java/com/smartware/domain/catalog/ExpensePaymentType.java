package com.smartware.domain.catalog;

public enum ExpensePaymentType {
	CASH("Cash"), DEBIT("Debit"), CREDIT_CARD("Credit Card"), BANK_TRANSFER("Bank Transfer");

	private String name;

	private ExpensePaymentType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
