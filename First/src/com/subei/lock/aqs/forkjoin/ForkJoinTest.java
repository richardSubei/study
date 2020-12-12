package com.subei.lock.aqs.forkjoin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

// ForkJoin 分而治之	将大任务拆分成小任务， 有点像递归
// 工作窃取
// 每个worker线程都维护一个队列，即ForkJoinWorkerThread中的队列
// 任务队列是双向队列，支持LIFO、FIFO
// 子任务会被加入到原先任务所在的Worker线程的任务队列
// Worker线程用LIFO的方式取出任务，后进入队列的任务先取出来
// 当任务队列为空时，会随机从其他worker线程的队列中拿走一个任务执行（工作窃取：steal work）

// 适用于非阻塞的操作，数据库、网络等都不太适合
// 适合数据处理、结果汇总、统计等场景
// java8实例 Arrays.parallelSort
public class ForkJoinTest {

	private DaoImpl daoImpl = new DaoImpl();	
	private List<String> sqls = new ArrayList<String>();

	public ForkJoinTest() {
		sqls.add("select * from portfolioz");
		sqls.add("select * from benchmark");
		sqls.add("select * from riskbench");
	}
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinTest forkJoinTest = new ForkJoinTest();
//		forkJoinTest.oneThread();
		forkJoinTest.forkJoin();
	}
	
	public void forkJoin() throws InterruptedException, ExecutionException {
		System.out.println("任务开始：" + new Date(System.currentTimeMillis()).getTime());
		int start = 0;
		int end = sqls.size() - 1;
		SubeiTask subeiTask = new SubeiTask(daoImpl, sqls, start, end);
		ForkJoinPool forkJoinPool = new ForkJoinPool(10);	//本质是一个线程池，个数默认为cpu核数
		ForkJoinTask<List<String>> forkJoinTask = forkJoinPool.submit(subeiTask);
		List<String> result = forkJoinTask.get();

		for (String string : result) {
			System.out.println(string);
		}
		System.out.println("任务结束：" + new Date(System.currentTimeMillis()).getTime());
	}

	public void oneThread() {
		System.out.println("任务开始：" + new Date(System.currentTimeMillis()).getTime());
		
		String sqlPortfolio = "select * from portfolio";
		String sqlBenchMark = "select * from benchmark";
		String sqlRiskBench = "select * from riskbench";
		List<String> listPortfolio= daoImpl.get(sqlPortfolio);
		List<String> listBenchmark= daoImpl.get(sqlBenchMark);
		List<String> listRiskBench= daoImpl.get(sqlRiskBench);
		
		List<String> result = this.gatherList(listPortfolio, listBenchmark, listRiskBench);
		
		for (String string : result) {
			System.out.println(string);
		}
		System.out.println("任务结束：" + new Date(System.currentTimeMillis()).getTime());
	}
	
//	public void multiThread() {
//		System.out.println("任务开始：" + new Date(System.currentTimeMillis()).getTime());
//		
//		ExecutorService executor = Executors.newFixedThreadPool(3);
//		
//		final String sqlPortfolio = "select * from portfolio";
//		final String sqlBenchMark = "select * from benchmark";
//		final String sqlRiskBench = "select * from riskbench";
//		final List<String> listPortfolio = null;
//		final List<String> listBenchmark = null;
//		final List<String> listRiskBench = null;
//		executor.submit(new Runnable() {
//			public void run() {
//				listPortfolio = daoImpl.get(sqlPortfolio);
//			}
//		});
//		executor.submit(new Runnable() {
//			public void run() {
//				listPortfolio = daoImpl.get(sqlBenchMark);
//			}
//		});
//		executor.submit(new Runnable() {
//			public void run() {
//				listPortfolio = daoImpl.get(sqlRiskBench);
//			}
//		});
//		
//		List<String> result = this.gatherList(listPortfolio, listBenchmark, listRiskBench);
//		
//		for (String string : result) {
//			System.out.println(string);
//		}
//		System.out.println("任务结束：" + new Date(System.currentTimeMillis()).getTime());
//	}
	
	
	public List<String> gatherList(List<String> list1, List<String> list2, List<String> list3) {
		list1.addAll(list2);
		list1.addAll(list3);
		return list1;
	}
	
}

// 实现ForkJoinTask接口 
class SubeiTask extends RecursiveTask<List<String>> {
	List<String> sqls;
	DaoImpl daoImpl;
	int start;
	int end;
	
	public SubeiTask(DaoImpl daoImpl, List<String> sqls, int start, int end) {
		this.daoImpl = daoImpl;
		this.sqls = sqls;
		this.start = start;
		this.end = end;
	}
	
//	定义拆分逻辑
	@Override
	protected List<String> compute() {
		int count = end - start;
		if (count == 0) {							// 只有一个sql时去查询数据库
			String sql = sqls.get(0);
			List<String> list = daoImpl.get(sql);
			return list;
		} else {										// 继续拆分
			int mid = (start + end) / 2;	// 一分为2
			SubeiTask task = new SubeiTask(daoImpl, sqls, start, mid);	
			SubeiTask task2 = new SubeiTask(daoImpl, sqls, mid + 1, end);
			task.fork();
			task2.fork();
			
			List<String> list1 = task.join();
			List<String> list2 = task2.join();
			List<String> result = new ArrayList<String>();
			result.addAll(list1);
			result.addAll(list2);
			return result;
		}
	}
}

