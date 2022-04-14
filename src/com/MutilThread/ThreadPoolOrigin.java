package com.MutilThread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue（公平、非公平） LinkedBlockingQueue（两个独立锁提高并发）
 * PriorityBlockingQueue（compareTo 排序实现优先） DelayQueue（缓存失效、定时任务 ）
 * SynchronousQueue（不存储数据、可用于传递数据） LinkedTransferQueue LinkedBlockingDeque
 * 
 * @author andy
 *
 */
public class ThreadPoolOrigin {
	public static void main(String[] args) {
//		ExecutorService executor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
//				new LinkedBlockingQueue<Runnable>());
		
//		ExecutorService executor = Executors.newCachedThreadPool();
//		ExecutorService executor = Executors.newFixedThreadPool(3);
//		ExecutorService executor = Executors.newSingleThreadExecutor();
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

		
		
		
		for (int i = 0; i < 3; i++) {
//			executor.schedule(()->{
//					System.out.println(Thread.currentThread().getName()+"  ");
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println(Thread.currentThread().getName()+" 执行完了 ");
//				
//			},1,TimeUnit.SECONDS);
//			
			executor.scheduleAtFixedRate(()->{
				System.out.println(Thread.currentThread().getName()+"  ");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+" 执行完了 ");
			
		},2,1,TimeUnit.SECONDS);
		}
		
		
		
//		for (int i = 0; i < 10; i++) {
//			executor.submit(()->{
//					System.out.println(Thread.currentThread().getName()+"  ");
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println(Thread.currentThread().getName()+" 执行完了 ");
//				
//			});
//
//		}
		
		
		
		
		executor.shutdown();

	}
}
