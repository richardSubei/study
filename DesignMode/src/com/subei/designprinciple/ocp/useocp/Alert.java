package com.subei.designprinciple.ocp.useocp;

import java.util.ArrayList;
import java.util.List;

public class Alert {

	private List<AlertHandler> handlers = new ArrayList<AlertHandler>();
	
	public void addHandler(AlertHandler alertHandler) {
		handlers.add(alertHandler);
	}
	
	public void check(ApiStatInfo apiStatInfo) {
		for (AlertHandler alertHandler : handlers) {
			alertHandler.check(apiStatInfo);
		}
	}
	
}
