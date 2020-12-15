package com.subei.net.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


// 改造成mainReactor subReactor
public class CopyOfReactor implements Runnable {

	Selector selector;
	ServerSocketChannel serverSocketChannel;
	
	public static void main(String[] args) {
		new Thread(new CopyOfReactor()).start();
	}
	
	public CopyOfReactor() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(8080));
			System.out.println("服务启动");
//			注册accept事件
			SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//			附件，下文中根据key.attachment获取绑定的附件，如果是accept则实例为acceptor，如果为读写事件，则实例为Handler
			key.attach(new MainReactor(key));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					dispatch(key);	//分发事件
					iterator.remove();
				}
				keys.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dispatch(SelectionKey key) {
		// 面向接口，看key是哪个实例（acceptor/handler）
		Runnable runnable = (Runnable) key.attachment();	
		if (runnable != null) {
			runnable.run();
		}
	}
	
	class MainReactor implements Runnable {
		subReactor[] handlers = new subReactor[10];
		volatile int nextHandler = 0;
		
		public MainReactor(SelectionKey key) {
			this.init();
		}
		
		public void init() {
			for (int i = 0; i < handlers.length; i++) {
				handlers[i] = new subReactor();
			}
		}
		
		public void run() {
			try {
				SocketChannel socketChannel = serverSocketChannel.accept();
				socketChannel.configureBlocking(false);
				System.out.println("收到请求，来自：" + socketChannel.getRemoteAddress());
				if (socketChannel != null) {
					subReactor handler = handlers[nextHandler];
					handler.register(socketChannel);
					if (nextHandler >= handlers.length) {
						nextHandler = 0;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class subReactor implements Runnable {
	Selector selector;
	ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	
	public subReactor() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void register(SelectableChannel channel) {
		try {
			channel.register(selector, SelectionKey.OP_READ, this);
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					iterator.remove();
					if (key.isReadable()) {
						System.out.println("有数据了");
						System.out.println(Thread.currentThread());
					} 
					if (key.isWritable()) {
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



