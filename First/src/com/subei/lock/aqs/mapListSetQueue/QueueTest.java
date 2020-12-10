package com.subei.lock.aqs.mapListSetQueue;

import java.util.concurrent.ArrayBlockingQueue;


public class QueueTest {

	public static void main(String[] args) throws InterruptedException {
		final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
		
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println("取到数据：" + queue.poll());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		Thread.sleep(2000);
		
		for (int i = 0; i < 6; i++) {
			final int ii = i;
			new Thread(new Runnable() {
				public void run() {
					queue.offer(ii + "");
					System.out.println("塞入数据完成：" + ii);
				}
			}).start();
		}
		
	}
}
 