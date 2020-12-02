package com.subei.stack;


public class SubeiQueue {

	private int[] items;
	private int size;
	private int head;
	private int tail;
	
	public SubeiQueue(int n) {
		items = new int[n];
		size = n;
		head = 0;
		tail = 0;
	}
	
	public boolean enqueue(int value) {
		if (tail == size) {
			return false;
		}
		items[tail] = value;
		tail++;
		return true;
	}
	
//	均摊时间复杂度O(n)
	public boolean enqueue1(int value) {
		if (tail == size) {		//假如队尾满了
			if (head == 0) {	//此时head在0，说明队列满了
				return false;
			}
			for (int i = head; i < tail; i++) {	//数据搬移
				items[i-head] = items[i];
			}
		}
		tail = tail - head;
		head = 0;
		items[tail] = value;
		tail++;
		return true;
	}
	
	public int dequeue() {
		if (head == tail) {
			return -1;
		}
		int value = items[head];
		head++;
		return value;
	}
	
}
