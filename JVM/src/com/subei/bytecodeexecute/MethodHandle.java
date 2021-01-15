package com.subei.bytecodeexecute;

import java.io.PrintStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandle {

	
	public static void main(String[] args) throws Throwable {
		Object obj = System.currentTimeMillis() % 2 == 0? System.out: new A();
		
		getPrintMH(obj).invokeExact("test");
		
	}
	
	private static java.lang.invoke.MethodHandle getPrintMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
		MethodType mt = MethodType.methodType(void.class, String.class);
		return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
	}
	
}

class A {
	public void println(String s) {
		System.out.println(s);
	}
}
