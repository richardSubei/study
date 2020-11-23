package com.subei.designprinciple.ocp.useocp;

import java.util.List;

public class Alert {

	private List<AlertHandler> alertHandlers;
	
	public void addHandlers(AlertHandler alertHandler) {
		alertHandlers.add(alertHandler);
	}
	
	public void check(ApiStatInfo apiStatInfo) {
		for (AlertHandler alertHandler : alertHandlers) {
			alertHandler.check(apiStatInfo);
		}
	}
	
}
