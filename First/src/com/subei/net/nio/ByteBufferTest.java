package com.subei.net.nio;

import java.nio.ByteBuffer;

public class ByteBufferTest {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(4);	//还可以通过wrap方法将字节数组转换为bytebuffer
		System.out.println("capacity:" + buffer.capacity() + "-" + "position:" + buffer.position() + "-" + "limit:" + buffer.limit());
		
		buffer.put((byte)1);
		buffer.put((byte)2);
		buffer.put((byte)3);
		
		System.out.println("capacity:" + buffer.capacity() + "-" + "position:" + buffer.position() + "-" + "limit:" + buffer.limit());

		buffer.flip();	//转换为读取模式
		buffer.get();
		buffer.get();

		System.out.println("capacity:" + buffer.capacity() + "-" + "position:" + buffer.position() + "-" + "limit:" + buffer.limit());

		buffer.compact();	//清空已阅读的数据，转为写入模式	clear方法清空整个缓冲区
		
		System.out.println("capacity:" + buffer.capacity() + "-" + "position:" + buffer.position() + "-" + "limit:" + buffer.limit());

//		rewind 重置position
//		mark 标记position位置
//		reste 重置position为上次mark标记的位置
		
		
	}
}
