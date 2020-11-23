package com.subei.bytecodeexecute;

public class DispatchDynamic {
	
	public static void main(String[] args) {
		Human human = new Man();
		human.sayHello();
	}
	
}

class Human {
	public void sayHello() {
		System.out.println("Human");
	}
}

class Man extends Human {
	@Override
	public void sayHello() {
		System.out.println("man");
	}
}

class Women extends Human {
	@Override
	public void sayHello() {
		System.out.println("women");
	}
}