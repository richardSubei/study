package com.subei.linkedlist;

public class LinkedListDeleteNode {

	public static void main(String[] args) {
		Node head1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);

		head1.setNext(node2);
		node2.setNext(node3);
		node3.setNext(node4);
		node4.setNext(node5);
		
		Node l1 = deleteNode(head1, 3);
		while (l1 != null) {
			System.out.println(l1.getData());
			l1 = l1.getNext();
		}
	}
	
//	删除第n个结点
	public static Node deleteNode(Node l1, int n) {
		int i = 0;
		Node pre = new Node(-1);
		pre.setNext(l1);
		while (pre != null) {
			if (i+1 == n) {
				pre.setNext(pre.getNext().getNext());
			}
			pre = pre.getNext();
			i++;
		}
		return pre;
	}

//	删除倒数第n个结点
	public static Node deleteNodeBack(Node l1, int n) {
		int length = 1;
		while (l1 != null) {
			length++;				//得到链表长度
			l1 = l1.getNext();
		}
		
		while (l1 != null) {
			
		}
		
		
		return null;
	}
}
