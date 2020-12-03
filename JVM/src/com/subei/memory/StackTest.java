package com.subei.memory;

public class StackTest {

	public static void main(String[] args) {
		test(1);
	}
	
	
	public static void test(int i) {
		System.out.println(i);
		test(++i);
	}
}
