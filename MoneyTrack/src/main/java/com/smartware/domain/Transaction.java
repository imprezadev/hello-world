package com.smartware.domain;

import java.util.Date;

public class Transaction {
	private Long id;
	private Date date;
	private Float amount;
	private String concept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", amount="
				+ amount + ", concept=" + concept + "]";
	}

}
