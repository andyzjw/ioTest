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
			Socket client = new Socket("localhost", 9999);// �������ӵ�������Ϣ
			Scanner scanner = new Scanner(client.getInputStream());// ��ȡ�������˵���Ӧ����
			scanner.useDelimiter("\n");
			PrintStream out = new PrintStream(client.getOutputStream()); // ��������˷�����Ϣ����
 			
			boolean flag = true;
			while (flag) {
				String inputData = InputUtil.getString("������Ҫ���͵����ݣ�").trim();
				out.println(inputData); // �����ݷ��͵�����������
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
		bw.write("���ڲ��Խӿ�");
		bw.flush();
		socket.shutdownOutput();
 
		
	}
}
