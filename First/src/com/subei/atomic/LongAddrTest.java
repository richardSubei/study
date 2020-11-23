package com.subei.atomic;

import java.util.concurrent.atomic.LongAdder;

public class LongAddrTest {

//	AtomicInteger AtomicLong 等 使用CAS自旋方式实现线程安全
//	当资源竞争较强烈时，效率底下
//	LongAddr划分成多个单元格，降低不同线程自旋等待时间，提高效率
	private static LongAdder longAdder = new LongAdder();
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 10000; j++) {
						longAdder.increment();
					}
				}
			}).start();
		}
		Thread.sleep(2000);
		System.out.println(longAdder.sum());
	}
}
