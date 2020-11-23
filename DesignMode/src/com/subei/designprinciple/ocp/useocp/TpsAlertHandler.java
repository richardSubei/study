package com.subei.designprinciple.ocp.useocp;

public class TpsAlertHandler extends AlertHandler {

	public TpsAlertHandler(AlertRule alertRule, Notification notification) {
		super(alertRule, notification);
	}

	public void check(ApiStatInfo apiStatInfo) {
		if (apiStatInfo.getRequestCount() > alertRule.getMaxTps()) {
			notification.notifyByWeiChat("请求数过多");
		}
	}

}
