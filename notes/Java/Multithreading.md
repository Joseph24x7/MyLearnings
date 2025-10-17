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

---

## 6. Callable vs Runnable:

### Runnable: 
- You typically use Runnable when you want to perform a task in the background without expecting a result or when you want to encapsulate code to run asynchronously.
- It does not return any result directly
- It cannot throw checked exceptions. Any checked exceptions thrown within a Runnable must be caught within the run method.

### Callable: 
- You typically use Callable when you need to execute a task concurrently and receive a result or when you want to handle exceptions more flexibly.
- It allows a task to return a value (of a specified type) 
- It allows to throw checked exceptions, which can be captured and handled.

---

## 7. Volatile vs Atomic Integer:

### AtomicInteger:
- Purpose: AtomicInteger is primarily used for performing atomic operations on integer variables in a thread-safe manner. 
- Atomic Operations: Ensures multiple threads can safely increment, decrement, compare-and-set, or get-and-set values without the need for explicit synchronization.
- Use Cases: Use AtomicInteger when you need to perform thread-safe, atomic operations on integer variables, such as maintaining counters,etc.,

### Volatile:
- Purpose: Its primary purpose is to ensure visibility of changes across threads.
- Visibility: volatile guarantees that changes made to the volatile variable by one thread are immediately visible to other threads.
- Atomic Operations: Unlike AtomicInteger, volatile does not provide atomic compound operations. It is focused solely on visibility, not atomicity.

In Short, 
- Atomicity ensures that an operation is indivisible, preventing concurrent threads from interfering with each other during the operation. It's critical for maintaining data integrity.
- Visibility ensures that changes made by one thread are immediately seen by other threads, allowing for effective communication and coordination between threads.

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

## 13. Thread pooling concept in parallel streams:

- Thread pooling for parallel streams is managed by the ForkJoinPool, which is a specialized type of thread pool designed for parallelism. 

- ForkJoinPool Creation: The ForkJoinPool automatically manages a pool of worker threads and divides the workload among them to execute tasks concurrently.
- Work Partitioning: This recursive splitting of task partitioning continues until the tasks are small enough to be executed efficiently.
- Task Execution: The worker threads from the ForkJoinPool execute these subtasks concurrently. 
- Joining Results: This may also involve combining results from multiple levels of subtasks after execution is completed.

---

## 14. What is a thread pool, and why is it useful in multithreaded applications?

- helps to reuse threads from a pre-allocated pool, reducing the overhead of thread creation and destruction. 
- helps to control the number of concurrently executing threads to avoid potential performance issues.
- helps with the task queue which allows you to submit tasks for processing, and the thread pool assigns them to available threads.
- Can be configured accordingly to the hardware capabilities which helps in the scalability which allows applications to efficiently utilize available hardware resources.

popular example: ExecutorService 

---

## 15. run() vs start():

- during start() method a new Thread is created and code inside run() method is executed in new Thread.
- if you call run() method directly no new Thread is created and code inside run() will execute on the current Thread.

---

## 16. how to lock a resource in java?
- By using synchronized keyword

---

## 17. if a method is synchronized and static, how will java behave?

- If a method is synchronized and static, it means that the lock associated with the method is at the class level. Only one thread can execute any synchronized static method of the class at a time.
- If a method is synchronized and not static, it means that the lock associated with the method is at the instance level. Only one thread can execute any synchronized non-static method on a particular instance of the class at a time.

---

## 18. Difference between completableFuture & Future? which is preferred?

### Using CompletableFuture:
- CompletableFuture is used when you need to perform asynchronous tasks that may return results or be composed together. You can easily combine multiple CompletableFutures to create more complex asynchronous workflows using methods like thenCompose, thenApply, thenAccept, and so on.
- CompletableFuture allows you to attach callbacks or actions that get executed when the computation is complete. This enables you to define behavior for success, failure, or completion without blocking the thread unnecessarily.
- CompletableFuture has built-in support for exceptionally handling errors. You can use the exceptionally method to specify a fallback value or to recover from an exception.

### Using Future:
- The Future interface lacks built-in support for easy composition of multiple asynchronous operations.
- Future relies on the get() method to retrieve the result of a computation, and it blocks until the result is available. This can make it challenging to perform asynchronous operations without blocking the thread.
- Exception handling with Future is often done by catching exceptions during the execution of the submitted task or by checking for exceptions using the get() method.

---

## 19. newCachedThreadPool() vs newFixedThreadPool() in Executor Service:

ExecutorService executor = Executors.newCachedThreadPool();
- This method creates an executor with an expandable thread pool that can dynamically adjust its size based on the number of incoming tasks.
- If a thread is not used for a specified amount of time (60 seconds in the case of the default implementation), it will be terminated and removed from the pool.
- It is suitable for scenarios where the number of tasks is not known in advance, and you want to reuse threads efficiently.

ExecutorService executor = Executors.newFixedThreadPool(poolSize - 10);
- This method creates an executor with a fixed-size thread pool where the number of threads is specified during pool creation.
- Tasks are queued if all threads are busy. If a thread encounters an exception and terminates, a new one will be created to replace it.
- It is suitable for scenarios where you want to limit the number of concurrently executing tasks.

---

## 20. Lifecycle of a Thread:

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
