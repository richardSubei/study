package com.subei.tomcat;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

//tomcat调优
//jmeter 1秒发送1000个请求，调整tomcat的线程数，观察服务器cpu的使用率，
//在jmeter中查看吞吐量，平均响应时间等
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@RequestMapping("/testCount")
	public String testCount() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
	
	@RequestMapping("/test")
	public String benckmark() {
		System.out.println("访问test" + Thread.currentThread().getName());
		for (int i = 0; i < 20000; i++) {
			new Random().nextInt();
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
	
}
