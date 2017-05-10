package com.smartware.domain;

import lombok.Data;
import lombok.ToString;

import com.smartware.domain.catalog.BankOperation;

@Data
@ToString(callSuper=true)
public class BankMovement extends MoneyMovement {
	private BankOperation operation;
	private String remarks;
}
