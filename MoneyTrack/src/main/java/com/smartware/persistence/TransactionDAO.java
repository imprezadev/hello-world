package com.smartware.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.smartware.domain.Transaction;

public class TransactionDAO {

	public Transaction getTransaction() {
		Calendar cal = new GregorianCalendar(2017, Calendar.FEBRUARY, 27);
		Date now = cal.getTime();

		Transaction transaction = new Transaction();
		transaction.setId(1L);
		transaction.setDate(now);
		transaction.setAmount(22f);
		transaction.setConcept("Lavada y encerada del Impreza");

		return transaction;
	}

}
