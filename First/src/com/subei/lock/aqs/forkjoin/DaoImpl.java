package com.subei.lock.aqs.forkjoin;

import java.util.ArrayList;
import java.util.List;

public class DaoImpl {

	public List<String> get(String sql) {
		List<String> list = new ArrayList<String>();
		list.add("结果集：" + sql);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}
}
