package com.subei.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class XingYuLock implements Lock {
	
	private XingYuSync sync;
	
	public XingYuLock() {
		sync = new XingYuSync();
	}
	
	@Override
	public boolean tryLock() {
		return sync.tryAcquire();
	}
	
	@Override
	public void lock() {
		sync.acquire();
	}
	
	@Override
	public void unlock() {
		sync.release();
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

