package com.subei.lock.aqs.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

	public static void main(String[] args) {
		final CountDownLatch countDownLatch = new CountDownLatch(10);
		Executor executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			final int No = i;
			executor.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(2000);
						System.out.println("我是" + Thread.currentThread() + ".我执行接口-" + No +"调用了");
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	                countDownLatch.countDown();
				}
			});
		}
		
		try {
			countDownLatch.await();			//阻塞等待
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行完毕");
		
		
	}
}
