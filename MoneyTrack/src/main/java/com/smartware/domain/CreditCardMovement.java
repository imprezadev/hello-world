package com.smartware.domain;

import com.smartware.domain.catalog.CreditCardOperation;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class CreditCardMovement extends MoneyMovement {
	private CreditCardOperation creditCardOperation;
	private String remarks;
}
