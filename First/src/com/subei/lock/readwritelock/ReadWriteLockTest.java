package com.subei.lock.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

//结论
//读锁为共享锁，多个线程可以获取同一把锁
//写锁为互斥锁，只能同时有一个线程获取该锁
//读 写 之间也互斥，获取读锁之后，其他线程不可获取写锁，反之同理，保证数据不会出错
public class ReadWriteLockTest {

	public static void main(String[] args) throws InterruptedException {
		final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		
//		获取写锁之后，其他线程不可获取读锁
//		lock.writeLock().lock();
//		System.out.println("主线程获取写锁");
//		new Thread(new Runnable() {
//			public void run() {
//				lock.readLock().lock();
//				System.out.println("子线程获取读锁");
//			}
//		}).start();
//		Thread.sleep(3000);
//		System.out.println("主线程等待3秒钟");
//		lock.writeLock().unlock();
//		System.out.println("主线程释放写锁");

		
//		获取读锁之后，不可以再获取写锁		即使是同一个线程也不可以（已经经过测试）
//		lock.readLock().lock();
//		System.out.println("主线程获取读锁");
//		new Thread(new Runnable() {
//			public void run() {
//				lock.writeLock().lock();
//				System.out.println("子线程获取写锁");
//			}
//		}).start();
//		Thread.sleep(3000);
//		System.out.println("主线程等待3秒钟");
//		lock.readLock().unlock();
//		System.out.println("主线程释放读锁");
		
		
//		读锁为共享锁
//		lock.readLock().lock();
//		System.out.println("第一次获取读锁");
//		new Thread(new Runnable() {
//			public void run() {
//				lock.readLock().lock();
//				System.out.println("第二次获取读锁");
//			}
//		}).start();
		
//		
		lock.writeLock().lock();
		System.out.println("第一次获取写锁");
		new Thread(new Runnable() {
			public void run() {
				lock.writeLock().lock();
				System.out.println("第二次获取写锁");
			}
		}).start();
		System.out.println(lock.writeLock().getHoldCount());
	}
}
