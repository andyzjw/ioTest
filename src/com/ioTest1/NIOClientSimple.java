package com.ioTest1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClientSimple {
	public static void main(String[] args) throws IOException {
		SocketChannel sc =SocketChannel.open(new InetSocketAddress("localhost",8888));
		sc.configureBlocking(false);
		ByteBuffer bb = ByteBuffer.allocate(50);
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String str = scanner.nextLine();
			bb.put(str.getBytes(),0,str.getBytes().length);
			bb.flip();
			sc.write(bb); // write时，会从当前position开始读取
			bb.clear();
		}
		sc.close();
	}
}
