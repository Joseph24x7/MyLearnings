package com.mylearnings.java.multithreading.problems;

public class PrintLettersNumbers {

    private static final Object lock = new Object();
    private static boolean printLetter = true;

    public static void main(String[] args) {
        Thread threadLetters = new Thread(PrintLettersNumbers::printLetters);
        Thread threadNumbers = new Thread(PrintLettersNumbers::printNumbers);

        // Start both threads
        threadLetters.start();
        threadNumbers.start();
    }

    // Function to print letters from 'a' to 'z'
    private static void printLetters() {
        for (char letter = 'a'; letter <= 'z'; letter++) {
            synchronized (lock) {
                while (!printLetter) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(letter);

                printLetter = false;
                lock.notify();
            }
        }
    }

    // Function to print numbers from 1 to 26
    private static void printNumbers() {
        for (int number = 1; number <= 26; number++) {
            synchronized (lock) {
                while (printLetter) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (number == 26) {
                    System.out.print(number);
                } else
                    System.out.print(number + ",");
                printLetter = true;
                lock.notify();
            }
        }
    }
}
