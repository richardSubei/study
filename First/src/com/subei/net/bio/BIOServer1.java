package com.subei.net.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer1 {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);
		System.out.println("服务器启动成功");
		
		while (!serverSocket.isClosed()) {
			Socket request = serverSocket.accept();
			System.out.println("收到请求，来自：" + request.getRemoteSocketAddress());
			new ReadAndWriteThread(request).start();
		}
	}
}

// 因为BIO的读写操作是耗时的，如果用单线程操作那只能处理一个请求
class ReadAndWriteThread extends Thread {

	private Socket request;
	
	public ReadAndWriteThread(Socket request) {
		this.request = request;
	}
	
	@Override
	public void run() {
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String msg = "";
			while ((msg = bufferedReader.readLine()) != null) {
				if (msg.length() == 0) {
					break;
				}
				System.out.println(msg);
			}
			System.out.println("收到数据，来自：" + request.getRemoteSocketAddress());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				request.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}


