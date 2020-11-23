package com.subei.designprinciple.ocp.useocp;

public class ErrorCountAlertHandler extends AlertHandler {

	public ErrorCountAlertHandler(AlertRule alertRule, Notification notification) {
		super(alertRule, notification);
	}

	public void check(ApiStatInfo apiStatInfo) {
		if (apiStatInfo.getRequestCount() > alertRule.getMaxTps()) {
			notification.notifyByWeiChat("请求数过多");
		}
	}

}
