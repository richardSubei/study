package com.subei.designprinciple.ocp.useocp;

public class Test {

	public static void main(String[] args) {
		Alert alert = new Alert();
		AlertRule rule = new AlertRule();
		Notification notification = new Notification();
		rule.setMaxTps(50);
		rule.setMaxErrorCount(5);
		TpsAlertHandler tpsAlertHandler = new TpsAlertHandler(rule, notification);
		ErrorCountAlertHandler errorCountAlertHandler = new ErrorCountAlertHandler(rule, notification);
		
		alert.addHandlers(tpsAlertHandler);
		alert.addHandlers(errorCountAlertHandler);

		ApiStatInfo apiStatInfo = new ApiStatInfo();
		apiStatInfo.setRequestCount(100);
		apiStatInfo.setErrorCount(10);
		
		alert.check(apiStatInfo);
		
	}
}
