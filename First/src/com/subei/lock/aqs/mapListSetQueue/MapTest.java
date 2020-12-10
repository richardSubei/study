package com.subei.lock.aqs.mapListSetQueue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

// HashMap 数组和链表实现，大小默认16，加载因子0.75，当大小超过16*0.75是将进行resize操作，
// 把数组长度扩展为原来的两倍，元素根据hash算法重新定位到新的位置，非常耗时，影响性能，最好创建map时根据估算大小提供初始值
// 在jdk1.8中，若链表长度超过8，map中元素超过64（默认情况），链表将转换为红黑树，此种情况概率不高
// HashMap使用fast-fail机制，在迭代时，如果有线程修改了map数据，则有可能产生fast-fail事件，抛出ConcurrentModifyException
// 迭代器的fast-fail机制在并发修改时，并不是一定会触发

// ConcurrentHashMap juc包下线程安全map，使用semgent数组实现，semegent继承于ReentrantLock，put时加锁，
// semegent中包含Entry[]数组，可以看成每个semegent中又有一个map
// jdk1.8中取消了semegent，全部改成cas和synchronized实现，当数组为空时，使用cas赋值，数组不为空时，
// synchronied锁住链表头，然后再进行put操作


// skipListMap 跳表，链表组成，但是会对结点随机生成多级索引，每个索引都有right、down指针，指向右侧、下级索引
// Redis中应用

public class MapTest {

	public static void main(String[] args) {
//		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(16, 0.75f, true);
		Map<String, String> map = new HashMap<String, String>();
		map.put("kobe", "kobe");
		map.put("james", "james");
		map.put("kurry", "kurry");
		map.get("kurry");
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) iterator
					.next();
			map.remove(entry);
			System.out.println(entry.getKey() + "-" + entry.getValue());
			
		}
		
//		Map<User, String> map2 = new HashMap<User, String>();
//		User user1 = new User("kobe", 30);
//		map.put(user1, "kobe");
		
		
		
	}
}

class User {
	private String name;
	private int age;
	
	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

