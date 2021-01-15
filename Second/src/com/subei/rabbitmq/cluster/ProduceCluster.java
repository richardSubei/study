package com.subei.rabbitmq.cluster;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import com.rabbitmq.client.AMQP.BasicProperties;
// 高可用		
// 要开启镜像队列
public class ProduceCluster {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("order-user");
		factory.setPassword("order-user");
		factory.setVirtualHost("v1");
		
		Address[] addresses = new Address[]{
			new Address("8.133.181.141"),
			new Address("8.133.170.150")
		};
//		开启、关闭自动重连
		factory.setAutomaticRecoveryEnabled(true);
//		设置每100ms自动重连，默认为5秒
		factory.setNetworkRecoveryInterval(100);
		factory.setTopologyRecoveryEnabled(false);
		
		Connection connection = null;
		Channel channel = null;
				
		try {
			connection = factory.newConnection(addresses);
			
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
			channel.confirmSelect();
			channel.addConfirmListener(new ConfirmListener() {
				
				@Override
				public void handleNack(long deliveryTag, boolean multiple) throws IOException {
					System.out.println("send failed: " + deliveryTag);
				}
				
				@Override
				public void handleAck(long deliveryTag, boolean multiple) throws IOException {
					System.out.println("send success: " + deliveryTag);
				}
			});
			channel.exchangeDeclare("exchange-cluster", "fanout");
			channel.queueDeclare("queue-cluster", false, false, false, null);
			channel.queueBind("queue-cluster", "exchange-cluster", "");
			
            for (int i = 0; i < 20; i++) {
                // 消息内容
                String message = "Hello World " + i;
                try {
    				BasicProperties basicProperties = new BasicProperties();
                    // 发送消息
                    channel.basicPublish("", "queue-cluster", basicProperties, message.getBytes());
                } catch (AlreadyClosedException e) {
                    // 可能连接已关闭，等待重连
                    System.out.println("消息 " + message + " 发送失败！");
                    i--;
                    TimeUnit.SECONDS.sleep(1);
                    continue;
                }
                System.out.println("消息 " + i + " 已发送！");
                TimeUnit.SECONDS.sleep(3);
            }
			
		} catch (IOException | TimeoutException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}
