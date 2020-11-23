package com.subei.bytecodeexecute;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class UseInvoke {
	
	class GrandFather {
		public void thinking() {
			System.out.println("grandfather");
		}
	}

	class Father extends GrandFather {
		@Override
		public void thinking() {
			System.out.println("father");
		}
	}

	//想要在son类的thinking方法中调用grandfater的thinking方法应该怎么做
	//思考：jdk1.7之前，invokeVirtual，根据方法接受者的实际类型进行调用，想调用grandFather中的方法，
	//必须在son中持有一个grandFather的引用，我估计只能通过new或者反射的方式实现
	//jdk1.7之后 可以通过MethodHandle实现方法调用
	class Son extends Father {
		@Override
		public void thinking() {
			MethodType mt = MethodType.methodType(void.class);
			try {
				MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, this.getClass());
				mh.invoke(this);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new UseInvoke().new Son().thinking();
	}
}


