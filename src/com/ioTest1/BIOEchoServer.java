package com.ioTest1;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOEchoServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		ExecutorService es = null;
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("服务器端已经启动，监听的端口为：" + 9999);

			boolean flag = true;
			  es = Executors.newFixedThreadPool(2);
			
			while(flag) {
				Socket client = serverSocket.accept() ;
				es.submit(new EchoClientHandler(client));
				 
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				es.shutdown();
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static class EchoClientHandler implements Runnable {
		private Socket socket;
		private Scanner scanner;
		private PrintStream out;
		private boolean flag = true;

		private EchoClientHandler(Socket client) {
			this.socket = client;
			try {
				this.scanner = new Scanner(this.socket.getInputStream());
				this.scanner.useDelimiter("\n");
				this.out = new PrintStream(this.socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
 			while (flag) {
				System.out.println("============当前线程"+Thread.currentThread().getName());
				if (this.scanner.hasNext()) {
					String val = this.scanner.next().trim();
					System.err.println("{服务器端}" + val);
					if ("byebye".equalsIgnoreCase(val)) {
						out.println("ByeByeByte...");
						flag = false;
					} else {
						out.println("【ECHO】" + val);
						
					}

				}
			}

			this.scanner.close();
			this.out.close();
			try {
				this.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
