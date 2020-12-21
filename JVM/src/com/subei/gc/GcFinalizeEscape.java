package com.subei.gc;

// 可达性算法分析，某些对象作为GC-ROOTS，搜索与GC-ROOTS相关联的对象，搜索的路径称为引用链，
// 当一个对象到GC-ROOTS没有任何一个引用链相关联时，则此对象为可回收的对象
// 栈中引用的对象、静态变量引用的对象、常量引用的对象、本地方法栈中引用的对象 可作为GC-ROOTS

// 对象如果要被回收至少要经过两次标记过程，可达性算法分析为第一次标记，
// 第一次标记之后将会进行一次筛选，筛选的条件为是否有必要执行finalize方法
// 如果对象没有覆盖finalize方法或者该方法已经被虚拟机调用过，则没必要执行
// 如果有必要执行则这个对象将被放在一个F-QUEUE队列中，由一个低优先级的finalizer线程去执行它
// 稍后GC将对F-QUEUE中的对象进行第二次标记，如果对象在finalize方法中重新与其他对象关联，
// 则会拯救自己，否则GC将会回收此对象
public class GcFinalizeEscape {

	static GcFinalizeEscape SAVE_HOOK;
	
	public void isAlive() {
		System.out.println("Im alive");
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize 方法执行");
		SAVE_HOOK = this;	//重新与引用链上的对象关联
//		SAVE_HOOK = new Obj();
	}
	
	public static void main(String[] args) throws InterruptedException {
		SAVE_HOOK = new GcFinalizeEscape();
		SAVE_HOOK = null;
//		第一次拯救自己
		System.gc();
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("Im dead");
		}
		
		SAVE_HOOK = null;
//		虚拟机只会调用一次finalize方法，所以这里拯救失败
		System.gc();
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("Im dead");
		}
	}
	
}

class Obj extends GcFinalizeEscape{
	
}