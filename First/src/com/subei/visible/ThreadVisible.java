package com.subei.visible;

public class ThreadVisible {

	private boolean flag = true;

//	-server 以server模式运行 JIT编译时，下方while循环发生指令重排序
	public static void main(String[] args) throws InterruptedException {
		final ThreadVisible tVisible = new ThreadVisible();
		new Thread(new Runnable() {
			int i = 0;
			public void run() {
//				if (tVisible.flag) {	此为发生指令重排序后的代码
//					while (true) {
//						i++;
//					}
//				}
				while (tVisible.flag) {
					i++;
				}
				System.out.println(i);
			}
		}).start();
		Thread.sleep(2000);
		tVisible.flag = false;
		System.out.println("flag 被置为false");
		
	}
	
}
