package com.MutilThread;

public class ThreadCommunication {
	public static void main(String[] args) {
		DataShare data=new DataShare();
		Runnable runable =new Add(data);
		Runnable runable2 =new Delete(data);
		for(int i = 0;i<3;i++) {
			new Thread(runable).start();
			new Thread(runable2).start();

		}
		
	}
}

class DataShare {
	private int i = 0;
	public synchronized void add() {
		this.i++;
		System.out.println(Thread.currentThread().getName()+"add  "+i);

	}
	
	public synchronized void delete() {
		this.i--;
		System.out.println(Thread.currentThread().getName()+"delete  "+i);
	}
	
	public int getData() {
		return this.i;
	}
}

class Add implements Runnable {
	private DataShare data;
	public Add (DataShare data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.data.add();
	}
}

class Delete implements Runnable {
	private DataShare data;
	public Delete (DataShare data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.data.delete();
	}
}