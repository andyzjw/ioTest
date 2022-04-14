package com.MutilThread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 使用cyclicBarrier await方法的线程，全部执行后， 线程内后续方法统一放开，继续执行。 
 * 主线程不会堵塞，会直接执行多线程后的代码
 * cyclicbarrier 的数量 跟多线程数量要一致，或者是其整数倍
 * @author andy
 *
 */

public class CyclicBarrierTest {
		public static void main(String[] args) {
			CyclicBarrier cyclicBarrier = new CyclicBarrier(6);
			
			for(int i = 0 ;i <12;i++) {
				new Thread(()->{
					System.out.println(Thread.currentThread().getName()+"  ");
					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						cyclicBarrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"  结束了");
 
				}).start();
			}
			
			System.out.println("主线程结束啦");
		}
}
