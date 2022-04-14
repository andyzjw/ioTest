package com.ioTest1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 
public class NIOServerSimple {
	public static void main(String[] args) throws IOException {
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.bind(new InetSocketAddress(8888));
		Selector selector = Selector.open();
		channel.register(selector,SelectionKey.OP_ACCEPT);
		
		while(selector.select() > 0) {
			System.out.println("=======服务端当前线程"+Thread.currentThread().getName());
			Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
			
			while(iterator.hasNext()) {
				SelectionKey sk= iterator.next();
				if(sk.isAcceptable()) {
					SocketChannel clientChannel = channel.accept();
					clientChannel.configureBlocking(false);
					clientChannel.register(selector, SelectionKey.OP_READ);
				}else if(sk.isReadable()) {
					SocketChannel clientChannel=(SocketChannel)sk.channel();
					 ByteBuffer bb = ByteBuffer.allocate(50);
					 int len = 0;
					 while((len = clientChannel.read(bb))>0) {
//						 bb.flip(); // 注释 如果用bb.remaining()来代表长度，那么需要flip
						 
						 String str = new String(bb.array(),0,len);
						 System.out.println(str);
						 bb.clear();
					 }

				}
				
				iterator.remove();
				
			}
		}
		
	}
}
 