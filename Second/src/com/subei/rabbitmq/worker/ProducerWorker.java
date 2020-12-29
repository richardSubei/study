package com.subei.rabbitmq.worker;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerWorker {

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
			
			channel.queueDeclare("queue-worker", false, false, false, null);
			
			for (int i = 0; i < 20; i++) {
				String msg = "Hello" + i;
				channel.basicPublish("", "queue-worker", null, msg.getBytes());
				System.out.println("消息" + i + "已经发送");
			}
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	
	}
}
