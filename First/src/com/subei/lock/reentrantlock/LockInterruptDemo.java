package com.subei.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

//线程中断
public class LockInterruptDemo {

	public ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		final LockInterruptDemo lockInterruptTest = new LockInterruptDemo();
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					lockInterruptTest.test(Thread.currentThread());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		
		t1.start();
		Thread.sleep(500);
		
		t2.start();
		Thread.sleep(2000);
		t2.interrupt();
		
	}
	
	public void test(Thread thread) throws InterruptedException {
        System.out.println(thread.getName() + "， 想获取锁");
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出（必须放在外面，将异常抛出，否则无法中断）
        try {
            System.out.println(thread.getName() + "得到了锁");
            Thread.sleep(10000); // 抢到锁，10秒不释放
        } finally {
            System.out.println(thread.getName() + "执行finally");
            lock.unlock();
            System.out.println(thread.getName() + "释放了锁");
        }
	}
	
}
