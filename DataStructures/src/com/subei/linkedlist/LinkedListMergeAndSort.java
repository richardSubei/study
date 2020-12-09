package com.subei.linkedlist;

//合并两个有序链表并排序	LeetCode 21题
public class LinkedListMergeAndSort {

	public static void main(String[] args) {
		Node h1 = new Node(1);
		Node n2 = new Node(4);
		Node n3 = new Node(5);
		
		h1.setNext(n2);
		n2.setNext(n3);
		
		Node h2 = new Node(1);
		Node m2 = new Node(2);
		Node m3 = new Node(3);
		Node m4 = new Node(6);
		
		h2.setNext(m2);
		m2.setNext(m3);
		m3.setNext(m4);
		
		Node pre = review(h1, h2);
		
		while (pre != null) {
			System.out.println(pre.getData());
			pre = pre.getNext();
		}
		
		
	}
	
//	比较两个链表头，谁小指向谁
	public static Node mergeAndSort(Node l1, Node l2) {
		Node preHead = new Node(-1);	//哨兵
		Node pre = preHead;
		while (l1 != null && l2 != null) {
			if (l1.getData() <= l2.getData()) {
				pre.setNext(l1);
				l1 = l1.getNext();
			} else {
				pre.setNext(l2);
				l2 = l2.getNext();
			}
			pre = pre.getNext();
		}
//		有序链表，可直接拼接剩余的
		if (l1 == null) {
			pre.setNext(l2);
		} else {
			pre.setNext(l1);
		}
		
		return preHead.getNext();
	}
	
	
	public static Node mergeAndSort1(Node l1, Node l2) {
		Node preHead = new Node(-1);
		Node pre = preHead;
		while (l1 != null && l2 != null) {
			if (l1.getData() <= l2.getData()) {
				pre.setNext(l1);
				l1 = l1.getNext();
			} else {
				pre.setNext(l2);
				l2 = l2.getNext();
			}
			pre = pre.getNext();
		}
		if (l1 == null) {
			pre.setNext(l2);
		} else {
			pre.setNext(l1);
		}
		
		return preHead.getNext();
	}
	
	public static Node review(Node l1, Node l2) {
		Node preHead = new Node(-1);
		Node pre = preHead;
		while (l1 != null && l2 != null) {
			if (l1.getData() <= l2.getData()) {
				pre.setNext(l1);
				l1 = l1.getNext();
			} else {
				pre.setNext(l2);
				l2 = l2.getNext();
			}
			pre = pre.getNext();
		}
		if (l1 != null) {
			pre.setNext(l1);
		} else if (l2 != null) {
			pre.setNext(l2);
		}
		return preHead.getNext();
	}

	
	
	
	
	
	
}
