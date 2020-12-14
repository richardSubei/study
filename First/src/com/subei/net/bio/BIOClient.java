package com.subei.net.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BIOClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("localhost", 8080);
		OutputStream out = s.getOutputStream();

		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入：");
		String msg = scanner.nextLine();
		out.write(msg.getBytes()); // 阻塞，写完成
		scanner.close();
		s.close();
	}
}