package com.subei.bytecodeexecute;
// 方法的解析
// 符合 编译期可知 运行时不变 的方法采用解析方式，包括使用invokestatic和invokespecial
// 调用的方法，invokestatic是静态方法，invokespecial包括私有方法，实例构造器，父类方法
// 这类方法在类加载阶段，将符号引用转换为直接引用。这类方法被称为非虚方法，其他称为虚方法。
// final方法虽然采用invokevirtual调用，但是由于final方法无法被覆盖，所以其是一种非虚方法
public class Resolution {

	public static void main(String[] args) {
		test();
		new Resolution().testFinal();
	}
	
	public static void test() {
		System.out.println("解析");
	}
	
	public final void testFinal() {
		System.out.println("final");
	}
}
