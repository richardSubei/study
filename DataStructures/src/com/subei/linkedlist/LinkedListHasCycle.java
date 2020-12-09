package com.subei.linkedlist;

import java.util.HashSet;
import java.util.Set;

// 环形链表，检测链表中是否有环	LeetCode 141题
public class LinkedListHasCycle {

	public static void main(String[] args) {
		Node head = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		
		head.setNext(node2);
		node2.setNext(node3);
		node3.setNext(node4);
		node4.setNext(node2);
		
		System.out.println(hasCycleUseSet(head));
		System.out.println(hasCycleUseFastAndSlow(head));
		System.out.println(checkReviw(head));

	}
	
//	如果是环形的链表，循环过程中，肯定会有结点会重复加入set中，
//	利用set的不可重复性，如果重复结点加入会返回false，证明存在环形
//	时间复杂度O(n) 空间复杂度O(n)
	public static boolean hasCycleUseSet(Node head) {
		if (head == null || head.getNext() == null) {
			return false;
		}
		Set<Node> set = new HashSet<Node>();
		while (head != null) {
			if (!set.add(head)) {
				return true;
			}
			head = head.getNext();
		}
		return false;
	}
	
//	快慢指针，龟兔赛跑，乌龟跑的慢，兔子跑的快，如果是环形的，
//	那兔子和乌龟肯定会再次相遇，也就是兔子把乌龟套圈了
//	时间复杂度O(n) 空间复杂度O(1)
	public static boolean hasCycleUseFastAndSlow(Node head) {
		if (head == null || head.getNext() == null) {
			return false;
		}
		Node slow = head;
		Node fast = head.getNext();
		while (slow != null) {
			if (fast == slow) {
				return true;
			}
			slow = slow.getNext();
			fast = fast.getNext().getNext();
		}
		return false;
	}
	
//	复习写一遍
	public static boolean checkReviw(Node l) {
		Node slow = l;
		Node fast = l;
		Node cur = l;
		while (cur != null) {
			slow = slow.getNext();
			fast = fast.getNext().getNext();
			cur = cur.getNext();
			if (slow == fast) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
