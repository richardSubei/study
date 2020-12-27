package com.subei.rabbitmq.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerRouting {

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
			
			String msg = "Hello A";
			
			channel.basicPublish("direct-exchange", "routing key1", null, msg.getBytes());
			System.out.println("消息已发送");
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} finally {
			if (channel != null && channel.isOpen()) {
				try {
					channel.close();
				} catch (IOException | TimeoutException e) {
					e.printStackTrace();
				}
			}
			if (connection != null && connection.isOpen()) {
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
