package com.subei.lock.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

public class LongAccumulatorTest {

	public static void main(String[] args) {
		LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
			@Override
			public long applyAsLong(long left, long right) {
//				return left * right;	//此处定义accumulate中的计算方法
				return left > right? left: right;
			}
		}, 2);
		
		longAccumulator.accumulate(4);
		System.out.println(longAccumulator.get());
		
		
	}
}
