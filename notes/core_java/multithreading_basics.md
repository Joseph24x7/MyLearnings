# Java Multithreading Basics

## 1. What is multithreading, and how does it differ from multiprocessing?


### Multiprocessing:
- Multiple independent processes run concurrently on a multicore or multiprocessor system.

### Multithreading:
- Single process is divided into multiple threads, each of which can execute independently and concurrently.

---


## 2. What is a race condition in multithreading, and how can it be avoided?


A race condition is a situation that occurs in multithreading when two or more threads access shared data or resources concurrently, and the final outcome depends on the timing or order of execution. Race conditions can lead to unexpected and undesirable results, including data corruption, program crashes, or incorrect behavior in a multithreaded application.
Example: Withdrawal of money.

---


## 3. Ways to create Thread and Advantages in each:


### Extending Thread Class (ThreadDemo):
- This approach is useful when you need to create a simple, standalone thread. 
- It's often used for tasks that can run independently.

### Implementing Runnable Interface (RunnableDemo):
- This approach is suitable when you want to separate the task logic from the thread management. 
- It's often used when you have multiple tasks that can share the same thread.

### Using Lambda Expressions (lambdaThread):
- Same as Java 8

### Using CompletableFuture:
- CompletableFuture is used when you need to perform asynchronous tasks that may return results or be composed together. 
- It's often used in scenarios where you want to create a chain of asynchronous operations.
- Suitable for building complex asynchronous workflows.

### Using Callable with ExecutorService (callableTask):
- Callable and ExecutorService are used when you need to execute a task that returns a result or throws an exception. 
- It's often used for tasks that require more control over thread management, resource allocation, and result handling.
- Supports execution of multiple tasks concurrently using a thread pool.

---


## 4. Synchronous vs Thread Safe:


### Synchronous:
- sequential order. Each task must wait for the previous one to complete before it starts.
- In a single-threaded program, operations are inherently synchronous because there is only one thread of execution, and tasks are performed sequentially.

### Thread-Safe:
- data structure is designed to be used safely by multiple threads concurrently without causing data corruption or unexpected behavior.
- Thread-safe code ensures that shared resources are accessed and modified in a way that prevents race conditions, data races, and other multithreading-related issues.

example for synchronous and thread safe: Vector, HashTable
example for asynchronous and thread safe: CopyOnWriteArrayList, ConcurrentHashMap.

---


## 5. Atomic Integer:


- An AtomicInteger is a part of Java's java.util.concurrent.atomic package
- Thread-Safe Counters: Maintain a counter that multiple threads can increment and decrement concurrently without causing data corruption or race conditions.

```java
    AtomicInteger counter = new AtomicInteger(0);
    counter.incrementAndGet(); 
    counter.decrementAndGet();
```

- Replacing Synchronization: Instead of using locks or synchronized blocks to protect critical sections of code, you can often use AtomicInteger to achieve the same results with better performance.
- Internally it uses Retries and Compare-And-Swap (CAS) operations to ensure atomicity without the need for traditional locking mechanisms.
---


## 6. If we have volatile then why we need to go for Atomic Integer?


- volatile gives visibility, AtomicInteger gives atomicity + visibility.
- volatile is safe only when - Single thread writes, Multiple threads reads. So race condition can still occur with volatile.
- But AtomicInteger provides atomic operations that ensure that multiple threads can safely modify the integer value without causing race conditions.

- Race condition with volatile:
```java
    volatile int counter = 0;

    public void increment() {
        counter++; // Not atomic
    }
```
- In the above example, multiple threads incrementing the counter simultaneously can lead to lost updates and inconsistent results.
- To ensure atomicity, we can use AtomicInteger:
```java
    AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet(); // Atomic operation
    }
```

---


## 7. Wait vs sleep vs notify vs join


- sleep is a method provided by the Thread class, it makes the current running thread to wait for certain amount of time.
- wait is used when a thread wants to hold its execution until another thread to notify it.
- notify is used to wake up a waiting thread when a certain condition is met or a shared resource is ready.
- join is used to make the current thread wait until another thread completes its execution.

---


## 8. How deadlock occurs, when thread deadlock will occur and how to solve it:


- Deadlock occurs in a multithreaded program when two or more threads are unable to proceed with their execution because they are each waiting for the other(s) to release a resource or lock.
- This results in a standstill where no thread can make progress, and the program becomes unresponsive. 

### types:
- circular deadlock
- resource wait deadlock

### Coding Example for Circular Deadlock:
```java
public class DeadlockExample {
    public static void main(String[] args) {
        final Object resource1 = "Resource1";
        final Object resource2 = "Resource2";
        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: Holding Resource 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 1: Waiting for Resource 2...");
                synchronized (resource2) {
                    System.out.println("Thread 1: Acquired Resource 2!");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: Holding Resource 2...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 2: Waiting for Resource 1...");
                synchronized (resource1) {
                    System.out.println("Thread 2: Acquired Resource 1!");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
```

