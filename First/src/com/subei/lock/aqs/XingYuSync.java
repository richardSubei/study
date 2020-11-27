package com.subei.lock.aqs;

public class XingYuSync extends XingYuAqs{

	@Override
	protected boolean tryAcquire() {
		return owner.compareAndSet(null, Thread.currentThread());
	}
	
	@Override
	protected boolean tryRelease() {
		return owner.compareAndSet(Thread.currentThread(), null);
	}
	
}
