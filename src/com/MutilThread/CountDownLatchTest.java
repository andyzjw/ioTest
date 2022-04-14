package com.MutilThread;

import java.util.concurrent.CountDownLatch;

/**
 * 相关数量统计完后，后续主进程代码可以继续进行
 * countDownLatch 中的数量如果不够则一直会等
 * @author andy
 *
 */
public class CountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		// 线程计数器
		
		CountDownLatch latch = new CountDownLatch(6);
		
		for(int i = 0 ;i <6;i++) {
			new Thread(()->{
				System.out.println(Thread.currentThread().getName()+"  ");
				System.out.println(Thread.currentThread().getName()+"  结束了");
				latch.countDown();

			}).start();
		}
		
		latch.await();
		
		System.out.println("结束啦");
		
	}
}
