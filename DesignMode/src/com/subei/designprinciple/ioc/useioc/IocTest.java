package com.subei.designprinciple.ioc.useioc;

public class IocTest {

	public static void main(String[] args) {
		UserServiceTest test = new UserServiceTest();
		JunitApplication.register(test);
		JunitApplication.test();
	}
}
