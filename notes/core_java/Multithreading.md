# Java Multithreading Concepts

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

## 8. Wait vs sleep vs notify vs join

- sleep is a method provided by the Thread class, it makes the current running thread to wait for certain amount of time.
- wait is used when a thread wants to hold its execution until another thread to notify it.
- notify is used to wake up a waiting thread when a certain condition is met or a shared resource is ready.
- join is used to make the current thread wait until another thread completes its execution.

---

## 9. How deadlock occurs, when thread deadlock will occur and how to solve it:

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

## 10. Synchronized block vs synchronized method:

### Synchronized Method:
- When a method is declared as synchronized, it means that the entire method is synchronized. 
- Only one thread can execute the synchronized method at a time, even if there are multiple methods within the same class that are synchronized.

### Synchronized Block:
- With synchronized blocks, you can specify a specific block of code to be synchronized. 
- Multiple threads can execute other non-synchronized code within the same class concurrently, as long as they don't enter the synchronized block simultaneously.

---

## 11. What is the purpose of the "join" method in Java threads?

If we create a new thread t1, and if we want the main thread to wait for t1 thread to complete its execution then we can go for t1.join.
In some scenarios the result of t1 thread may be required for main thread to proceed, in such cases we can go for join() method.

---

## 12. Explain the terms "synchronized" and "volatile" in the context of Java multithreading:

### Volatile:
- Purpose: Its primary purpose is to ensure visibility of changes across threads.
- Visibility: volatile guarantees that changes made to the volatile variable by one thread are immediately visible to other threads.

### Synchronous:
- sequential order. Each task must wait for the previous one to complete before it starts.
- In a single-threaded program, operations are inherently synchronous because there is only one thread of execution, and tasks are performed sequentially.

---

## 13. Parallel Stream and ForkJoinPool: 

- Parallel Stream:
- Parallel streams in Java allow you to process collections of data in parallel, utilizing multiple threads to improve performance for large datasets.
- It uses the ForkJoinPool internally to manage threads and divide the workload.
```java
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    int sum = numbers.parallelStream()
                     .mapToInt(Integer::intValue)
                     .sum();
```
- ForkJoinPool Creation: The ForkJoinPool automatically manages a pool of worker threads and divides the workload among them to execute tasks concurrently.
- It uses CPU cores efficiently by dynamically adjusting the number of threads based on the available resources.
- Ideal for smaller tasks that can be broken down into smaller subtasks and executed in parallel.

---

## 14. newCachedThreadPool() vs newFixedThreadPool() in Executor Service. What is the best advisable approach for I/O intensive tasks?

ExecutorService executor = Executors.newCachedThreadPool();
- This method creates an executor with an expandable thread pool that can dynamically adjust its size based on the number of incoming tasks.
- If a thread is not used for a specified amount of time (60 seconds in the case of the default implementation), it will be terminated and removed from the pool.
- It is suitable for scenarios where the number of tasks is not known in advance, and you want to reuse threads efficiently.
- Not Advised for CPU intensive tasks as it can create too many threads leading to resource exhaustion.

ExecutorService executor = Executors.newFixedThreadPool(poolSize - 10);
- This method creates an executor with a fixed-size thread pool where the number of threads is specified during pool creation.
- Tasks are queued if all threads are busy. If a thread encounters an exception and terminates, a new one will be created to replace it.
- It is suitable for scenarios where you want to limit the number of concurrently executing tasks.
- Not Advised for I/O intensive tasks as threads may remain idle waiting for I/O operations to complete.

Advised Replacement for I/O intensive tasks:
- We can make use of ThreadPoolExecutor with a custom configuration.
```java
    int corePoolSize = 10;
    int maximumPoolSize = 50;
    long keepAliveTime = 60L;
    TimeUnit unit = TimeUnit.SECONDS;
    BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();

    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        corePoolSize,
        maximumPoolSize,
        keepAliveTime,
        unit,
        workQueue
    );
```
- This configuration allows for a flexible thread pool that can grow and shrink based on the workload, making it suitable for I/O-intensive tasks.

---

## 15. if a method is synchronized and static, how will java behave?

- If a method is synchronized and static, it means that the lock associated with the method is at the class level. Only one thread can execute any synchronized static method of the class at a time.
- If a method is synchronized and not static, it means that the lock associated with the method is at the instance level. Only one thread can execute any synchronized non-static method on a particular instance of the class at a time.

---

## 16. Difference between completableFuture & Future? which is preferred?

### Using CompletableFuture:
- CompletableFuture is used when you need to perform asynchronous tasks that may return results or be composed together. You can easily combine multiple CompletableFutures to create more complex asynchronous workflows using methods like thenCompose, thenApply, thenAccept, and so on.
- CompletableFuture allows you to attach callbacks or actions that get executed when the computation is complete. This enables you to define behavior for success, failure, or completion without blocking the thread unnecessarily.
- CompletableFuture has built-in support for exceptionally handling errors. You can use the exceptionally method to specify a fallback value or to recover from an exception.

### Using Future:
- The Future interface lacks built-in support for easy composition of multiple asynchronous operations.
- Future relies on the get() method to retrieve the result of a computation, and it blocks until the result is available. This can make it challenging to perform asynchronous operations without blocking the thread.
- Exception handling with Future is often done by catching exceptions during the execution of the submitted task or by checking for exceptions using the get() method.

---

## 17. Lifecycle of a Thread:

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

