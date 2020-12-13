package com.subei.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NIOServer1 {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(8080));
		System.out.println("服务器启动成功");
		List<SocketChannel> list = new ArrayList<SocketChannel>();
		
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				System.out.println("收到请求，来自：" + socketChannel.getRemoteAddress());
				socketChannel.configureBlocking(false);
				list.add(socketChannel);
			} else {
				Iterator<SocketChannel> iterator = list.iterator();
				while (iterator.hasNext()) {
					SocketChannel socketChannel2 = iterator.next();
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					if (socketChannel2.read(byteBuffer) == 0) {
						continue;		//如果没有数据则继续运行
					}
					while (socketChannel2.isOpen() && socketChannel2.read(byteBuffer) != -1) {
//						此处做简单判断，读取一个字节就假设已经读取结束
						if (byteBuffer.position() > 0) {
							break;
						}
					}
					byteBuffer.flip();
					byte[] bytes = new byte[byteBuffer.limit()];
					byteBuffer.get(bytes);
					System.out.println(new String(bytes));
					
					String response = "response";
					ByteBuffer byteBuffer2 = ByteBuffer.wrap(response.getBytes());
					while (byteBuffer2.hasRemaining()) {
						socketChannel2.write(byteBuffer2);
					}
					
				}
			}
		}
		
		
	}
}
