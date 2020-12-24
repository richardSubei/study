package com.subei.gc;

public class FullGCDemo2 {

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			
			
			
//			第三方依赖包的方法可能调用了System.gc
//			虚拟机启动可以设置参数显示禁用System.gc -XX:DisableExplicitGC
		}
	}
}
