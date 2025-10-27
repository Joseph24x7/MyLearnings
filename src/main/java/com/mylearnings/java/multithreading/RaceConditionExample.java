package com.mylearnings.java.multithreading;

import lombok.Getter;
import lombok.Setter;

public class RaceConditionExample {

    public static void main(String... args) {

        BankAccount account = new BankAccount();

        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("balance during deposit: " + account.getBalance());
                account.deposit(10);
            }
        });

        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("balance during withdrawal: " + account.getBalance());
                account.withdraw(10);
            }
        });

        depositThread.start();
        withdrawThread.start();

        try {
            depositThread.join(); // joining both threads will not necessarily prevent a race condition, as it will only ensure that the
            withdrawThread.join(); // main thread waits for depositThread and withdrawThread to complete their execution.
        } catch (InterruptedException ignored) {
        }
        System.out.println("Final Balance: " + account.getBalance());
    }

}


@Getter
@Setter
class BankAccount {
    private double balance;

    //To avoid race conditions, you can use synchronization mechanisms like locks or synchronized blocks to ensure that only
    // one thread can access the critical section (in this case, the deposit and withdraw methods) at a time.
    public void deposit(double amount) {
        synchronized (this) {
            balance = balance + amount;
        }
    }

    public void withdraw(double amount) {
        synchronized (this) {
            balance = balance - amount;
        }
    }
}
