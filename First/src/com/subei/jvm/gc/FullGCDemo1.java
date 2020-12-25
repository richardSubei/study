package com.subei.jvm.gc;

public class FullGCDemo1 {

//	-Xmx512m -server -verbose:gc -XX:+PrintGCDetails -Xloggc:filthpath -XX:+HeapDumpOnOutOfMemoryError
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			byte[] bytes = new byte[1024 * 1024 * 256];
			System.gc();
			Thread.sleep(2000);
		}
	}
	
//	性能监控
//	cat(大众点评)		zabbix
}
