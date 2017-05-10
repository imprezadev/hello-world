package com.smartware.app;

import com.smartware.service.BankMovementServiceTest;
import com.smartware.service.ExpenseServiceTest;
import com.smartware.service.MoneyMovementServiceTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(MoneyMovementServiceTest.class);
		suite.addTestSuite(ExpenseServiceTest.class);
		suite.addTestSuite(BankMovementServiceTest.class);

		return suite;
	}

}
