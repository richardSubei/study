package com.subei.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {

	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
		System.out.println("发送请求");
		
		while (!socketChannel.finishConnect()) {
			Thread.yield();
		}
		
		String msg = getMsg();
		ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
		while (byteBuffer.hasRemaining()) {
			socketChannel.write(byteBuffer);
		}
		
		ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
		while (socketChannel.isOpen() && socketChannel.read(byteBuffer2) != -1) {		//读到通道末尾返回-1
			if (byteBuffer2.position() > 0) {
				break;
			}
		}
		byteBuffer2.flip();
		byte[] bytes = new byte[byteBuffer2.limit()];
		byteBuffer2.get(bytes);
		System.out.println(new String(bytes));
		
		socketChannel.close();
		
	}
	
	public static String getMsg() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入：");
		String msg = scanner.nextLine();
		return msg;
	}
}
