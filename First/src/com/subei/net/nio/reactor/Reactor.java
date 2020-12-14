package com.subei.net.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {

	Selector selector;
	ServerSocketChannel serverSocketChannel;
	
	public static void main(String[] args) {
		new Thread(new Reactor()).start();
	}
	
	public Reactor() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(8080));
			System.out.println("服务启动");
			SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			key.attach(new Acceptor(key));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					dispatch(key);
					iterator.remove();
				}
				keys.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dispatch(SelectionKey key) {
		Runnable runnable = (Runnable) key.attachment();
		if (runnable != null) {
			runnable.run();
		}
	}
	
	
	class Acceptor implements Runnable {
		SelectionKey key;
		
		public Acceptor(SelectionKey key) {
			this.key = key;
		}
		
		@Override
		public void run() {
			try {
				SocketChannel socketChannel = null;
				if (key.isAcceptable()) {
					socketChannel = serverSocketChannel.accept();
					System.out.println("收到请求，来自：" + socketChannel.getRemoteAddress() );
					if (socketChannel != null) {
						new Thread(new Handler(selector, socketChannel)).start();
					}
				} else {
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

class Handler implements Runnable {
	SocketChannel socketChannel;
	SelectionKey key;
	ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	final Selector selector;
	
	public Handler(Selector selector, SocketChannel sChannel) {
		this.socketChannel = sChannel;
		this.selector = selector;
		try {
			socketChannel.configureBlocking(false);
			key = socketChannel.register(selector, SelectionKey.OP_READ);
			key.attach(this);
			selector.wakeup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		if (key.isReadable()) {
            try {
				while (socketChannel.isOpen() && socketChannel.read(readBuffer) != -1) {
				    // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
				    if (readBuffer.position() > 0) break;
				}
//				if(readBuffer.position() == 0) continue; // 如果没数据了, 则不继续后面的处理
				readBuffer.flip();
				byte[] content = new byte[readBuffer.limit()];
				readBuffer.get(content);
				System.out.println(new String(content));
				System.out.println("收到数据,来自：" + socketChannel.getRemoteAddress());
				key = socketChannel.register(selector, SelectionKey.OP_WRITE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (key.isWritable()) {
            // 响应结果 200
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: 11\r\n\r\n" +
                    "Hello World";
            ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
            while (buffer.hasRemaining()) {
                try {
					socketChannel.write(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            key.cancel();		//处理完毕，取消selector中的readable事件
		}
	}
	
}



