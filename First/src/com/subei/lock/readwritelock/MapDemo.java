package com.subei.lock.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapDemo {

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	Map<String, Object> map = new HashMap<String, Object>();
	
	public Object get(String key) {
		lock.readLock().lock();
		Object obj;
		try {
			obj = map.get(key);
		} finally {
			lock.readLock().unlock();
		}
		return obj;
	}
	
	public void put(String key, Object obj) {
		lock.writeLock().lock();
		try {
			map.put(key, obj);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
