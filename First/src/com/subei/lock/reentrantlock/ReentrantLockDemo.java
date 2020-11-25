package com.subei.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

//演示可重入
//执行了几次lock，对应就要执行几次unlock
public class ReentrantLockDemo {

	static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		lock.lock();
		try {
			System.out.println("第一次获取锁");
			System.out.println("当前线程获取锁的次数：" + lock.getHoldCount());
			lock.lock();
			System.out.println("第二次获取锁");
			System.out.println("当前线程获取锁的次数：" + lock.getHoldCount());
		} finally {
			lock.unlock();
//			lock.unlock();
		}
		
		System.out.println("当前线程获取锁的次数：" + lock.getHoldCount());
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread() + " 期望拿到锁");
				lock.lock();
				System.out.println(Thread.currentThread() + " 拿到了锁");
				lock.unlock();
				System.out.println(Thread.currentThread() + " 释放锁");
			}
		}).start();
		
		Thread.sleep(3000);
		lock.unlock();
	}
}
