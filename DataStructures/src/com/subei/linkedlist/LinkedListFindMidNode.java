package com.subei.linkedlist;

//	删除链表的中间结点
public class LinkedListFindMidNode {

	public static void main(String[] args) {
		Node head1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		

		head1.setNext(node2);
		node2.setNext(node3);
		node3.setNext(node4);
		node4.setNext(node5);
		node5.setNext(node6);
		
//		Node l1 = findMidNode(head1);
		Node l1 = findMidNodeUseFastAndSlow(head1);
		while (l1 != null) {
			System.out.println(l1.getData());
			l1 = l1.getNext();
		}
	}
	
	public static Node findMidNode(Node l1) {
		if (l1 == null) {
			return null;
		}
		Node temp = l1;
		int length = 0;
		while (temp != null) {
			temp = temp.getNext();
			length++;
		}
		
		int index = 0;
		if (length % 2 == 0) {
			index = length / 2 + 1;
		} else {
			index = (length + 1) / 2;
		}
		Node cur = l1;
		for (int i = 1; i < index; i++) {
			cur = cur.getNext();
		}
		return cur;
	}
	
//	快慢指针，快指针每次前进两次，慢指针每次前进一次，当快指针为null时，慢指针正好在中间结点
	public static Node findMidNodeUseFastAndSlow(Node l1) {
		if (l1 == null) {
			return null;
		}
		Node fast = l1;
		Node slow = l1;
		while (fast != null && slow != null) {
			fast = fast.getNext().getNext();
			slow = slow.getNext();
		}
		return slow;
	}
	
}
