package com.subei.jvmoptimize;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
