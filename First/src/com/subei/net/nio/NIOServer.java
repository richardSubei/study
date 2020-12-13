package com.subei.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class NIOServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(8080));
		serverSocketChannel.configureBlocking(false);
		System.out.println("服务器启动成功");
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();	
			if (socketChannel != null ) {
				System.out.println("收到请求，来自：" + socketChannel.getRemoteAddress());
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				while (socketChannel.read(byteBuffer) != -1 ) {
//					常连接情况下需要手动判定有没有读取结束（此处做一个简单判断，超过0字节就读取结束）
					if (byteBuffer.position() > 0) {		//假设只要读到数据就跳出循环
						break;
					}
				}
				
				byteBuffer.flip();	//读取模式
				byte[] bytes = new byte[byteBuffer.limit()];	
				byteBuffer.get(bytes);		//读取buffer内容，并存入bytes数组
				System.out.println(new String(bytes));
				
				String response = "response";
				
				ByteBuffer repBuffer = ByteBuffer.wrap(response.getBytes());	//创建bytebuffer的两种方式之一
				while (repBuffer.hasRemaining()) {	//position < limit
					socketChannel.write(repBuffer);
				}				
			}
			Thread.sleep(3000);
		}
	}
}
