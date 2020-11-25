package com.subei.lock.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class CacheDataDemo {

	private Map<String, Object> map = new HashMap<String, Object>();
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	
	public static void main(String[] args) {
		
	}
	
	public Object get(String id) {
		Object value = null;
		//先从缓存中读取
		lock.readLock().lock();
		try {
			if (map.get(id) == null) {
				//缓存中不存在，则从数据库中读取
				lock.readLock().unlock();
				lock.writeLock().lock();
				try {
					if (map.get(id) == null) {	//双重检查
						//从数据库中读取
						value = new Object();
					}
					lock.readLock().lock();	//加读锁，降级写锁，这样就不会有其他线程可以修改值，保证数据一致性
				} finally {
					lock.writeLock().unlock();
				}
			} else {
				value = map.get(id);
			}
		} finally {
			lock.readLock().unlock();
		}
		return value;
	}
	
}
