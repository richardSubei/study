package com.subei.net.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class BIOServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8080);
		System.out.println("服务器启动");
		while (!server.isClosed()) {
			Socket request = server.accept();					//阻塞
			System.out.println("收到连接：" + request.toString());
			InputStream inputStream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String msg;
			while ((msg = reader.readLine()) != null) {			//阻塞
				if (msg.length() == 0) {
					break;
				}
				System.out.println(msg);
			}
			System.out.println("收到数据，来自：" + request.toString());
			request.close();
		}
		server.close();
	}
}
