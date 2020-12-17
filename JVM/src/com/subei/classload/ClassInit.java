package com.subei.classload;

// 有且只有5种情况要初始化类
// 1.new putstatic getstatic invokeStatic 这4条指令时
// 2.如果子类初始化时，父类还没初始化，则先初始化父类
// 3.对类进行反射调用的时候，先对类初始化
// 4.虚拟机启动时，用户需要指定一个执行的主类（包含main方法的那个类），虚拟机会先初始化这个类
// 5.methHandler
// 以上为主动引用，main方法中有几个被动引用的例子，不会触发类的初始化
// 例如通过子类引用父类的静态字段，只会初始化父类；
// 通过数组来定义引用类，不会触发类的初始化
// 通过类引用final常量不会触发类的初始化，常量在编译阶段放入引用类的常量池中
public class ClassInit {

//	static {
//		System.out.println("classinit, 带main的主类");
//	}
	
	public static void main(String[] args) {
//		以下代码执行的时候都会先触发带main方法的主类，即方法的入口类
		
//		new Son();	new指令触发son初始化，发现父类还没初始化，则先初始化父类
//		int age = Father.age;	getstatic指令触发父类初始化
		
//		int age = Son.age;	 //被动引用例子之一，通过子类引用父类的静态字段，只会初始化父类，只有直接定义该静态字段的类才会初始化
		int age = Son.sonAge;	//常量在编译阶段放入调用类的常量池，本质上并没有直接引用定义常量的类
//		Son[] sons = new Son[10]; //通过数组来定义引用类，不会触发类的初始化。
// 虚拟机自动生成一个[Lcom.subie.classload.ClassInit的类，其直接继承于Object，创建动作由newarray触发。其内部封装了length属性和clone方法
		
//		String name = new Son().fatherName;
		
	}
	
}

class Father {
	
	public static int fatherAge = 30;
	
	public String fatherName = "father";
	
	static {
		System.out.println("Im father");
	}
}

class Son extends Father{
	public static final int sonAge = 3;
	
	public String sonName = "son";
	
	static {
		System.out.println("Im son");
	}
}

