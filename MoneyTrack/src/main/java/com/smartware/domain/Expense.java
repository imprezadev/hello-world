package com.smartware.domain;

import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class Expense extends Transaction {
	private PaymentType paymenType;
	private ExpenseCategory category;
}
