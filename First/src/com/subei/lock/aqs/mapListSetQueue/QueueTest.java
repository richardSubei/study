package com.subei.lock.aqs.mapListSetQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// add		如果队列满，抛出异常
// remove	队列为空，抛出异常
// element	返回头部元素，如果为空，抛出异常
// offer	如果队列满，则返回false
// poll		如果队列为空，返回null
// peek		如果队列为空，返回null
// put	阻塞，如果队列满了，则阻塞等待
// take	阻塞，如果队列为空，则阻塞等待


// ArrayBlockingQueue LinkedBlockingQueue 
// PriorityQueue	PriorityBlockingQueue
// DelayedQueue	
// ConcurrentLinkedQueue

public class QueueTest {

	public static void main(String[] args) throws InterruptedException {
		final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
//		final LinkedBlockingQueue<String> lQueue = new LinkedBlockingQueue<String>();
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
 