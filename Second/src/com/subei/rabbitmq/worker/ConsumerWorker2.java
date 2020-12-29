package com.subei.rabbitmq.worker;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class ConsumerWorker2 {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("8.133.181.141");
		factory.setUsername("admin");
		factory.setPassword("admin");
		
		Connection connection = null;
		Channel channel = null;
		
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();

			DeliverCallback callback = new DeliverCallback() {
				@Override
				public void handle(String consumerTag, Delivery message) throws IOException {
					System.out.println("收到消息：" + new String(message.getBody(), "UTF-8"));
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			channel.queueDeclare("queue-worker", false, false, false, null);
			
			channel.basicQos(10);
			
			channel.basicConsume("queue-worker", callback, new CancelCallback() {
				
				@Override
				public void handle(String consumerTag) throws IOException {
					
				}
			});
			
			System.out.println("开始接受消息");
			System.in.read();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		
		
	}
}
