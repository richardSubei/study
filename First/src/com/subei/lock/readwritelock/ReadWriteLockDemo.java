package com.subei.lock.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReadWriteLockDemo {

	static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		final ReadLock readLock = lock.readLock();
		final WriteLock writeLock = lock.writeLock();
		
//		new Thread(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread() + " 期望获得写锁");
//				writeLock.lock();
//				System.out.println(Thread.currentThread() + " 获得了写锁");
//				try {
//					Thread.sleep(10000);
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					writeLock.unlock();
//					System.out.println(Thread.currentThread() + " 释放写锁");
//				}
//			}
//		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread() + " 期望获得读锁");
				readLock.lock();
				System.out.println(Thread.currentThread() + " 获得了读锁");
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					readLock.unlock();
					System.out.println(Thread.currentThread() + " 释放读锁");
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread() + " 期望获得读锁");
				readLock.lock();
				System.out.println(Thread.currentThread() + " 获得了读锁");
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					readLock.unlock();
					System.out.println(Thread.currentThread() + " 释放读锁");
				}
			}
		}).start();
		
	}
}