### Solutions to Avoid Deadlock:
- Lock Ordering: Establish a consistent order in which locks are acquired by threads.
- Lock Timeout: Implement timeouts when trying to acquire locks.
- Deadlock Detection: Use algorithms to detect deadlocks and take corrective actions.

---


## 9. Synchronized block vs synchronized method:


### Synchronized Method:
- When a method is declared as synchronized, it means that the entire method is synchronized. 
- Only one thread can execute the synchronized method at a time.

### Synchronized Block:
- With synchronized blocks, you can specify a specific block of code to be synchronized. 
- Multiple threads can execute other non-synchronized code within the same class concurrently, as long as they don't enter the synchronized block simultaneously.

### Singleton class is one good example of going to synchronized block instead of synchronized method to improve performance.
---


## 10. if a method is synchronized and static, how will java behave?


### If a method is synchronized and static:
    - Java acquires a lock on the Class object (ClassName.class).
    - Only one thread across all instances of that class can execute any static synchronized method at a time.

### If a method is synchronized and not static:
    - Java acquires a lock on the object instance (this).
    - Only one thread per instance can execute synchronized instance methods at a time.

---


## 11. Lifecycle of a Thread:


1. New: 
- Thread t1 = new Thread(() -> //task); //It is in this state after the instantiation of the thread object.
- A thread is in the "New" state when it has been created but not yet started.

2. Runnable:
- t1.start();
- A thread enters the "Runnable" state when the start() method is called on the thread object.
- In this state, the thread is ready to run, but the operating system (or the thread scheduler) has not yet selected it to be the running thread.

3. Blocked:
- A thread transitions to the "Blocked" state when it is temporarily inactive.
- This can occur when a thread is waiting for a resource (e.g., waiting for I/O operations, waiting to acquire a lock) or when it's in the sleep() state.

4. Waiting:
- Object.wait(), Thread.join(), or LockSupport.park()
- A thread enters the "Waiting" state when it is waiting for another thread to perform a particular action.

5. Timed Waiting:
- Thread.sleep() or Object.wait(timeout).
- Similar to the "Waiting" state, but with a specified time limit.

6. Terminated:
- A thread enters the "Terminated" state when it has completed its execution or if an uncaught exception terminates it.
- Once a thread is terminated, it cannot return to any other state.

---


## 12. How to handle counters in multithreaded application?

- Use AtomicInteger for thread-safe counters.
```java
    AtomicInteger counter = new AtomicInteger(0);
    counter.incrementAndGet();
    counter.decrementAndGet();
```
- Use LongAdder for high contention scenarios.
```java
    LongAdder counter = new LongAdder();
    counter.increment();
    counter.decrement();
```
- Use ReentrantLock for custom locking mechanisms.
```java
    ReentrantLock lock = new ReentrantLock();
    lock.lock();
    try {
        // update counter
    } finally {
        lock.unlock();
    }
```
### When to use these different counters:
- Use AtomicInteger for simple counters with low contention.
- Use LongAdder for high contention scenarios where multiple threads frequently update the counter.
- Use ReentrantLock for complex scenarios where you need more control over locking and unlocking.

---


## 13. How `synchronized` Works Internally in Java


At the JVM level, `synchronized` is implemented using **Monitor entry** and **Monitor exit** instructions (`monitorenter` and `monitorexit` in bytecode).

### Internal Mechanics:
1. **Object Monitor:** Every object in Java is associated with a Monitor (an internal lock object).
2. **Object Header (Mark Word):** The header of every Java object contains a "Mark Word" (32 or 64 bits) which stores the lock state (unlocked, biased, lightweight lock, heavyweight lock, or GC metadata).
3. **Lock Inflation (JVM Optimization):**
   - **Biased Lock:** The JVM biases the lock towards the first thread that acquires it. If no other thread tries to acquire the lock, the thread can enter/exit the synchronized block with zero synchronization overhead.
   - **Lightweight Lock:** If another thread tries to acquire a biased lock, the lock inflates to a lightweight lock. The thread uses **CAS (Compare-And-Swap)** spin-locking to acquire the lock. No OS-level thread blocking happens.
   - **Heavyweight Lock:** If contention is high (spinning fails multiple times), the lock inflates to a heavyweight lock. The JVM calls OS-level synchronization primitives, blocking/suspending competing threads and placing them in an entry queue until the lock is released.

---


## 14. Volatile Keyword: Visibility & Reordering


The `volatile` keyword in Java is used to mark a variable as stored in main memory, rather than in thread-specific CPU caches.

### Main Uses:
1. **Thread Visibility:** Ensures that writes to the variable by one thread are immediately visible to all other threads. Without `volatile`, changes made by Thread A might only exist in its local CPU cache, and Thread B might read a stale value from its own cache.
2. **Prevents Instruction Reordering:** Creates a memory barrier preventing the JVM/compiler from reordering instructions around the volatile read/write (crucial for Double-Checked Locking).

*Note:* `volatile` does NOT provide **atomicity** (it doesn't lock). For compound operations like `i++` (which is read-modify-write), you still need synchronization or `AtomicInteger`.

---


