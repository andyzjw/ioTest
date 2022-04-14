package com.MutilThread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MyCallable   {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ArrayList<FutureTask<Student>> list = new ArrayList<>();
	     ExecutorService executorService= Executors.newFixedThreadPool(2);
		FutureTask<Student> call = null;
		System.out.println("main:"+System.currentTimeMillis());
		for(int i = 0;i<4;i++) {
			list.add(call = new FutureTask<Student>(new MyCall(i)));
			Future f = executorService.submit(call);  // 这里获得future 为空
		}
	
		System.out.println("main:"+System.currentTimeMillis());
		 
		for(FutureTask<Student> task:list) {
			System.out.println(task.get().userName);
		}
		System.out.println("==================");
 		
		for(int i = 0;i<4;i++) {
 			Future<Student> f = executorService.submit( new MyCall(i));
 			System.out.println(f.get().userName);
 			 
		}
		
//		new Thread(new FutureTask<>(new MyCall(0))).start();

	}

}

class MyCall implements Callable<Student>{
	private int i;
	public MyCall(int i) {
		this.i =i;
	}

	@Override
	public Student call() throws Exception {
		System.out.println(Thread.currentThread().getName()+" "+this.i);
		Long start =System.currentTimeMillis();
		System.out.println(start);
		TimeUnit.SECONDS.sleep(1);
		Long end =System.currentTimeMillis();
		System.out.println(end);
		Student stu=new Student();
		stu.userName="liyang"+i;
		return stu;
	}
	
}
class Student{
	public  String userName;
	
}