package com.subei.bytecodeexecute;

//依赖静态类型决定调用方法的为静态分派
public class DispatchStatic {
	
	static class Human {
		
	}

	static class Man extends Human {
		
	}

	static class Women extends Human {
		
	}

	public void sayHello(Human human) {
		System.out.println("human");
	}
	public void sayHello(Man man) {
		System.out.println("man");
	}
	public void sayHello(Women women) {
		System.out.println("women");
	}
//	静态分派，根据参数的外观类型确认调用哪一个方法
//	恰好最近学习面向对象编程和面向过程编程的知识，这种数据和方法相分离的写法是明显的面向过程编程风格
//	面向对象和面向过程编程，主要区别是代码的组织方式不同，面向对象是以类为组织代码的基本单元
//	面向过程以数据和方法为组织代码的基本单元
	public static void main(String[] args) {
		DispatchStatic dispatchStatic = new DispatchStatic();
		Human human = new Human();
		Human human2 = new Man();
		Human human3 = new Women();
		dispatchStatic.sayHello(human);
		dispatchStatic.sayHello(human2);
		dispatchStatic.sayHello(human3);
	}
}

