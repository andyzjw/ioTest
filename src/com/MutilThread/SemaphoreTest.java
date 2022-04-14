package com.MutilThread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

/**
 * 可以控制一定时间内操作对象的数量
 * 可以控制同时访问的线程个数
 * @author andy
 *
 */
public class SemaphoreTest {
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(3);
		
		for(int i = 0 ;i <5;i++) {
			new Thread(()->{
				try {
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName()+"  ");
				 
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				semaphore.release();
				System.out.println(Thread.currentThread().getName()+"  结束了");

			}).start();
		}
		
		System.out.println("主线程结束啦");
	}
}
