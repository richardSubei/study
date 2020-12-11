package com.subei.lock.aqs.mapListSetQueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// 带优先级的队列，可以设置对比方式
// PriorityBlockingQueue 带优先级的阻塞队列，take时，如果队列为空，则阻塞
public class PriorityQueueTest {

	public static void main(String[] args) {
		Queue<String> queue = new PriorityQueue<String>();
		
		queue.offer("c");
		queue.offer("b");
		queue.offer("a");
		
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		
//		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(11, new Comparator<Integer>() {
//
//			@Override
//			public int compare(Integer i1, Integer i2) {
//				return -1;
//			}
//		});
//		
//		queue.offer(1);
//		queue.offer(2);
//		
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());
	}
}
