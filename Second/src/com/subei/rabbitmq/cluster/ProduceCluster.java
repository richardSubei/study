package com.subei.rabbitmq.cluster;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
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
			
			channel.exchangeDeclare("exchange-fanout", "fanout");
			channel.queueDeclare("queue1", false, false, false, null);
			channel.queueBind("queue1", "exchange-fanout", "");
			
            for (int i = 0; i < 20; i++) {
                // 消息内容
                String message = "Hello World " + i;
                try {
                    // 发送消息
                    channel.basicPublish("", "queue1", null, message.getBytes());
                } catch (AlreadyClosedException e) {
                    // 可能连接已关闭，等待重连
                    System.out.println("消息 " + message + " 发送失败！");
                    i--;
                    TimeUnit.SECONDS.sleep(2);
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
