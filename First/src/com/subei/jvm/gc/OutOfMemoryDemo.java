package com.subei.jvm.gc;

import java.util.ArrayList;
import java.util.List;

// 内存溢出
// 网易云课堂真实问题
// 查询订单时，未做分页数据校验，导致查询11w订单，list中保存11w条数据，最终导致内存溢出 （由此扩展到绩效系统的oom，假设。。。）
// 快速解决办法 1.留有足够的证据，内存映像		2.重启系统
// 查找原因办法：jvm参数中设置HeapDumpOnOutOfMemoryError,利用mat工具分析堆栈信息
public class OutOfMemoryDemo {

	static List<Object> list = new ArrayList<Object>();
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			list.add(new byte[1024 * 1024 * 64]);
			Thread.sleep(50000);
		}
	}
	
}
