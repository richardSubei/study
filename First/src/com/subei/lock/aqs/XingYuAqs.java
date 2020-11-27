package com.subei.lock.aqs;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public abstract class XingYuAqs {
	
	protected AtomicReference<Thread> owner = new AtomicReference<Thread>();
	protected LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();

	protected boolean tryAcquire() {
		throw new UnsupportedOperationException();
	}
	
	protected boolean tryRelease() {
		throw new UnsupportedOperationException();
	}
	
	public void acquire() {
		boolean addQ = true;
		while (!tryAcquire()) {
			if (addQ) {
				waiters.offer(Thread.currentThread());
			} else {
				LockSupport.park();
			}
		}
		waiters.remove(Thread.currentThread());
	}
	
	public void release() {
		if (tryRelease()) {
			for (Thread thread : waiters) {
				LockSupport.unpark(thread);
			}
		}
	}
	
}
