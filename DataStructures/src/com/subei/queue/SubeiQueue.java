package com.subei.queue;

// 队列
// 循环队列、阻塞队列
// 线程池中的队列，数据库连接池队列，消息队列等等

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
//		这个地方是说，队尾没空间了，由于有过出队操作，队头不在0处，此时若要入队，明明还有空间，但是却不能入队
		if (tail == size) {		//假如队尾满了
			if (head == 0) {	//此时head在0，说明队列满了
				return false;
			}
			for (int i = head; i < tail; i++) {	//数据搬移
				items[i-head] = items[i];
			}
		}
		tail = tail - head;		//更新队尾和队头
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
