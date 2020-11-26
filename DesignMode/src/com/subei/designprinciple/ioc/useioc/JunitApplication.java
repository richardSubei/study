package com.subei.designprinciple.ioc.useioc;

import java.util.ArrayList;
import java.util.List;

public class JunitApplication {

	private static List<TestCase> testCases = new ArrayList<TestCase>();
	
	public static void register(TestCase testCase) {
		testCases.add(testCase);
	}
	
	public static void test() {
		for (TestCase testCase : testCases) {
			testCase.run();
		}
	}
}
