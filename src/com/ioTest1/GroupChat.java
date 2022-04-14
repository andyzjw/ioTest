package com.ioTest1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChat {
    private ServerSocketChannel ssc;
    private Selector selector;

    public GroupChat() {
        try {
            // 获取通道
            ssc = ServerSocketChannel.open();
            // 非阻塞
            ssc.configureBlocking(false);
            // 绑定端口
            ssc.bind(new InetSocketAddress(9999));
            // 获取选择器
            selector = Selector.open();
            // 注册
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void listen() throws IOException {
        while (selector.select() > 0) {
            // 获取事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                if (sk.isAcceptable()) {
                    // 获取客户端连接
                    getClientRegister();
                } else if (sk.isReadable()) {
                    // 读取客户端信息，并发送到其他客户端
                    readAndSendOther(sk);
                }

                iterator.remove();
            }

        }
    }

    private void readAndSendOther(SelectionKey sk) {
        SocketChannel channel = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        String time = sdf.format(date);
        try {
            // 获取当前选择器上的“读就绪”通道
            channel = (SocketChannel) sk.channel();
            // 读取数据准备
            ByteBuffer bf = ByteBuffer.allocate(1024);
            int len = 0;
            while ((len = channel.read(bf)) > 0) {

                bf.flip();
                String msg = new String(bf.array(), 0, len);
                System.out.println("服务器接收到客户端的信息：" + msg + "   " + channel.getRemoteAddress());
                msg = time+ " " + channel.getRemoteAddress() + ":" + msg;
                sendMsgToClient(msg, channel);
                bf.clear();
            }

        } catch (Exception e) {
            // 如果当前客户端离线
            try {
                sk.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }


    }

    private void sendMsgToClient(String msg, SocketChannel channel) throws IOException {
        System.out.println("开始转发消息给客户端，处理线程:" + Thread.currentThread().getName());
        for (SelectionKey sk : selector.keys()) {
            Channel channel1 = sk.channel();
            if (channel1 instanceof SocketChannel && channel1 != channel) {
                ByteBuffer bf = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                ((SocketChannel) channel1).write(bf);
            }
        }
    }

    private void getClientRegister() {
        try {
            SocketChannel accept = ssc.accept();
            // 配置非阻塞
            accept.configureBlocking(false);
            //
            accept.register(selector, SelectionKey.OP_READ);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        GroupChat gc = new GroupChat();
        gc.listen();

    }
}

class GroupChatClient {
    private SocketChannel sc;
    private Selector selector;

    public GroupChatClient() {
        try {
            sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
            sc.configureBlocking(false);
            selector = Selector.open();
            sc.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            if ("quit".trim().equalsIgnoreCase(str)) {
                sc.close();
                break;
            }
            byteBuffer.put(str.getBytes(StandardCharsets.UTF_8), 0, str.getBytes().length);
            byteBuffer.flip();
//            System.out.println(byteBuffer.position());
//            System.out.println(byteBuffer.limit());
//            System.out.println("===================");
            try {
                this.sc.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println(byteBuffer.position());
//            System.out.println(byteBuffer.limit());
            byteBuffer.clear();
        }
    }

    private void readInfo() throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        while (this.selector.select() > 0) {
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
//                    socketChannel.configureBlocking(false);
                    socketChannel.read(bb);

                    bb.flip();

                    System.out.println(new String(bb.array(), 0, bb.remaining()));
                }
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        GroupChatClient gcc = new GroupChatClient();

        new Thread(() -> {
            try {
                while (true) {
                    gcc.readInfo();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        gcc.sendMsg();
    }


}