package com.smartware.domain;

import java.util.Date;

import com.smartware.domain.catalog.Currency;

import lombok.Data;

public @Data class Transaction {
	private Long id;
	private Date date;
	private Float amount;
	private Currency currency;
	private String concept;
}
