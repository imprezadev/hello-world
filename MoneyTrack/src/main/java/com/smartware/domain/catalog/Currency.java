package com.smartware.domain.catalog;

public enum Currency {
	PEN("Soles"), USD("Dollars"), EUR("Euros");

	private String name;

	private Currency(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
