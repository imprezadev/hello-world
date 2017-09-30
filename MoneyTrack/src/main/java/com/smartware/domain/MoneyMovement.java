package com.smartware.domain;

import java.util.Date;

import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.TransactionType;

import lombok.Data;

public @Data class MoneyMovement implements Comparable<MoneyMovement> {
	private Long id;
	private TransactionType type;
	private Date date;
	private Float amount;
	private Currency currency;

	@Override
	public int compareTo(MoneyMovement o) { // sort by date, ascending order
		return this.date.compareTo(o.getDate());
	}
}
