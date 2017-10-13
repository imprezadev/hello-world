package com.smartware.domain;

import com.smartware.domain.catalog.PaymentType;

import lombok.Data;

@Data
public class CreditCardPayment extends MoneyMovement {
	private PaymentType paymentType; 
	private String remarks;
}
