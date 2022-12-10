package com.mylearnings.java.multithreading;

public class ThreadDemo extends Thread{
	
	@Override
	public void run() {
		
		for(int i=0;i<1000;i++) {
			
			//Thread.sleep(5000L); //Sleep() causes the currently executing thread to sleep (temporarily cease execution).
			
			//Low priority or Long Running threads
			//Thread.yield(); //Yield() causes the currently executing thread object to temporarily pause and allow other threads to execute.
			
			synchronized(Thread.currentThread()) {
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("This is "+Thread.currentThread().getName()+" : "+i);
			
		}
		
	}

}
