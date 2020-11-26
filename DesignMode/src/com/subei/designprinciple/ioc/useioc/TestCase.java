package com.subei.designprinciple.ioc.useioc;

public abstract class TestCase {

	public void run() {
		if (doTest()) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
	}
	
	public abstract boolean doTest();
}
