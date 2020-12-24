package com.subei.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * 三种方法对比
 * @author ysstech
 *
 */
public class ThreadCommunication {

	private Object baozi = null;
	
	public static void main(String[] args) throws InterruptedException {
//		new ThreadCommunication().testSuspend();
		new ThreadCommunication().testWait();
//		new ThreadCommunication().testPark();
	}
	
	//suspend不释放锁，对执行顺序也有要求
	public void testSuspend() throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized (ThreadCommunication.class) {
					if (baozi == null) {
						System.out.println("没有包子，线程挂起");
						Thread.currentThread().suspend();
					}
					System.out.println("吃包子");
				}
			}
		});
		t.start();
		Thread.sleep(2000);
		synchronized (ThreadCommunication.class) {
			baozi = new Object();
			t.resume();
		}
		System.out.println("通知消费者");
	}
	
	//wait notify 释放锁，但是对执行顺序有要求，若先执行notify，则执行wait后，无法将线程唤醒
	public void testWait() {
		final Object lock = new Object();
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized (lock) {
					if (baozi == null) {
						System.out.println("没有包子，线程挂起");
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("吃包子");
				}
			}
		});
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		baozi = new Object();
		synchronized (lock) {
			lock.notify();
		}
		System.out.println("通知消费者");
	}
	
	//对执行顺序没有要求 但是挂起不释放锁
	public void testPark() {
		final Object lock = new Object();
		Thread t = new Thread(new Runnable() {
			public void run() {
				if (baozi == null) {
					System.out.println("没有包子，线程挂起");
					LockSupport.park();
				}
				System.out.println("吃包子");
			}
		});
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		baozi = new Object();
		LockSupport.unpark(t);
		System.out.println("通知消费者");
	}
	
}
