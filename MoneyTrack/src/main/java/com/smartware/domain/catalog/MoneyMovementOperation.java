package com.smartware.domain.catalog;

public enum MoneyMovementOperation {
	EXPENSE("Expense"),
	WITHDRAWAL("Withdrawal"),
	CREDIT_CARD_PAYMENT("CreditCardPayment"),
	GOT_SALARY("GotSalary");

	private String urlRoot;

	private MoneyMovementOperation(String urlRoot) {
		this.urlRoot = urlRoot;
	}

	public String getUrlRoot() {
		return urlRoot;
	}

}
