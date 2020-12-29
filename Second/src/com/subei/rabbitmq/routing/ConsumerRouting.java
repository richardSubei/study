package com.subei.rabbitmq.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class ConsumerRouting {

	public static Runnable Service = new Runnable() {
		@Override
		public void run() {
			ConnectionFactory connectionFactory = new ConnectionFactory();
			connectionFactory.setHost("8.133.181.141");
			connectionFactory.setUsername("admin");
			connectionFactory.setPassword("admin");
			
			Connection connection = null;
			Channel channel = null;
			
			String queue = Thread.currentThread().getName();
			
			try {
				connection = connectionFactory.newConnection();
				channel = connection.createChannel();
				
				DeliverCallback callback = new DeliverCallback() {
					@Override
					public void handle(String consumerTag, Delivery message) throws IOException {
						System.out.println(queue + "收到消息：" + new String(message.getBody(), "UTF-8"));
					}
				};
				
				channel.basicConsume(queue, callback , new CancelCallback() {
					@Override
					public void handle(String consumerTag) throws IOException {
						
					}
				});
				System.out.println(queue + "开始接受消息");
				System.in.read();
			} catch (IOException | TimeoutException e) {
				e.printStackTrace();
			} finally {
//				关闭通道和连接 ，省略掉吧先
			}
		}
	};
	
	public static void main(String[] args) {
		new Thread(Service, "queue-ps").start();;
//		new Thread(Service, "queue-ps").start();;
	}
}
