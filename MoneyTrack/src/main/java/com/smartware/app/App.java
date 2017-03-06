package com.smartware.app;

import java.util.Calendar;
import java.util.Date;
import com.smartware.domain.Transaction;

public class App {

  public static void main(String[] args) {
    Calendar cal = Calendar.getInstance();
    Date now = cal.getTime();

    Transaction trans = new Transaction();
    trans.setId(1L);
    trans.setDate(now);
    trans.setAmount(22f);
    trans.setConcept("Lavada del Impreza");

    System.out.println("Transaction: " + trans);
  }

}