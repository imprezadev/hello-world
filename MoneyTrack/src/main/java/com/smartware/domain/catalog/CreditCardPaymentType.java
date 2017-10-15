package com.smartware.domain.catalog;

public enum CreditCardPaymentType {
	DEPOSIT("Deposit"), BANK_TRANSFER("Bank Transfer");

	private String name;

	private CreditCardPaymentType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
