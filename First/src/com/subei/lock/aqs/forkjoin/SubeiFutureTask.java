package com.subei.lock.aqs.forkjoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

public class SubeiFutureTask<T> implements Runnable, Future<T> {

	Callable<T> callable;	
	T result;	//结果集
	LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();	//若run方法未执行完毕，调用get方法的线程加入等待队列
	String state = "NEW";	//状态位
	
	public SubeiFutureTask(Callable<T> callable) {
		this.callable = callable;
	}
	
	@Override
	public void run() {
		try {
			result = callable.call();	//callable接口的call方法其实是在runnable接口中的run方法中调用
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			state = "COMPLETE";
		}
		Thread waiter;
		while (waiters != null) {
			waiter = waiters.poll();
			LockSupport.unpark(waiter);	//唤醒调用get方法的线程
		}
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		if ("COMPLETE".equals(state)) {
			return result;
		}
		waiters.offer(Thread.currentThread());
		while (!"COMPLETE".equals(state)) {
			LockSupport.park();		//如果run方法还没执行完毕，阻塞
		}
		return result;
	}
	
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}
