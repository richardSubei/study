package com.subei.designprinciple.ocp.useocp;

public class MaxTpsHandler extends AlertHandler{

	public MaxTpsHandler(AlertRule alertRule, Notification notification) {
		this.alertRule = alertRule;
		this.notification = notification;
	}
	
	@Override
	public void check(ApiStatInfo apiStatInfo) {
		if (apiStatInfo.getRequestCount() > alertRule.getMaxTps()) {
			notification.notify("请求数过多");
		}
	}

	
}
