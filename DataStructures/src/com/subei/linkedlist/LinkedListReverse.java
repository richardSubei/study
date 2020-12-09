package com.subei.linkedlist;

//单链表反转	LeetCode 206题
public class LinkedListReverse {

	public static void main(String[] args) {
		Node head = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		
		head.setNext(node2);
		node2.setNext(node3);
		node3.setNext(node4);
		node4.setNext(null);
		
		Node h = head;
		while (null != h) {
			System.out.println(h.getData());
			h = h.getNext();
		}
		
//		h = reverseLeetCode(head);
		h = reverseReview(head);
		System.out.println("反转后");
		while (null != h) {
			System.out.println(h.getData());
			h = h.getNext();
		}
	}
	
	public static Node reverse(Node head) {
		if (head == null) {
			return head;
		}
		Node pre = head;
		Node cur = head.getNext();
		Node temp;
		while (null != cur) {
			temp = cur.getNext();
			cur.setNext(pre);
			pre = cur;
			cur = temp;
		}
		head.setNext(null);
		return pre;
	}
	
//	时间复杂度O(n) 空间复杂度O(1)
	public static Node reverseLeetCode(Node head) {
		if (head == null || head.getNext() == null) {
			return head;
		}
		Node pre = null;
		Node cur = head;
		while (cur != null) {
			Node temp = cur.getNext();
			cur.setNext(pre);
			pre = cur;
			cur = temp;
		}
		return pre;
	}
	
	
	
	
	
//	复习链表反转
	public static Node reverseReview(Node head) {
		if (head == null || head.getNext() == null) {
			return head;
		}
		Node pre = null;
		Node temp;
		while (head != null) {
			temp = head.getNext();
			head.setNext(pre);
			pre = head;
			head = temp;
		}
		return pre;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
