package com.subei.thread;

public class ThreadStop {
	
	private static int i;
	private static int j;
	
	public static void main(String[] args) throws InterruptedException {
//		new ThreadStop().testStop();
		new ThreadStop().testInterrupt();
		Thread.sleep(5000);
		System.out.println(i + ";" + j);
	}
	
	//stop 线程不安全
	public void testStop () {
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized (this) {
					i++;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					j++;
				}
			}
		});
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
	}
	
	
	//stop 线程不安全
	public void testInterrupt () {
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized (this) {
					i++;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					j++;
				}
			}
		});
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.interrupt();
	}

}
