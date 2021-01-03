package com.subei.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

// 临时队列使用
public class ConsumerPS {

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
//			定义exchange		名称及类型
			channel.exchangeDeclare("exchange-ps", "direct");
//			临时队列
			String queueName = channel.queueDeclare().getQueue();
//			绑定exchange 和 队列 
			channel.queueBind(queueName, "exchange-ps", "key-ps");
			
			DeliverCallback callback = new DeliverCallback() {
				
				@Override
				public void handle(String consumerTag, Delivery message) throws IOException {
					System.out.println("收到数据：" + new String(message.getBody(), "UTF-8"));
				}
			};
			channel.basicConsume(queueName, callback, new CancelCallback() {
				
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
