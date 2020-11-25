package com.subei.designprinciple.ocp.useocp;

public class Test {

	public static void main(String[] args) {
		AlertRule alertRule = new AlertRule();
		alertRule.setMaxTps(1000);
		alertRule.setMaxErrorCount(10);
		ApiStatInfo apiStatInfo = new ApiStatInfo();
		apiStatInfo.setRequestCount(2000);
		apiStatInfo.setErrorCount(20);
		Notification notification = new Notification();
		
		Alert alert = new Alert();
		
		MaxTpsHandler handler = new MaxTpsHandler(alertRule, notification);
		MaxErrorCountHandler handler2 = new MaxErrorCountHandler(alertRule, notification);
		alert.addHandler(handler);
		alert.addHandler(handler2);
		
		alert.check(apiStatInfo);
	}
}
