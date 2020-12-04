package com.subei.stack;

public class StackTest {

	public static void main(String[] args) {
//		SubeiStack stack = new SubeiStack(3);
//		stack.push(1);
//		stack.push(2);
//		System.out.println(stack.push(3));
//		System.out.println(stack.push(4));
//		
//		System.out.println(stack.pop());
//		stack.push(10);
//		System.out.println(stack.pop());
//		System.out.println(stack.pop());
//		System.out.println(stack.pop());
		
		SubeiStack stack = new SubeiStack(5);
		stack.push(-2);
		stack.push(-3);
		System.out.println(stack.top());
		System.out.println(stack.getMin());
		stack.push(-5);
		stack.push(2);
		System.out.println(stack.getMin());
		stack.push(-100);
		System.out.println(stack.getMin());
		stack.push(-200);							//失败，超过大小
		System.out.println(stack.getMin());

	}
	
}
