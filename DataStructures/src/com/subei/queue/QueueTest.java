package com.subei.queue;

import com.subei.linkedlist.Node;

public class QueueTest {

	public static void main(String[] args) {
		SubeiCycleQueue queue = new SubeiCycleQueue(5);
		
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);
		
	}
}
