package com.smartware.domain.catalog;

public enum ExpenseCategory {
	COMMUTE("Commute"), 
	LUNCH("Lunch"), 
	FUN("Fun"), 
	FUN_TASTE("Fun Taste"), 
	DIEGO_SCHOOL("Diego School"), 
	CAREER_DEV("Career Development"), 
	HOME_SERVICE("Home Service"), 
	IMPREZA_CLEAN("Impreza Clean"), 
	IMPREZA_FUEL("Impreza Fuel");
	
	private String name;

	private ExpenseCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
