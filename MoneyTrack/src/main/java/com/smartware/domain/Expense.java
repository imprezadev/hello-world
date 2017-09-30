package com.smartware.domain;

import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class Expense extends MoneyMovement {
	private PaymentType paymentType;
	private ExpenseCategory category;
	private String detail;
}
