package com.subei.lock.aqs.forkjoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


public class FutureTaskTest {

	public static void main(String[] args) {
		FutureTaskTest test = new FutureTaskTest();
		test.useFutureTask();
//		test.useFutureAndPool();
	}
	
	public void useFutureTask() {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "组合收益率查询成功，------这就是返回的数据";
			}
		};
		Callable<String> callable2 = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "基准收益率查询成功，------这就是返回的数据";
			}
		};

//		FutureTask<String> futureTask = new FutureTask<>(callable);
//		FutureTask<String> futureTask2 = new FutureTask<>(callable2);
		SubeiFutureTask<String> futureTask = new SubeiFutureTask<>(callable);
		SubeiFutureTask<String> futureTask2 = new SubeiFutureTask<>(callable2);
		new Thread(futureTask).start();
		new Thread(futureTask2).start();
		
		try {
			String str = futureTask.get();
			String str2 = futureTask2.get();
			System.out.println(str);
			System.out.println(str2);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	public void useFutureAndPool() {
		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "组合收益率查询成功，------这就是返回的数据";
			}
		};
		Callable<String> callable2 = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "基准收益率查询成功，------这就是返回的数据";
			}
		};
		
		Future<String> future = executor.submit(callable);
		Future<String> future2 = executor.submit(callable2);
		
		try {
			String str = future.get();
			String str2 = future2.get();
			System.out.println(str);
			System.out.println(str2);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
