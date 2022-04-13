package com.ioTest1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.util.InputUtil;

 
 
public class BIOEchoClient {
	public static void main(String[] args) {
		try {
			Socket client = new Socket("localhost", 9999);// 定义连接的主机信息
			Scanner scanner = new Scanner(client.getInputStream());// 获取服务器端的响应数据
			scanner.useDelimiter("\n");
			PrintStream out = new PrintStream(client.getOutputStream()); // 向服务器端发送信息内容
 			
			boolean flag = true;
			while (flag) {
				String inputData = InputUtil.getString("请输入要发送的内容：").trim();
				out.println(inputData); // 把数据发送到服务器端上
				if (scanner.hasNext()) {
					String str = scanner.next().trim();
					System.out.println(str);
				}
				if ("byebye".equalsIgnoreCase(inputData)) {
					flag = false;
				}
			}
			
 	        client.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Server{
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(9999);
		Socket s = ss.accept();
 		BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
 		String str ;
 		if((str=bf.readLine()) != null) {
 	 		System.out.println(str);
 		}
 		
 		s.shutdownInput();
 		System.out.println("jieshula");
 		
	}
}

class Client{
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost",9999); 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("我在测试接口");
		bw.flush();
		socket.shutdownOutput();
 
		
	}
}
