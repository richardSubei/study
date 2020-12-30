package com.subei.rabbitmq.cluster;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class VirtualHostsDemo {

	public static void main(String[] args) {
		ConnectionFactory factory  = new ConnectionFactory();
		factory.setUsername("order-user");
		factory.setPassword("order-user");
		factory.setVirtualHost("v1");
		
		Connection connection = null;
		Channel produceChannel = null;
		Channel consumeChannel = null;
		
		Address[] addresses = new Address[]{
			new Address("8.133.181.141"),
			new Address("8.133.170.150")
		};
		try {
			connection = factory.newConnection(addresses);
			produceChannel = connection.createChannel();
			
			produceChannel.exchangeDeclare("exchange-fanout", "fanout");
			produceChannel.queueDeclare("queue-1", false, false, false, null);
			produceChannel.queueDeclare("queue-2", false, false, false, null);
			produceChannel.queueBind("queue-1", "exchange-fanout", "");
			produceChannel.queueBind("queue-2", "exchange-fanout", "");

			String msg = "hello hello";
			produceChannel.basicPublish("exchange-fanout", "", null, msg.getBytes());
			System.out.println("消息已发送");
			
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		
	}
}
