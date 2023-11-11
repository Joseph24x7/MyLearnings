package com.mylearnings.java.gcmemory;

public class GCDemo {
	
	GCDemo demo3;
	
	//Island of Isolation
	public static void main(String[] args) {
		
		GCDemo demo1=new GCDemo();
		GCDemo demo2=new GCDemo();
		
		demo1.demo3=demo2;
		demo2.demo3=demo1;
		
		demo1=demo2=null;
		
		Runtime.getRuntime().gc();
		
		System.out.println("Main");
		
	}
	
	@Override
	protected void finalize() throws Throwable { 
		System.out.println("Finalize");
	}

}
