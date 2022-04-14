package com.MutilThread;

public class ThreadCommunication2 {
	public static void main(String[] args) {
		final DataShare data=new DataShare();
	 
		for(int i = 0;i<3;i++) {
			new Thread(()->{
				data.add();
			}).start();
			
			new Thread(()->{
				data.delete();
			}).start();

		}
		
	}
}
 