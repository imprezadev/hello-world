package com.smartware.domain;

import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;

import lombok.Data;

@Data
public class Expense extends Transaction {
	private PaymentType paymenType;
	private ExpenseCategory category;
}
