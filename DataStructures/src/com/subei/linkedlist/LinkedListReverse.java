package com.subei.linkedlist;

public class LinkedListReverse {

	public static void main(String[] args) {
		Node head = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		
		head.setNext(node2);
		node2.setNext(node3);
		node3.setNext(node4);
		
		Node h = head;
		while (null != h) {
			System.out.println(h.getData());
			h = h.getNext();
		}
		
		h = reverse(head);
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
	
}
