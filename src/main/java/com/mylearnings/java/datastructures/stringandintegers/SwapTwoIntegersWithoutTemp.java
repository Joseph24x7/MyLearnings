package com.mylearnings.java.datastructures.stringandintegers;

public class SwapTwoIntegersWithoutTemp {
	
	public static void main(String[] args) {
		
		int a=10,b=20;
		
		a=a+b;
		b=a-b;
		a=a-b;


		System.out.println(a);
		System.out.println(b);
		
		System.out.println(factorial(5L));
		
	}
	
	static Long factorial(Long n) {
		
		if(n==0) {
			return 1L;
		}
		
		return n*factorial(n-1);
	}

}
