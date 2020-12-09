package com.subei.lock.aqs.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

//凑齐几个线程一起执行
public class CyclicBarrierTest {

	 public static void main(String[] args) throws InterruptedException {
		 final LinkedBlockingQueue<Thread> queue = new LinkedBlockingQueue<Thread>();
		 
		 final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
			public void run() {
				System.out.println("凑齐5个线程一起执行");
				for (int i = 0; i < 5; i++) {
					System.out.println(queue.poll().getName());
				}
			}
		 });

		 for (int i = 0; i < 10; i++) {
			 new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(3000);
						queue.offer(Thread.currentThread());
						System.out.println();
						cyclicBarrier.await();				//阻塞等待凑齐线程个数
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "执行完毕");
				}
			}).start();
		 }
		
		 
		 
		 
	 }
	
}
