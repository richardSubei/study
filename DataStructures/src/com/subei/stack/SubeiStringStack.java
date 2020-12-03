package com.subei.stack;

public class SubeiStringStack {

	private String[] items;
	private int length;
	private int tail;
	
	public SubeiStringStack(int n) {
		items = new String[n];
		length = n;
		tail = 0;
	}
	
	public boolean push(String value) {
		if (tail == length) {
			return false;
		}
		items[tail] = value;
		tail++;
		return true;
	}
	
	public String pop() {
		if (tail == 0) {
			return null;
		}
		String value = items[tail-1];
		tail--;
		return value;
	}
	
	public boolean isEmpty() {
		if (tail == 0) {
			return true;
		}
		return false;
	}
	
	
}
