package com.subei.lock.aqs.mapListSetQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {

	public static void main(String[] args) {
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(11, new Comparator<Integer>() {

			@Override
			public int compare(Integer i1, Integer i2) {
				return -1;
			}
		});
		
		queue.offer(1);
		queue.offer(2);
		
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}
}
