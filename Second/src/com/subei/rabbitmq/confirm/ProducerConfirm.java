package com.subei.rabbitmq.confirm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Confirm.SelectOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

// rabbitmq 发布确认机制
// 发送的可靠性
//https://www.rabbitmq.com/confirms.html
public class ProducerConfirm {

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
//			开启发布确认机制
			SelectOk selectOk = channel.confirmSelect();
			List<String> queue = new ArrayList<>();
			channel.addConfirmListener(new ConfirmListener() {
//				发送失败回调
				@Override
				public void handleNack(long deliveryTag, boolean multiple) throws IOException {
					System.out.println("受理失败：" + deliveryTag);
				}
//				发送成功回调	
				@Override
				public void handleAck(long deliveryTag, boolean multiple) throws IOException {
					System.out.println("受理成功：" + queue.get((int)deliveryTag) + " " + multiple);	//multiple 是否批量
				}
			});
			channel.exchangeDeclare("exchange-confirm", "fanout");
			for (int i = 0; i < 20; i++) {
				String msg = "Hello confirm " + i;
				queue.add(msg);
				BasicProperties basicProperties = new BasicProperties();
				channel.basicPublish("exchange-confirm", "", basicProperties, msg.getBytes());	
				System.out.println("消息已发送 " + i);
			}
			Thread.sleep(20 * 1000);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
}
