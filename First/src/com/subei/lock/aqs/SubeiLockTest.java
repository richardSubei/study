package com.subei.lock.aqs;

//使用自己写的锁测试线程安全
public class SubeiLockTest {

	static int value = 0;
	static SubeiLock lock = new SubeiLock();
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 10000; j++) {
						lock.lock();
						try {
							value++;
						} finally {
							lock.unlock();
						}
						
					}
				}
			}).start();
		}
		Thread.sleep(3000);
		System.out.println(value);
	}
}
