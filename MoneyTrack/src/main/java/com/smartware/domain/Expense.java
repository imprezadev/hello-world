package com.smartware.domain;

import lombok.Data;

@Data
public class Expense extends Transaction {
	private PaymentType paymenType;
	private ExpenseCategory category;
}
