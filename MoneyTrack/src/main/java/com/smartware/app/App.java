package com.smartware.app;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.smartware.domain.Transaction;

public class App {

  public static void main(String[] args) {
    Calendar cal = new GregorianCalendar(2017, Calendar.MARCH, 4);
    Date now = cal.getTime();

    Transaction trans = new Transaction();
    trans.setId(1L);
    trans.setDate(now);
    trans.setAmount(22f);
    trans.setConcept("Lavada del Impreza");

    System.out.println("Transaction: " + trans);
  }

}