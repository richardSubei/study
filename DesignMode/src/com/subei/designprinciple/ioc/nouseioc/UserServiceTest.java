package com.subei.designprinciple.ioc.nouseioc;

public class UserServiceTest {

	public static boolean doTest() {
		return true;
	}
	
	public static void main(String[] args) {
//		流程都由程序员控制	控制反转 控制是指对程序流程的控制 反转是指由程序员控制变为框架控制
		if (doTest()) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
	}
	
}
