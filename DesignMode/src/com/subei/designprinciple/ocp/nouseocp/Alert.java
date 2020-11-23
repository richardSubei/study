package com.subei.designprinciple.ocp.nouseocp;

public class Alert {

	private AlertRule rule;
	private Notification notification;
	
	public Alert(AlertRule rule, Notification notification) {
		this.rule = rule;
		this.notification = notification;
	}
	
	public void check(int tps, int errorCount) {
		if (tps > rule.getMaxTps()) {
			notification.notify1("tps超过警戒值");
		}
		if (errorCount > rule.getMaxErrorCount()) {
			notification.notify1("错误请求数过多");
		}
	}

}
