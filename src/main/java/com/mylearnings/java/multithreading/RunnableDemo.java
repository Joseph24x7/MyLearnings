package com.mylearnings.java.multithreading;

public class RunnableDemo implements Runnable {
	
	ThreadDemo threadDemo=new ThreadDemo();

	@Override
	public void run() {
		
		for(int i=0;i<1000;i++) {
			System.out.println("This is "+Thread.currentThread().getName()+" : "+i);
		}
		
	}

}
