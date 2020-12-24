package com.subei.stringpool;


public class StringPoolTest {

	public static void main(String[] args) {
//		jdk1.7之后
		
//		在字符串常量池中创建一个对象，s1指向常量池
//		String s1 = "1";
//		常量池中存在"1"，在堆中创建一个String对象，该对象指向常量池中的"1"
//		String s2 = new String("1");	
//		常量池中不存在"3"，仅在堆中创建一个对象，不在常量池中创建
//		String s3 = new String("3");
		
//		intern 池化：如果当前字符串存在于常量池中，判断的条件是equals方法返回ture，
//		也就是说字符串内容是一样的，那就返回字符串在常量池的引用；
//		如果当前字符串不存在于常量池，那常量池中创建一个引用，并指向堆中，然后返回常量池中的地址
		
//		String sa = "a";
//		String sasString = new String("a");
//		sasString = sasString.intern();		//常量池中存在a 直接返回常量池中的引用	
//		System.out.println(sa == sasString);
		
//		String sasString = new String("a");
//		sasString = sasString.intern();		//常量池中不存在，在常量池中创建引用指向堆，返回常量池地址
//		String sa = "a";
//		System.out.println(sa == sasString);
//		
		
		String s1 = "古时的风筝";
		String s2 = "古时的风筝";
		String s3 = new String("古时的风筝");
		String s4 = new String("古时的风筝");
		System.out.println(s1 == s2);
		System.out.println(s2 == s3);
		System.out.println(s3 == s4);
		s3.intern();
		System.out.println(s2 == s3);	//false 仅调用intern方法，s3还是指向堆中对象
		s3 = s3.intern();	
		System.out.println(s2 == s3);	//true s3此时指向常量池
		s4 = s4.intern();
		System.out.println(s3 == s4);	//true s4也指向常量池
	}
}
