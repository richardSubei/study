package com.subei.lock.aqs.forkjoin;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

// ForkJoin 分而治之	将大任务拆分成小任务， 有点像递归
public class ForkJoinTest {

	private DaoImpl daoImpl = new DaoImpl();	

	
	public static void main(String[] args) {
		ForkJoinTest forkJoinTest = new ForkJoinTest();
//		forkJoinTest.oneThread();
		forkJoinTest.multiThread();
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
	
	public void multiThread() {
		System.out.println("任务开始：" + new Date(System.currentTimeMillis()).getTime());
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		final String sqlPortfolio = "select * from portfolio";
		final String sqlBenchMark = "select * from benchmark";
		final String sqlRiskBench = "select * from riskbench";
		final List<String> listPortfolio = null;
		final List<String> listBenchmark = null;
		final List<String> listRiskBench = null;
		executor.submit(new Runnable() {
			public void run() {
				listPortfolio = daoImpl.get(sqlPortfolio);
			}
		});
		executor.submit(new Runnable() {
			public void run() {
				listPortfolio = daoImpl.get(sqlBenchMark);
			}
		});
		executor.submit(new Runnable() {
			public void run() {
				listPortfolio = daoImpl.get(sqlRiskBench);
			}
		});
		
		List<String> result = this.gatherList(listPortfolio, listBenchmark, listRiskBench);
		
		for (String string : result) {
			System.out.println(string);
		}
		System.out.println("任务结束：" + new Date(System.currentTimeMillis()).getTime());
	}
	
	public void forkJoin() {
		SubeiTask subeiTask = new SubeiTask(daoImpl, sqls, start, end)
		ForkJoinPool forkJoinPool = new ForkJoinPool(10);
		ForkJoinTask<List> forkJoinTask = forkJoinPool.submit(task);
		List<String> result = forkJoinTask.get();

		for (String string : result) {
			System.out.println(string);
		}
		System.out.println("任务结束：" + new Date(System.currentTimeMillis()).getTime());
	}
	
	
	
	public List<String> gatherList(List<String> list1, List<String> list2, List<String> list3) {
		list1.addAll(list2);
		list1.addAll(list3);
		return list1;
	}
	
}

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
	
	
	@Override
	protected List<String> compute() {
		int count = end - start;
		if (count == 0) {
			String sql = sqls.get(0);
			List<String> list = daoImpl.get(sql);
			return list;
		} else {
			int mid = (start + end) / 2;
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

