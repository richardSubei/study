package com.subei.jdkmonitortool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MonitorTest {

	public static void main(String[] args) throws IOException, InterruptedException {
//		fillHeap(1000);		内存分析
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		createBusyThread();
		br.readLine();
		Object obj = new Object();
		testLockThread(obj);
		Thread.sleep(50000);
		synchronized (obj) {
			obj.notify();
			System.out.println("唤醒");
		}
	}
	
//	死循环演示
	public static void createBusyThread() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					
				}
			}
		});
		t.start();
		System.out.println("死循环执行");
	}
	
	public static void testLockThread(final Object obj) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized (obj) {
					try {
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		System.out.println("锁定等待执行");
	}
	
	
	public static void fillHeap(int num) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		for (int i = 0; i < num; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list.add(new OOMObject());
		}
		System.gc();
	}
	
}

class OOMObject {
	public byte[] placeHolder = new byte[64 * 1024];
}
