package com.subei.net.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


// Reactor多线程模型
public class ReactorMultiple implements Runnable{
	
	Selector selector;
	ServerSocketChannel serverSocketChannel;
	
	public static void main(String[] args) {
		new Thread(new ReactorMultiple()).start();
	}
	
	public ReactorMultiple() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(8080));
			System.out.println("服务器启动成功");
			SelectionKey key = serverSocketChannel.register(selector, 0);
			key.interestOps(SelectionKey.OP_ACCEPT);
			key.attach(new Aceptor(key));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (!Thread.interrupted()) {
			try {
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					iterator.remove();
					dispatch(key);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void dispatch(SelectionKey key) {
		Runnable r = (Runnable) key.attachment();
		if (r != null) {
			r.run();
		}
	}
	
	class Aceptor implements Runnable {
		
		SelectionKey key;
		
		public Aceptor(SelectionKey key) {
			this.key = key;
		}
		
		public void run() {
			try {
				SocketChannel socketChannel = serverSocketChannel.accept();
				System.out.println("收到请求，来自：" + socketChannel.getRemoteAddress());
				if (socketChannel != null) {
					new MultipleHandler(selector, socketChannel);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class MultipleHandler implements Runnable {

	SelectionKey key;
	SocketChannel socketChannel;
	ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	ExecutorService executorService = Executors.newFixedThreadPool(10);	//利用线程池处理任务
	
	public MultipleHandler(Selector selector, SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
		try {
			socketChannel.configureBlocking(false);
			key = socketChannel.register(selector, 0);
			key.interestOps(SelectionKey.OP_READ);
			key.attach(this);
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
//				doSomething 
//				与Reactor单线程模型的区别是此处使用线程池提交任务
				Future<String> future = executorService.submit(new Processer());	
				System.out.println(future.get());
				key.interestOps(SelectionKey.OP_WRITE);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		if (key.isAcceptable()) {
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
            key.cancel();		//处理完毕，取消selector中的事件
		}
	}
}

class Processer implements Callable<String> {

	public String call() throws Exception {
		return "处理成功";
	}
	
}



