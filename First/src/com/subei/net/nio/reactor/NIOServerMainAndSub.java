package com.subei.net.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;



public class NIOServerMainAndSub {
	
	private ReactorThread[] mainReactor = new ReactorThread[1];
	private ReactorThread[] subReactor = new ReactorThread[10];
	Selector selector;
	ServerSocketChannel serverSocketChannel;
	volatile int nextHandler = 0;
	
	public static void main(String[] args) {
		NIOServerMainAndSub nioServerMainAndSub = new NIOServerMainAndSub();
		nioServerMainAndSub.newGroup();
		nioServerMainAndSub.initAndRegister();
	}
	
	
	public void newGroup() {
		for (int i = 0; i < mainReactor.length; i++) {
			mainReactor[i] = new ReactorThread() {
				
				@Override
				public void handler(SelectableChannel channel) {
					ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
					SocketChannel socketChannel;
					try {
						socketChannel = serverSocketChannel.accept();
						socketChannel.configureBlocking(false);
						ReactorThread workEventLoop = subReactor[nextHandler];
						workEventLoop.doStart();
						SelectionKey key = workEventLoop.register(socketChannel);
						key.interestOps(SelectionKey.OP_READ);
						nextHandler++;
						if (nextHandler >= subReactor.length) {
							nextHandler = 0;
						}
	                    System.out.println(Thread.currentThread().getName() + "收到新连接 : " + socketChannel.getRemoteAddress());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}
		
		for (int i = 0; i < subReactor.length; i++) {
			subReactor[i] = new ReactorThread() {
				
				@Override
				public void handler(SelectableChannel channel) throws IOException {
                    // work线程只负责处理IO处理，不处理accept事件
                    SocketChannel ch = (SocketChannel) channel;
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                    while (ch.isOpen() && ch.read(requestBuffer) != -1) {
                        // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
                        if (requestBuffer.position() > 0) break;
                    }
                    if (requestBuffer.position() == 0) return; // 如果没数据了, 则不继续后面的处理
                    requestBuffer.flip();
                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);
                    System.out.println(new String(content));
                    System.out.println(Thread.currentThread().getName() + "收到数据,来自：" + ch.getRemoteAddress());

                    // TODO 业务操作 数据库、接口...
//                    workPool.submit(() -> {
//                    });

                    // 响应结果 200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Hello World";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    while (buffer.hasRemaining()) {
                        ch.write(buffer);
                    }
                }
			};
		}
		
	}
	
	public void initAndRegister() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			mainReactor[0].doStart();
			SelectionKey key = mainReactor[0].register(serverSocketChannel);
			key.interestOps(SelectionKey.OP_ACCEPT);
			serverSocketChannel.bind(new InetSocketAddress(8080));
			System.out.println("服务器启动成功，端口8080");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

abstract class ReactorThread extends Thread {

	Selector selector;
	boolean running = false;
	LinkedBlockingQueue<FutureTask<SelectionKey>> queue = new LinkedBlockingQueue<FutureTask<SelectionKey>>();
	
	public ReactorThread() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void handler(SelectableChannel channel) throws IOException;
	
	public SelectionKey register(SelectableChannel selectableChannel) {
    		final SelectableChannel channel2 = selectableChannel;
		FutureTask<SelectionKey> futureTask = new FutureTask<SelectionKey>(new Callable<SelectionKey>() {

			public SelectionKey call() throws Exception {
				return channel2.register(selector, 0, channel2);
			}
		});
		try {
			queue.put(futureTask);
			return futureTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void run() {
		while (running) {
			
			try {
				FutureTask<SelectionKey> futureTask;
				if ((futureTask = queue.poll()) != null) {
					futureTask.run();
				}
				
				selector.select(1000);
				
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					iterator.remove();
					if (key.isAcceptable() | key.isReadable()) {
						SelectableChannel selectableChannel = (SelectableChannel) key.attachment();
						selectableChannel.configureBlocking(false);
						handler(selectableChannel);
						if (!selectableChannel.isOpen()) {
							key.cancel();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public void doStart() {
		if (!running) {
			running = true;
			start();
		}
	}
	
}

