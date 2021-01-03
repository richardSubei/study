package com.subei.rabbitmq.confirm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

// 消息确认机制
public class ConsumerConfirm {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("8.133.181.141");
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setVirtualHost("v1");

		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			// 死信队列，出异常的数据放入此队列
			channel.exchangeDeclare("dlq-exchange", "fanout");
			channel.queueDeclare("dlq-queue", false, false, false, null);
			channel.queueBind("dlq-queue", "dlq-exchange", "");

			channel = connection.createChannel();
			channel.exchangeDeclare("exchange-confirm", "fanout");
			Map<String, Object> arguments = new HashMap<String, Object>();
//			x-dead-letter-exchange 死信队列
			arguments.put("x-dead-letter-exchange", "dlq_exchange");
			channel.queueDeclare("queue-confirm", false, false, false, arguments);
			channel.queueBind("queue-confirm", "exchange-confirm", "");

			Channel finalChannel = channel;
			channel.basicConsume("queue-confirm", false, "消费者-手动回执", 
					new DefaultConsumer(finalChannel) {
						public void handleDelivery(String consumerTag, 
								com.rabbitmq.client.Envelope envelope, 
								com.rabbitmq.client.AMQP.BasicProperties properties, 
								byte[] body) throws IOException {
							try {
								System.out.println("收到消息：" + new String(body));
//								消息序号
								long deliveryTag = envelope.getDeliveryTag();
								Thread.sleep(1000);
//								finalChannel.basicAck(deliveryTag, false);
								finalChannel.basicNack(deliveryTag, false, false);
							} catch (InterruptedException e) {
//								异常消费, requeue参数 true重发，false不重发(丢弃或者移到DLQ死信队列)
								finalChannel.basicNack(envelope.getDeliveryTag(), false, false);
								e.printStackTrace();
							}
							
						};
					}

			);
			System.out.println("准备接受消息");
			System.in.read();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

	}
}
