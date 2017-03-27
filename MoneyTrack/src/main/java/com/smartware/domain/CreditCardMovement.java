package com.smartware.domain;

import com.smartware.domain.catalog.CreditCardOperation;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class CreditCardMovement extends Transaction {
	private CreditCardOperation operation;
	private String remarks;
}
