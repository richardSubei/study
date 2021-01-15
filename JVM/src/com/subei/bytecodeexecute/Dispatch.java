package com.subei.bytecodeexecute;

// 单分派和多分派，根据方法的宗量来判断是单还是多，
// 多于一个宗量，那就是多分派
public class Dispatch {

	static class QQ {
		
	}
	
	static class _360 {
		
	}
	
	public static class Father {
		public void hardChoice(QQ arg) {
			System.out.println("Father choose QQ");
		}
		
		public void hardChoice(_360 arg) {
			System.out.println("Father choose 360");
		}
	}
	
	public static class Son extends Father {
		@Override
		public void hardChoice(QQ arg) {
			System.out.println("Son choose QQ");
		}
		
		@Override
		public void hardChoice(_360 arg) {
			System.out.println("Son choose 360");
		}
	}
	
	
	public static void main(String[] args) {
//		在编译阶段生成两条invokevirtual指令，根据外观类型，
//		确定两条指令的参数为Father.hardChoice(new _360()) Father.hardChoice(new QQ())
//		在虚拟机运行阶段，会根据实际类型确定最终调用的方法
		Father father = new Father();
		Father son = new Son();
		father.hardChoice(new _360());
		son.hardChoice(new QQ());
	}
}
