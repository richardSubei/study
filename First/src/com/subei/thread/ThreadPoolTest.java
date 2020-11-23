package com.subei.thread;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {
//		new ThreadPoolTest().threadPoolExecutorTest();
//		new ThreadPoolTest().threadPoolExecutorTest1();
//		new ThreadPoolTest().threadPoolExecutorTest2();
//		new ThreadPoolTest().threadPoolExecutorTest4();
//		new ThreadPoolTest().threadPoolExecutorTest5();
		new ThreadPoolTest().threadPoolExecutorTest6();

	}
	
	public void testComm(ThreadPoolExecutor tExecutor) throws InterruptedException {
		for (int i = 0; i < 15; i++) {
			final int n = i;
			tExecutor.submit(new Runnable() {
				public void run() {
					System.out.println("开始执行：" + n);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.err.println("结束执行：" + n);
				}
			});
			System.out.println("任务提交成功：" + n);
		}
		Thread.sleep(500L);
		System.out.println("当前线程池数量：" + tExecutor.getPoolSize());
		System.out.println("当前线程等待的数量：" + tExecutor.getQueue().size());
		Thread.sleep(15000L);
		System.out.println("当前线程池数量：" + tExecutor.getPoolSize());
		System.out.println("当前线程等待的数量：" + tExecutor.getQueue().size());
		
	}
	
	/**
	 * 线程池运行顺序
	 * 1.首先判断是否达到核心线程数，如果没达到继续生成一个线程
	 * 2.如果达到核心线程数，往队列里放入任务（若为无界队列则一直放）
	 * 3.如果队列已满，判断是否达到最大线程数，如果没达到，继续生成线程，如果达到则拒绝执行
	 * 
	 * 由于本例是无界队列，所以达到核心线程数之后，便不再生成线程
	 * @throws InterruptedException
	 */
	public void threadPoolExecutorTest() throws InterruptedException {
		ThreadPoolExecutor tExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		testComm(tExecutor);
	}
	
	/**
	 * 定义拒绝策略
	 * @throws InterruptedException
	 */
	public void threadPoolExecutorTest1() throws InterruptedException {
		ThreadPoolExecutor tExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable paramRunnable,
					ThreadPoolExecutor paramThreadPoolExecutor) {
				System.err.println("线程池已满，不允许执行新的任务");
			}
		});
		testComm(tExecutor);
	}
	
	public void threadPoolExecutorTest2() throws InterruptedException {
//		Executors.newCachedThreadPool(); 的实现
//		有空闲线程，则使用空闲线程，否则新建线程
		ThreadPoolExecutor tExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		testComm(tExecutor);
		Thread.sleep(10000);
		System.out.println("当前线程池数量：" + tExecutor.getPoolSize());
	}
	
	public void threadPoolExecutorTest3() {
		ScheduledThreadPoolExecutor tExecutor = new ScheduledThreadPoolExecutor(5);
		tExecutor.schedule(new Runnable() {
			public void run() {
				System.out.println("开始执行");
			}
		}, 3, TimeUnit.SECONDS);
		
	}
	
	/**
	 * 
	 */
	public void threadPoolExecutorTest4() {
		final ScheduledThreadPoolExecutor tExecutor = new ScheduledThreadPoolExecutor(5);
		tExecutor.scheduleAtFixedRate(new Runnable() {
			public void run() {
//				System.out.println("开始执行");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("当前线程数量为：" + tExecutor.getPoolSize() + "---" + System.currentTimeMillis());
			}
		}, 3, 1, TimeUnit.SECONDS);
	}
	
	public void threadPoolExecutorTest5() {
		final ScheduledThreadPoolExecutor tExecutor = new ScheduledThreadPoolExecutor(5);
		tExecutor.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("当前线程数量为：" + tExecutor.getPoolSize() + "---" + System.currentTimeMillis());
			}
		}, 3, 2, TimeUnit.SECONDS);
	}
	
	public void threadPoolExecutorTest6() {
		ThreadPoolExecutor tExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new RejectedExecutionHandler() {
			
			@Override
			public void rejectedExecution(Runnable paramRunnable,
					ThreadPoolExecutor paramThreadPoolExecutor) {
				System.out.println("有任务被拒绝执行了");
			}
		});
		
		for (int i = 0; i < 15; i++) {
			final int n = i;
			tExecutor.submit(new Runnable() {
				public void run() {
					System.out.println("开始执行任务：" + n);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.err.println("任务执行结束：" + n);
				}
			});
			System.out.println("任务提交成功：" + n);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		顺序关闭已提交的任务
//		tExecutor.shutdown();
//		tExecutor.submit(new Runnable() {
//			public void run() {
//				System.out.println("追加一个任务");
//			}
//		});
//		立即停止线程，并返回待执行任务列表
		List<Runnable> list = tExecutor.shutdownNow();
		tExecutor.submit(new Runnable() {
			public void run() {
				System.out.println("追加一个任务");
			}
		});
		System.out.println(list.size());
	}
	
}
