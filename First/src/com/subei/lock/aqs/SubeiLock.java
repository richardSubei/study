package com.subei.lock.aqs;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class SubeiLock implements Lock {

	//当前锁的拥有者
	private AtomicReference<Thread> owner = new AtomicReference<Thread>();
	//等待队列
	private LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();
	
	@Override
	public boolean tryLock() {
		return owner.compareAndSet(null, Thread.currentThread());
	}
	
	@Override
	public void lock() {
		boolean addQ = true;
		while (!tryLock()) {
			if (addQ) {
				waiters.offer(Thread.currentThread());
				addQ = false;		//防止重复加入
			} else {
				LockSupport.park();	//使用while循环防止伪唤醒
			}
		}
		waiters.remove(Thread.currentThread());
	}

	@Override
	public void unlock() {
		if (owner.compareAndSet(Thread.currentThread(), null)) {
			Iterator<Thread> iterator = waiters.iterator();
			while (iterator.hasNext()) {
				Thread waiter = iterator.next();
				LockSupport.unpark(waiter);
			}
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	
}
