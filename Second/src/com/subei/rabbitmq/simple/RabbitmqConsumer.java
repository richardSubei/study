package com.subei.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class RabbitmqConsumer {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		
		factory.setHost("8.133.181.141");
		factory.setUsername("admin");
		factory.setPassword("admin");
		
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection("消费者");
			channel = connection.createChannel();
			
			channel.queueDeclare("queuetest", false, false, false, null);
//			回调函数，收到数据后调用此方法
			DeliverCallback callback = new DeliverCallback() {
				
				@Override
				public void handle(String consumerTag, Delivery message) throws IOException {
					System.out.println("收到消息：" + new String(message.getBody(), "UTF-8"));
				}
			};
			channel.basicConsume("queuetest", callback, new CancelCallback() {
				
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
