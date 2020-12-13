package com.subei.lock.cas;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * CAS	compareAndSwap	比较并交换	直接操作内存	java底层方法
 * 用工作内存中的值与主内存中的值进行比较，如果两个值一样才进行下一步操作
 * @author forever11270201
 *
 */
public class CASTest {
	
	int value = 0;
	
	private static Unsafe unsafe;
	
	static long valueOffSet; 	//内存偏移量，即在内存中的地址
	
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");	//反射方法获得unsafe对象
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
			
			valueOffSet = unsafe.objectFieldOffset(CASTest.class.getDeclaredField("value"));	//获得value属性在内存中的地址
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add() {
		int current;
		do {
			current = unsafe.getIntVolatile(this, valueOffSet);	//根据内存地址，获得其值
		} while (!unsafe.compareAndSwapInt(this, valueOffSet, current, current+1));
		//compareAndSwapInt(this, valueOffSet, current, current+1)
		//1.对象
		//2.变量所在内存位置
		//3.工作内存中的值
		//4.下一步操作
	}
	
	public static void main(String[] args) throws Exception {
		CASTest casTest = new CASTest();
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10000; j++) {
						casTest.add();
					}
				}
			}).start();
		}
		Thread.sleep(2000);
		System.out.println(casTest.value);
	}
}
