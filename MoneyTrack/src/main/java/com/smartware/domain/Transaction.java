package com.smartware.domain;

import java.util.Date;

import lombok.Data;

public @Data class Transaction {
	private Long id;
	private Date date;
	private Float amount;
	private String concept;
}
