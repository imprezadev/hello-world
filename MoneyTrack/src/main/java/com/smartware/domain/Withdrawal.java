package com.smartware.domain;

import lombok.Data;

@Data
public class Withdrawal extends MoneyMovement {
	private String remarks;
}
