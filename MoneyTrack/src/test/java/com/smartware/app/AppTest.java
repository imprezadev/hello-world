package com.smartware.app;

import com.smartware.service.MoneyTrackServiceTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(MoneyTrackServiceTest.class);

		return suite;
	}

}
