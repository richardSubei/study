package com.subei.jvmoptimize;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//jvm GC优化演示
//每100毫秒创建150个线程，1秒钟最多存在1500个线程
//调整线程数量，调整垃圾收集器，观察
public class GCOptimizeTest {

	public static void main(String[] args) {
		Executors.newScheduledThreadPool(10).scheduleAtFixedRate(new Runnable() {
			public void run() {
				for (int i = 0; i < 150; i++) {
					byte[] bytes = new byte[512 * 1024];
					try {
						Thread.sleep(new Random().nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, 100, 100, TimeUnit.MILLISECONDS);
	}
}
