package com.subei.queue;
//循环队列
public class SubeiCycleQueue {

	private int items[];
	private int size;
	private int head;
	private int tail;
	
	public SubeiCycleQueue(int n) {
		items = new int[n];
		size = n;
		head = 0;
		tail = 0;
	}
//	入队
	public boolean enqueue(int value) {
		if ((tail + 1) % size == head) {		//队列满的条件
			return false;
		}
		items[tail] = value;
		tail = (tail + 1) % size;
		return true;
	}
//	出队
	public int dequeue() {
		if (tail == head) {
			return -1;
		}
		int value = items[tail];
		head = (head + 1) % size;
		return value;
	}
	
}
