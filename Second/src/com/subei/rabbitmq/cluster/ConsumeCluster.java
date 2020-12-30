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
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;

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
		
		factory.setAutomaticRecoveryEnabled(true);
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
			
			channel.exchangeDeclare("exchange-fanout", "fanout");
			channel.queueDeclare("queue1", false, false, false, null);
			channel.queueBind("queue1", "exchange-fanout", "");
			channel.basicConsume("queue1", deliverCallback, new CancelCallback() {
				@Override
				public void handle(String consumerTag) throws IOException {
					
				}
			});
			
			System.out.println("消费者收到数据");
			System.in.read();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
