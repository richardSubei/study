package com.subei.designprinciple.ocp.useocp;

public class MaxErrorCountHandler extends AlertHandler {

	public MaxErrorCountHandler(AlertRule alertRule, Notification notification) {
		this.alertRule = alertRule;
		this.notification = notification;
	}
	
	@Override
	public void check(ApiStatInfo apiStatInfo) {
		if (apiStatInfo.getErrorCount() > alertRule.getMaxErrorCount()) {
			notification.notify("错误数过多");
		}
	}

}
