package com.smartware.app;

import com.smartware.domain.Transaction;

public class App {
 
  public static void main(String[] args) {
    Transaction trans = new Transaction();
    trans.setId(1L);
    trans.setAmount(22f);
    trans.setConcept("Lavada del Impreza");

    System.out.println("Transaction: " + trans);
  }

}