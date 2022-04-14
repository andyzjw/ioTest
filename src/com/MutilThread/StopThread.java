package com.MutilThread;

public class StopThread {
	
	/***
	 * 1.运行完run然后停止
	 * 2. stop停止
	 */
	
	
	/**
	 * 3。通过interrupt打断是的线程，isInterrupted成为true 获取标志 此方法为Thread线程的
	 * 
	 */
	public void Interrupt() {
		Thread thread1 = new Thread(() -> {
				System.out.println(Thread.currentThread().isInterrupted());
				
				System.out.println(Thread.currentThread().isInterrupted());
		});
		thread1.start();
		
		System.out.println("12321");
		thread1.interrupt();
	}
}
