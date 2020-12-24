package com.subei.gc;

import java.util.ArrayList;
import java.util.List;


public class OutOfMemoryDemo {

	static List<Object> list = new ArrayList<Object>();
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			list.add(new byte[1024 * 1024 * 64]);
			Thread.sleep(1000);
		}
	}
	
}
