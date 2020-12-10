package com.subei.lock.aqs.mapListSetQueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// ArrayList 动态数组，快速失败，在迭代时不可并发修改
// LinkedList 双向链表
// CopyOnWriteArrayList 读时可写，数组复制，需要更多内存，可能读到不正确的数据（脏读）
public class ListTest {

	public static void main(String[] args) throws InterruptedException {
		final List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		
//		for (String string : list) {
//			list.remove(string);		// Fast-fail 快速失败
//		}
		
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			list.remove(string);			// Fast-fail 快速失败
		}
	}
	
}
