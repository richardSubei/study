package com.subei.lock.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ReadWriteLockTest1 {
//	ReentrantReadWriteLock 可以多线程读
//	某线程获取读锁之后，其他线程还可以获取读锁，但是不可获取写锁，因为读的时候如果还在写，那数据应该是什么样子的呢
//	同理获取写锁之后，也不应该获取读锁，总之，读写锁，不可同时读和写，但是可以多线程同时读
//	
	final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		final ReadWriteLockTest1 test1 = new ReadWriteLockTest1();
		
		new Thread(new Runnable() {
			public void run() {
				test1.read(Thread.currentThread());
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				test1.read(Thread.currentThread());
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				test1.write(Thread.currentThread());
			}
		}).start();
	}
	
//	多线程读 共享锁
	public void read(Thread thread) {
		lock.readLock().lock();
		long start = System.currentTimeMillis();
		try {
			while (System.currentTimeMillis() - start <= 1) {
				System.out.println(thread.getName() + " 正在进行读操作");
			}
			System.out.println(thread.getName() + " 读操作结束");
		} finally {
			lock.readLock().unlock();
		}
	}
//	写
	public void write(Thread thread) {
		lock.writeLock().lock();
		long start = System.currentTimeMillis();
		try {
			while (System.currentTimeMillis() - start <= 1) {
				System.out.println(thread.getName() + " 正在进行写操作");
			}
			System.out.println(thread.getName() + " 写操作结束");
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	
}
