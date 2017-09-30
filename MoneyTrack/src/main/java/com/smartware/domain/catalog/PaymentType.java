package com.smartware.domain.catalog;

public enum PaymentType {
	CASH("Cash"), DEBIT("Debit"), CREDIT("Credit");

	private String name;

	private PaymentType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
