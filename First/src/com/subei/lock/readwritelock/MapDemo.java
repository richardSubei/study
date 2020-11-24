package com.subei.lock.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapDemo {

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	Map<String, Object> map = new HashMap<String, Object>();
	
	

}
