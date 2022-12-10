package com.mylearnings.java.multithreading;

public class MainClass {
	
	public static void main(String[] args) throws InterruptedException{
		
		//Types of thread -> daemon, user threads -> main , child 
		//demo.setDaemon(true); // Least priority - stops when main / child thread ends.
		//Thread.sleep(10000L);
		//demo.run(); -> runs as a normal method rather than a new thread
		//If we override start method , run method will never be called.
		// We can start() thread only once, otherwise it will throw illegal state exception.
		//demo.join(); -> If another thread wants to wait until this thread completion then we should go for join()
		
		ThreadDemo demo=new ThreadDemo();
		demo.setName("ThreadClass Child Thread");
		demo.start();
		
		
		RunnableDemo runnableDemo=new RunnableDemo();
		Thread thread=new Thread(runnableDemo);
		thread.setName("Runnable Thread");
		thread.start();
	}

}
