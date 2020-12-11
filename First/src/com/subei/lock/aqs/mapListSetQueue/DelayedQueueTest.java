package com.subei.lock.aqs.mapListSetQueue;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

//（基于PriorityQueue来实现的）是一个存放Delayed 元素的无界阻塞队列，
//只有在延迟期满时才能从中提取元素。如果延迟都还没有期满，则队列没有头部，并且poll将返回null。
//当一个元素的 getDelay(TimeUnit.NANOSECONDS) 方法返回一个小于或等于零的值时，
//则出现期满，poll就以移除这个元素了。此队列不允许使用 null 元素。
public class DelayedQueueTest {

	public static void main(String[] args) throws InterruptedException {
		Message message = new Message("message-001", new Date(System.currentTimeMillis() + 5000L));
		Message message2 = new Message("message-002", new Date(System.currentTimeMillis() + 7000L));

		DelayQueue<Message> queue = new DelayQueue<Message>();
		queue.add(message);
		queue.add(message2);
		Message m;
		while (true) {
			m = queue.poll();
			if (m != null) {
				System.out.println(m.getContent());
			} else {
				System.out.println(m);
			}
			Thread.sleep(1000);
		}
	}
}


class Message implements Delayed {

	String content;
	Date sendTime;
	
	public Message(String content, Date sendTime) {
		this.content = content;
		this.sendTime = sendTime;
	}
	
//	延时时间，当poll时，会调用该方法，如果返回数小于0，说明到时间了
	@Override
	public long getDelay(TimeUnit unit) {
		long duration = sendTime.getTime() - System.currentTimeMillis();
		return TimeUnit.NANOSECONDS.convert(duration, TimeUnit.MILLISECONDS);	//纳秒
	}
//	当offer时，可以自定义优先级
	@Override
	public int compareTo(Delayed newDelayed) {
		return newDelayed.getDelay(TimeUnit.NANOSECONDS) < this.getDelay(TimeUnit.NANOSECONDS) ? 1: -1;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
}