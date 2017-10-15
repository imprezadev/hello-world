package com.smartware.domain;

import com.smartware.domain.catalog.CreditCardPaymentType;

import lombok.Data;

@Data
public class CreditCardPayment extends MoneyMovement {
	private CreditCardPaymentType paymentType; 
	private String remarks;
}
