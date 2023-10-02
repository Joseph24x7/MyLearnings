package com.mylearnings.java.multithreading;

public class DeadlockExample {

    //resource1 and resource2 represent two resources or locks that two threads, thread1 and thread2, need to access.
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) {

        // thread1 first acquires resource1 and then attempts to acquire resource2
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: Holding resource 1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 1: Waiting for resource 2...");
                synchronized (resource2) {
                    System.out.println("Thread 1: Acquired resource 2.");
                }
            }
        });

        // while thread2 first acquires resource2 and then attempts to acquire resource1
        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: Holding resource 2...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 2: Waiting for resource 1...");
                synchronized (resource1) {
                    System.out.println("Thread 2: Acquired resource 1.");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}

class DeadlockSolution {
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: Holding resource 1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 1: Waiting for resource 2...");
                synchronized (resource2) {
                    System.out.println("Thread 1: Acquired resource 2.");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource1) { // Acquire resource1 first
                System.out.println("Thread 2: Holding resource 1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 2: Waiting for resource 2...");
                synchronized (resource2) {
                    System.out.println("Thread 2: Acquired resource 2.");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}

class WaitDeadlockExample {
    public static void main(String[] args) {
        final Object lock = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // Thread 1 waits for a notification
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                // Thread 2 never notifies Thread 1
            }
        });

        thread1.start();
        thread2.start();
    }
}

// The code you provided intentionally creates a deadlock scenario where thread1 waits for threadA to complete,
// and thread2 waits for threadB to complete.
// This deadlock occurs because each thread is waiting for another thread to finish, forming a circular dependency.
class JoinDeadlockExample {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                Thread threadA = new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                threadA.start();
                threadA.join(); // Thread 1 waits for ThreadA

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread threadB = new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                threadB.start();
                threadB.join(); // Thread 2 waits for ThreadB

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }
}

class AvoidJoinDeadlockExample {
    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread1 = new Thread(() -> {
            threadA.start();
            try {
                threadA.join(); // Thread 1 waits for ThreadA
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            threadB.start();
            try {
                threadB.join(); // Thread 2 waits for ThreadB
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }
}
