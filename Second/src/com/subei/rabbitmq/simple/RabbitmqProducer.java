package com.subei.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitmqProducer {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("8.133.181.141");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin");
		
		Connection connection = null;
		Channel channel = null;
		
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
            /**
             * 声明（创建）队列
             * 如果队列不存在，才会创建
             * RabbitMQ 不允许声明两个队列名相同，属性不同的队列，否则会报错
             *
             * queueDeclare参数说明：
             * @param queue 队列名称
             * @param durable 队列是否持久化
             * @param exclusive 是否排他，即是否为私有的，如果为true,会对当前队列加锁，其它通道不能访问，
             *                  并且在连接关闭时会自动删除，不受持久化和自动删除的属性控制。
             *                  一般在队列和交换器绑定时使用
             * @param autoDelete 是否自动删除，当最后一个消费者断开连接之后是否自动删除
             * @param arguments 队列参数，设置队列的有效期、消息最大长度、队列中所有消息的生命周期等等
             */
			channel.queueDeclare("queuetest", false, false, false, null);
			
			String msg = "Hello World simple";
			channel.basicPublish("", "queuetest", null, msg.getBytes());
			System.out.println("消息已发送");
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
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
