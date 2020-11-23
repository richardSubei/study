package com.subei.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {

//	由于value++为非原子操作，所以value最终值不等于20000
//	private static int value = 0;
	
//	使用cas保证原子操作，compare and swap，比较并交换，将旧值和内存中的值比较，
//	若一样，则将旧值替换为新值，若失败，则自旋一直比较旧值和内存中的值，直到成功为止
//	底层使用Unsafe实现
	private static AtomicInteger value = new AtomicInteger(0);
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j =0; j < 10000; j++) {
//						value++;
 						value.incrementAndGet();
					}
				}
			}).start();
		}
		Thread.sleep(2000);
		System.out.println(value.get());
	}
}
