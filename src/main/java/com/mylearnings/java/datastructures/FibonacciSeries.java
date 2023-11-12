package com.mylearnings.java.datastructures;

public class FibonacciSeries {

    public static void main(String[] args) {

        // Efficient way
        fibonacciSeries(10);

        // Using Recursion
        for (int i = 0; i < 10; i++) {
            System.out.println(fibonacci(i));
        }
    }

    static void fibonacciSeries(int n) {
        int a=0, b=1, c;
        System.out.println(a);
        System.out.println(b);
        for(int i=2;i<n;i++) {
            c = a+b;
            System.out.println(c);
            a=b;
            b=c;
        }
    }

    static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

}
