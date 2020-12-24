package com.subei.gc;

//内存分配策略	 serial/serial old	parnew/seriol old (不同收集器分配策略是不同的)

//对象优先在eden分配，当eden空间不够时，将通过分配担保机制提前进入老年代

//大对象直接进入老年代
//-XX:PretenureSizeThreshold=1024*1024  此参数的单位只能是字节，如题为1M

//长期存活的对象进入老年代
//-XX:MaxTenuringThreshold=15 设置经过多少次moniorGC还未被回收则进入老年代 默认为15次
//但是当survivor中相同年龄的对象超过survivor空间的一半时，则大于等于该年龄的对象提前进入
//老年代，就算其年龄未超过设置的值

//空间分配担保
//在发生monior GC之前，虚拟机会检查老年代连续空间大于是否大于新生代所有存活的对象所占空间，
//若大于则这次GC是安全的，否则要看是否允许分配担保失败（HandlePromotionFailure），
//如果不允许，将发生Full GC，如果允许，则判断老年代连续空间是否大于历次晋升到老年代对象的平均大小，
//如果大于，将尝试进行monior GC，尽管这次GC是有风险的
//如果小于，将进行full GC
public class AllocationStrategy {

	public static final int _1M = 1024 * 1024;
	

	public static void main(String[] args) {
		testAllocation();
	}
	
/*	-verbose:gc
	-Xms20m
	-Xmx20m
	-Xmn10m
	-XX:+PrintGCDetails
	-XX:SurvivorRatio=8
	-XX:+UseSerialGC	-XX:+UserParNewGC	
	-XX:+UseParallelGC	-XX:+UserParallelOldGC
	-XX:+UseConcMarkSweepGC
*/
	public static void testAllocation() {
		byte[] byte1 = new byte[2 * _1M];
		byte[] byte2 = new byte[2 * _1M];
		byte[] byte3 = new byte[2 * _1M];
		byte[] byte4 = new byte[4 * _1M];
		//3个2M的数组通过分配担保机制进入老年代 		
	}

}
