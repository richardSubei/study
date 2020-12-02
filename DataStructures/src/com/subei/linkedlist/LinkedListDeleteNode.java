package com.subei.linkedlist;

// 删除倒数第几个结点	LeetCode 19题
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
		
//		Node l1 = deleteNode(head1, 3);
//		Node l1 = deleteNodeBack(head1, 3);
		Node l1 = deleteNodeBackOneCycle(head1, 2);
		while (l1 != null) {
			System.out.println(l1.getData());
			l1 = l1.getNext();
		}
	}
	
//	删除第n个结点
	public static Node deleteNode(Node l1, int n) {
		int i = 0;
		Node preHead = new Node(-1);
		Node pre = preHead;
		pre.setNext(l1);
		while (pre != null) {
			if (i+1 == n) {
				pre.setNext(pre.getNext().getNext());
			}
			pre = pre.getNext();
			i++;
		}
		return preHead.getNext();
	}

//	删除倒数第n个结点
	public static Node deleteNodeBack(Node l1, int n) {
		int length = 0;
		Node lengthNode = l1;
		while (lengthNode != null) {
			length++;				//得到链表长度
			lengthNode = lengthNode.getNext();
		}
		Node preHead = new Node(-1);	//哨兵结点 为什么要用哨兵，因为如果要删除的是第一个结点，用了哨兵就可以不用做特殊的判断
		preHead.setNext(l1);
		Node pre = preHead;
		for (int i = 1; i < length - n + 1; i++) {
			pre = pre.getNext();
		}
		pre.setNext(pre.getNext().getNext());
		
		return preHead.getNext();
	}
	
//	一次循环实现	快慢指针
//	快指针领先慢指针n个结点，当快指针到达链尾时，
	public static Node deleteNodeBackOneCycle(Node l1, int n) {
		
		Node pre = new Node(-1);
		pre.setNext(l1);
		Node first = l1;
		Node second = pre;
		
//		首先将first指针与second指针拉开距离
		for (int i = 0; i < n; i++) {
			first = first.getNext();
		}
		
		while (first != null) {
			first = first.getNext();
			second = second.getNext();
		}
		second.setNext(second.getNext().getNext());
		return pre.getNext();
	}
	
	
}
