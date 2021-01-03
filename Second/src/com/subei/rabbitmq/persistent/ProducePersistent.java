package com.subei.rabbitmq.persistent;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

// 持久化
public class ProducePersistent {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setVirtualHost("v1");
		factory.setHost("8.133.181.141");
		
		Connection connection = null;
		Channel channel = null;
		
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare("exchange-persistent", "direct", true);
			channel.queueDeclare("queue-persistent", true, false, false, null);
			channel.queueBind("queue-persistent", "exchange-persistent", "c1");
			channel.queueBind("queue-persistent", "exchange-persistent", "c2");
			
			String msga = "Hello A";
//			持久化消息	MessageProperties.PERSISTENT_BASIC
			channel.basicPublish("exchange-persistent", "c1", MessageProperties.PERSISTENT_BASIC, msga.getBytes());
			System.out.println("消息A已发送");
			
			String msgb = "Hello B";
			channel.basicPublish("exchange-persistent", "c2", MessageProperties.PERSISTENT_BASIC, msgb.getBytes());
			System.out.println("消息B已发送");
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		
	}
}
