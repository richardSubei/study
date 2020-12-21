package com.subei.gc;

// CMS 致力于减少停顿时间
// 初始标记
// 并发标记
// 重新标记
// 并发清除

//1.cpu资源敏感 
//2.无法处理浮动垃圾，所以需要预留一部分空间存放浮动垃圾 
//3.并发清除算法产生内存碎片，可以通过参数设置是否整理内存，也可以设置在进行多少次不压缩的收集以后
//紧接着来一次压缩的收集
public class GarbageCollector {

	
}
