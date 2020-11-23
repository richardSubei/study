package com.subei.bytecodeexecute;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;

public class MethodHandle {

	
	public static void main(String[] args) throws Throwable {
		Object obj = System.currentTimeMillis() % 2 == 0? System.out: new A();
		
		getPrintMH(obj).invokeExact("test");
		
	}
	
	private static java.lang.invoke.MethodHandle getPrintMH(Object receiver) {
//		MethodType mt = MethodType.methodType(void.class, String.class);
		return null;
//		return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
	}
	
}

class A {
	public void println(String s) {
		System.out.println(s);
	}
}
