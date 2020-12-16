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

// 多Reactor 线程模型 自己练习
public class NIOServerMainAndSubPractise {

	private ReactorThreadCopy[] mainReactors = new ReactorThreadCopy[1];
	private ReactorThreadCopy[] subReactors = new ReactorThreadCopy[10];
	private ServerSocketChannel serverSocketChannel;
	private volatile int nextHandler = 0;
	
	public static void main(String[] args) {
		NIOServerMainAndSubPractise nioServerMainAndSubPractise = new NIOServerMainAndSubPractise();
		nioServerMainAndSubPractise.newGroup();
		nioServerMainAndSubPractise.initAndRegister();
		nioServerMainAndSubPractise.bind(8080);
	}
	
	private void initAndRegister() {
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			mainReactors[0].doStart();
			SelectionKey key = mainReactors[0].register(serverSocketChannel);
			key.interestOps(SelectionKey.OP_ACCEPT);
			key.attach(serverSocketChannel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void bind(int port) {
		try {
			serverSocketChannel.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("服务器启动成功，端口" + port);
	}
	
	public void newGroup() {
		for (int i = 0; i < mainReactors.length; i++) {
			mainReactors[i] = new ReactorThreadCopy() {
				
				@Override
				public void handler(SelectableChannel channel) {
					ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
					try {
						SocketChannel socketChannel = serverSocketChannel.accept();
						socketChannel.configureBlocking(false);
						if (socketChannel != null) {
							System.out.println(Thread.currentThread().getName() + "接收到请求，来自：" + socketChannel.getRemoteAddress());
							subReactors[nextHandler].doStart();
							SelectionKey key = subReactors[nextHandler].register(socketChannel);
							key.interestOps(SelectionKey.OP_READ);
							key.attach(socketChannel);
							nextHandler++;
							if (nextHandler >= subReactors.length) {
								nextHandler++;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}
		
		for (int i = 0; i < subReactors.length; i++) {
			subReactors[i] = new ReactorThreadCopy() {
				
				@Override
				public void handler(SelectableChannel channel) {
					SocketChannel socketChannel = (SocketChannel) channel;
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					try {
						while (socketChannel.isOpen() && socketChannel.read(readBuffer) != -1) {
							if (readBuffer.position() > 0) {
								break;
							}
						}
	                    if (readBuffer.position() == 0) return; // 如果没数据了, 则不继续后面的处理
						readBuffer.flip();
						byte[] bytes = new byte[readBuffer.limit()];
						readBuffer.get(bytes);
						System.out.println(new String(bytes));
						System.out.println(Thread.currentThread().getName() + "收到数据，来自：" + socketChannel.getRemoteAddress());
					
	                    // 响应结果 200
	                    String response = "HTTP/1.1 200 OK\r\n" +
	                            "Content-Length: 11\r\n\r\n" +
	                            "Hello World";
	                    ByteBuffer writeBuffer = ByteBuffer.wrap(response.getBytes());
	                    while (writeBuffer.hasRemaining()) {
	                        socketChannel.write(writeBuffer);
	                    }
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}
	}
}

abstract class ReactorThreadCopy extends Thread {
	
	private Selector selector;
	private boolean running = false;
	private LinkedBlockingQueue<FutureTask<SelectionKey>> taskQueue = new LinkedBlockingQueue<FutureTask<SelectionKey>>();
	
	public ReactorThreadCopy() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void handler(SelectableChannel channel);
	
	public SelectionKey register(SelectableChannel channel) {
		final SelectableChannel channel2 = channel;
		FutureTask<SelectionKey> futureTask = new FutureTask<SelectionKey>(new Callable<SelectionKey>() {

			public SelectionKey call() throws Exception {
				return channel2.register(selector, 0);
			}
		});
		taskQueue.add(futureTask);
		try {
			return futureTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void run() {
		while (running) {
			FutureTask<SelectionKey> task;
			if ((task = taskQueue.poll()) != null) {
				task.run();
			}
			
			try {
				selector.select(1000);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				if (key.isAcceptable() || key.isReadable()) {
					SelectableChannel channel = (SelectableChannel) key.attachment();
					try {
						channel.configureBlocking(false);
					} catch (IOException e) {
						e.printStackTrace();
					}
					handler(channel);
					if (!channel.isOpen()) {
						key.cancel();
					}
				}
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
