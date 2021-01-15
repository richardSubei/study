package com.subei.rabbitmq.cluster;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import com.rabbitmq.client.AMQP.BasicProperties;

// gaokeyong and manual ack
public class ConsumeCluster {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("order-user");
		factory.setPassword("order-user");
		factory.setVirtualHost("v1");
		
		Address[] addresses = new Address[]{
				new Address("8.133.181.141"),
				new Address("8.133.170.150")
		};
//		开启自动重连
		factory.setAutomaticRecoveryEnabled(true);
//		设置每200ms重连一次
		factory.setNetworkRecoveryInterval(200);
		
		Connection connection = null;
		Channel channel = null;
		
		try {
			connection = factory.newConnection(addresses);
			
            // 添加重连监听器
            ((Recoverable) connection).addRecoveryListener(new RecoveryListener() {
//				开始重连回调
				@Override
				public void handleRecoveryStarted(Recoverable recoverable) {
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date()) + " 开始尝试重连！");
				}
//				重连成功后的回调
				@Override
				public void handleRecovery(Recoverable recoverable) {
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date()) + " 已重新建立连接！");
				}
			});
			channel = connection.createChannel();
			DeliverCallback deliverCallback = new DeliverCallback() {
				
				@Override
				public void handle(String consumerTag, Delivery message) throws IOException {
					System.out.println("收到数据：" + new String(message.getBody(), "UTF-8"));
				}
			};
			
			channel.exchangeDeclare("exchange-cluster", "fanout");
			channel.queueDeclare("queue-cluster", false, false, false, null);
			channel.queueBind("queue-cluster", "exchange-cluster", "");
			Channel finalChannel = channel;
			
			channel.basicConsume("queue-cluster", false, "manual", new DefaultConsumer(finalChannel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					System.out.println("收到数据：" + new String(body, "UTF-8"));
					long deliveryTag = envelope.getDeliveryTag();
					finalChannel.basicAck(deliveryTag, false);	
//					finalChannel.basicNack(deliveryTag, false, false);
				}
			});
			
//			channel.basicConsume("queue-cluster", deliverCallback, new CancelCallback() {
//				@Override
//				public void handle(String consumerTag) throws IOException {
//					
//				}
//			});
			
			System.out.println("准备接受数据");
			System.in.read();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
