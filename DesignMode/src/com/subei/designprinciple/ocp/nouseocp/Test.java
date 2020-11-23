package com.subei.designprinciple.ocp.nouseocp;


public class Test {

	public static void main(String[] args) {
		AlertRule rule = new AlertRule();
		Notification notification = new Notification();
		rule.setMaxTps(50);
		rule.setMaxErrorCount(5);
		
		Alert alert = new Alert(rule, notification);
		alert.check(100, 10);
		
	}
}
