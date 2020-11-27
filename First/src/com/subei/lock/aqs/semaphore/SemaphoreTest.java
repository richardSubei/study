package com.subei.lock.aqs.semaphore;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//信号量 控制线程并发数量
public class SemaphoreTest {

	
	public static void main(String[] args) throws InterruptedException {
		final Semaphore semaphore = new Semaphore(5);
		final SemaphoreTest test = new SemaphoreTest();
		Executor executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 9; i++) {
			final String vipNo = "vip00" + i;
			executor.execute(new Runnable() {
				public void run() {
					try {
						semaphore.acquire();
						test.service(vipNo);
						Thread.sleep(10000);
						semaphore.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public void service(String vipNo) throws InterruptedException {
		System.out.println("楼上男宾一位，贵宾编号 " + vipNo);
		Thread.sleep(new Random().nextInt(3000));
		System.out.println("欢迎贵宾下次光临，贵宾编号 " + vipNo);
	}
}
