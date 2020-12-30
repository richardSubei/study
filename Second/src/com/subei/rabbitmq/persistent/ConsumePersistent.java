package com.subei.rabbitmq.persistent;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class ConsumePersistent {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("8.133.181.141");
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setVirtualHost("v1");
		
		Connection connection = null;
		Channel channel = null;
		String clientName = Thread.currentThread().getName();
		
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			DeliverCallback deliverCallback = new DeliverCallback() {
				@Override
				public void handle(String consumerTag, Delivery message) throws IOException {
					System.out.println(clientName + "收到数据：" + new String(message.getBody(), "UTF-8"));
				}
			};
			
			channel.basicConsume("queue-persistent", deliverCallback, new CancelCallback() {
				@Override
				public void handle(String consumerTag) throws IOException {
					
				}
			});
			System.out.println("准备接收数据");
			System.in.read();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		
	}
}
