package com.subei.stack;
//	先进后出，只在栈尾进行操作
//	java中方法的调用，利用栈
//	数据的计算，例如2+3*4,一个栈存数据，一个栈存运算符
//	浏览器的前进后退功能，使用两个栈X Y，a b c，页面依次浏览，
//	依次入X栈，后退时，依次出栈，并压入Y栈,前进时，从Y出栈，压入X栈

//	利用顺序存储结构（数组）实现一个栈
public class SubeiStack {

	private int[] items;
	private int[] min;
	private int size;
	private int tail;
	
	public SubeiStack(int n) {
		items = new int[n];
		min = new int[n];
		size = n;
		tail = 0;
	}
	
	public boolean push(int value) {
		if (tail == size ) {	
			return false;
		}
		items[tail] = value;
		if (tail == 0) {
			min[tail] = value;
		} else {
			if (value < min[tail - 1]) {
				min[tail] = value;
			} else {
				min[tail] = min[tail - 1];
			}
		}
		tail++;
		return true;
	}
	
	public int pop() {
		if (tail == 0) {
			throw new RuntimeException("越界");
		}
		int value = items[tail - 1];
		tail--;
		return value;
	}
	
	public int top() {
		if (tail == 0) {
			return -1;
		}
		return items[0];
	}
	
	public int getMin() {
		return min[tail - 1];
	}
	
}
