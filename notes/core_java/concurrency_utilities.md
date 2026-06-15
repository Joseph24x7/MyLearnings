# Java Concurrency Utilities

## 1. Parallel Stream and ForkJoinPool:


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


## 2. newCachedThreadPool() vs newFixedThreadPool() in Executor Service. What is the best advisable approach for I/O intensive tasks?


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

Advisable Replacement for CPU intensive tasks:
- For CPU-intensive tasks, it is advisable to use a fixed thread pool with a size equal to the number of available CPU cores.
```java
    int poolSize = Runtime.getRuntime().availableProcessors();
    ExecutorService executor = Executors.newFixedThreadPool(poolSize);
```

Advisable Replacement for I/O intensive tasks after Java 21:
- Java 21 introduced a new virtual thread feature that allows for lightweight threads that can be created in large numbers without the overhead of traditional threads.
```java
    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
```

---


## 3. Difference between completableFuture & Future? which is preferred?


### Using CompletableFuture:
- CompletableFuture is used when you need to perform asynchronous tasks that may return results or be composed together. You can easily combine multiple CompletableFutures to create more complex asynchronous workflows using methods like thenCompose, thenApply, thenAccept, and so on.
- CompletableFuture allows you to attach callbacks or actions that get executed when the computation is complete. This enables you to define behavior for success, failure, or completion without blocking the thread unnecessarily.
- CompletableFuture has built-in support for exceptionally handling errors. You can use the exceptionally method to specify a fallback value or to recover from an exception.

### Using Future:
- The Future interface lacks built-in support for easy composition of multiple asynchronous operations.
- Future relies on the get() method to retrieve the result of a computation, and it blocks until the result is available. This can make it challenging to perform asynchronous operations without blocking the thread.
- Exception handling with Future is often done by catching exceptions during the execution of the submitted task or by checking for exceptions using the get() method.

---


## 4. Parallel Programming using CompletableFuture


### What is CompletableFuture?
```
- Introduced in Java 8
- Non-blocking, async programming
- Promises a result will be available in future
- Chain multiple async operations
- Better for I/O-bound tasks
```

### Simple Example
```java
// Without CompletableFuture (blocking)
String result = getUserData();  // Waits for response
System.out.println(result);

// With CompletableFuture (non-blocking)
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> getUserData());
future.thenAccept(result -> System.out.println(result));
// Code continues immediately, doesn't wait
```

### Common Methods
```java
// Create future
CompletableFuture<String> cf = new CompletableFuture<>();
cf.complete("done");  // Manually complete

// Supply value asynchronously
CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "hello");

// Chain operations
cf2.thenApply(s -> s.toUpperCase())
   .thenAccept(System.out::println);

// Wait for completion
String result = cf2.get();  // Blocks until done
String result2 = cf2.join();  // Similar to get(), but throws unchecked exception

// Handle exceptions
cf2.exceptionally(e -> "error: " + e.getMessage());
```

### Real-World Example
```java
CompletableFuture<User> getUserAsync(Long id) {
    return CompletableFuture.supplyAsync(() -> {
        return userRepository.findById(id);  // Blocking call in thread pool
    });
}

// Usage
getUserAsync(1L)
    .thenCompose(user -> getOrdersAsync(user.getId()))  // Chain async calls
    .thenApply(orders -> orders.stream()
        .map(Order::getTotal)
        .reduce(0.0, Double::sum))
    .thenAccept(total -> System.out.println("Total: " + total))
    .exceptionally(e -> {
        e.printStackTrace();
        return null;
    });
```

---


## 5. supplyAsync vs applyAsync in CompletableFuture


### supplyAsync
```java
// supplyAsync(Supplier<U> supplier)
// Returns CompletableFuture<U>
// Takes a Supplier (produces value, no input)

CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "hello");
// Result type: CompletableFuture<String>

// Common use: Initial async operation
CompletableFuture.supplyAsync(() -> fetchFromDatabase())
```

### thenApply (not applyAsync, but related)
```java
// thenApply(Function<T, U> fn)
// Maps result of previous stage
// Executes in same thread as previous stage (blocking)

CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "hello")
    .thenApply(s -> s.toUpperCase());  // HELLO
// This thenApply runs in thread pool
```

### thenApplyAsync
```java
// thenApplyAsync(Function<T, U> fn)
// Maps result of previous stage
// Executes in different thread (non-blocking)

CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "hello")
    .thenApplyAsync(s -> {
        Thread.sleep(1000);  // Takes time
        return s.toUpperCase();
    });  // Runs in separate thread

// Can specify executor
.thenApplyAsync(s -> s.toUpperCase(), Executors.newFixedThreadPool(4))
```

### Comparison Table

| Method | Input | Output | Execution | Use Case |
|--------|-------|--------|-----------|----------|
| **supplyAsync** | None (Supplier) | U | New thread | Start async flow |
| **thenApply** | T (from previous) | U | Same thread | Quick transformation |
| **thenApplyAsync** | T (from previous) | U | New thread | Long-running operation |

### Example Showing Difference
```java
// Using thenApply (blocking in same thread)
CompletableFuture.supplyAsync(() -> {
    System.out.println("Step 1: " + Thread.currentThread().getName());
    return "data1";
})
.thenApply(s -> {
    System.out.println("Step 2 (thenApply): " + Thread.currentThread().getName());
    return s + "-processed";  // Same thread as previous
});

// Output:
// Step 1: ForkJoinPool.commonPool-worker-1
// Step 2: ForkJoinPool.commonPool-worker-1

// Using thenApplyAsync (non-blocking, different thread)
CompletableFuture.supplyAsync(() -> {
    System.out.println("Step 1: " + Thread.currentThread().getName());
    return "data1";
})
.thenApplyAsync(s -> {
    System.out.println("Step 2 (thenApplyAsync): " + Thread.currentThread().getName());
    return s + "-processed";  // Different thread
});

// Output:
// Step 1: ForkJoinPool.commonPool-worker-1
// Step 2: ForkJoinPool.commonPool-worker-2
```

---


## 6. Rate Limiting Infrastructure - Separate App vs In-App


### Option 1: In-Application Rate Limiting
```java
@RestController
public class OrderController {
    
    @PostMapping("/orders")
    @RateLimiter(name = "order")  // Using Resilience4J
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.create(order));
    }
}
```

**Pros:**
- ✅ Simple, no extra infrastructure
- ✅ Faster (local decision)
- ✅ Per-service configuration

**Cons:**
- ❌ Different limits per instance (inconsistent)
- ❌ No global view of traffic
- ❌ Doesn't protect backend from DDoS

### Option 2: API Gateway Rate Limiting
```xml
<!-- Apigee/Kong rate limiting -->
<RateLimit>
  <Allow count="1000"/>
  <Interval>1</Interval>
  <TimeUnit>minute</TimeUnit>
</RateLimit>
```

**Pros:**
- ✅ Centralized control
- ✅ Consistent across services
- ✅ Protects entire backend
- ✅ Global traffic view

**Cons:**
- ⚠️ Additional infrastructure (Apigee, Kong)
- ⚠️ Single point of failure (unless HA setup)
- ⚠️ Higher latency (extra hop)

### Option 3: Separate Rate Limiting Service
```
┌────────────┐
│  Client    │
└─────┬──────┘
      │
┌─────▼──────────────────┐
│  Rate Limiting Service  │  (Redis-backed, distributed)
│  (Separate deployment)  │
└─────┬──────────────────┘
      │
┌─────▼─────────┐
│ API Gateway   │
└─────┬─────────┘
      │
┌─────▼─────────────┐
│ Microservices     │
└───────────────────┘
```

**Pros:**
- ✅ True distributed rate limiting
- ✅ Can be scaled independently
- ✅ Consistent across all services
- ✅ Redis backend for persistence

**Cons:**
- ❌ Extra infrastructure to manage
- ❌ Network latency (extra call)
- ❌ Operational complexity

### Recommendation
| Scenario | Best Choice |
|----------|------------|
| Small system, few services | In-Application |
| Medium system with API Gateway | API Gateway |
| Large system, strict consistency | Separate service |
| Very high traffic, DDoS risk | API Gateway + Separate service |

### Example: Separate Rate Limiting Service
```java
@RestController
@RequestMapping("/rate-limit")
public class RateLimitController {
    
    @Autowired
    private RateLimitService rateLimitService;
    
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkRateLimit(
            @RequestParam String apiKey,
            @RequestParam(defaultValue = "10") int limit) {
        
        boolean allowed = rateLimitService.allowRequest(apiKey, limit);
        return ResponseEntity.ok(allowed);
    }
}

// In API Gateway
@Component
public class RateLimitingFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String apiKey = ((HttpServletRequest) request).getHeader("X-API-Key");
        
        // Call separate rate limiting service
        boolean allowed = rateLimitService.checkLimit(apiKey);
        
        if (!allowed) {
            ((HttpServletResponse) response).setStatus(429);  // Too Many Requests
            return;
        }
        
        chain.doFilter(request, response);
    }
}
```

---


## 7. Notify vs NotifyAll: When to Use Which?


Both `notify()` and `notifyAll()` are used to wake up threads that are waiting on an object's monitor (via `wait()`).

### `notify()`
- **Behavior:** Wakes up only **one random thread** currently waiting on the object's monitor.
- **Lock Acquisition:** The woken thread does not immediately run; it moves to the entry set and waits to acquire the lock when the current thread releases it.
- **Use Case:** Use when all waiting threads are performing the **exact same task** (e.g. worker threads in a thread pool), and it doesn't matter which thread is woken up.
- **Risk:** Can lead to **deadlocks/signal loss** if the "wrong" thread is woken up (e.g., waking up another producer instead of a waiting consumer when the queue is full).

### `notifyAll()`
- **Behavior:** Wakes up **all threads** currently waiting on the object's monitor.
- **Lock Acquisition:** All woken threads move to the entry set and compete to acquire the lock. Only one gets it, while others block again when they try to enter the synchronized block.
- **Use Case:** Use when waiting threads are waiting on **different conditions** (e.g., some waiting to read, some waiting to write) or doing different tasks.
- **Example:**
```java
public class SharedQueue {
    private List<Integer> queue = new ArrayList<>();
    private int LIMIT = 5;

    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == LIMIT) {
            wait();
        }
        queue.add(item);
        // Wake up everyone so consumers can consume, or producers can check limits
        notifyAll(); 
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int val = queue.remove(0);
        notifyAll(); // Wake up producers/consumers
        return val;
    }
}
```

---


## 8. Deep Dive: ThreadPoolExecutor Configuration Parameters


`ThreadPoolExecutor` is the engine behind Java's `ExecutorService`. It has five critical parameters:

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    corePoolSize,        // 1. Core threads kept alive
    maximumPoolSize,     // 2. Max threads allowed
    keepAliveTime,       // 3. Time idle threads wait
    unit,                // 4. Time unit
    workQueue,           // 5. Task queue
    threadFactory,       // 6. Thread creation logic (Optional)
    handler              // 7. Rejection handler (Optional)
);
```

### 1. How Core/Max/Queue Work Together (Task Submission Flow):
1. **Under Core Size:** When a task is submitted, if running threads < `corePoolSize`, a new thread is created immediately, even if other threads are idle.
2. **Queueing:** If running threads >= `corePoolSize`, the task is placed in the `workQueue`.
3. **Exceeding Queue:** If the `workQueue` is full, and running threads < `maximumPoolSize`, a new thread is created.
4. **Rejection:** If the `workQueue` is full and running threads >= `maximumPoolSize`, the task is rejected using the configured `RejectedExecutionHandler`.

### 2. Work Queue Options:
- **`LinkedBlockingQueue` (Bounded/Unbounded):** 
  - Standard queue. If unbounded, the pool never grows beyond `corePoolSize` (since the queue is never full).
- **`SynchronousQueue` (Direct Handoff):** 
  - Does not store tasks. Instantly hands task to a thread. If no thread is available, creates a new one (up to `maximumPoolSize`). Used in `CachedThreadPool`.
- **`ArrayBlockingQueue` (Bounded):**
  - Array-based bounded queue. Prevents memory exhaustion.

### 3. Rejection Policies:
- **`AbortPolicy` (Default):** Throws `RejectedExecutionException`.
- **`CallerRunsPolicy`:** The thread submitting the task runs it itself (slows down submission rate, natural backpressure).
- **`DiscardPolicy`:** Silently discards the task.
- **`DiscardOldestPolicy`:** Discards the oldest unhandled task in the queue and retries.
```

---


## 9. Double-Checked Locking (Singleton Pattern)


Double-Checked Locking is an optimization used in lazy initialization of a Singleton class to avoid the overhead of acquiring a lock every time the instance is requested.

### Implementation:
```java
public class DoubleCheckedSingleton {
    // ⚠️ CRITICAL: volatile prevents instruction reordering
    private static volatile DoubleCheckedSingleton instance;

    private DoubleCheckedSingleton() {}

    public static DoubleCheckedSingleton getInstance() {
        if (instance == null) { // First Check (No Lock)
            synchronized (DoubleCheckedSingleton.class) { // Lock
                if (instance == null) { // Second Check (With Lock)
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
}
```

### Why `volatile` is mandatory here:
The creation of a new object `instance = new DoubleCheckedSingleton();` is not atomic. It involves three bytecode instructions under the hood:
1. Allocate memory.
2. Invoke the constructor to initialize the fields.
3. Assign the memory address to the `instance` variable.
Without `volatile`, the JVM/compiler can reorder these steps: executing step 3 *before* step 2. If this happens, a second thread checking `instance == null` (First Check) might see a non-null but **partially initialized** object reference and attempt to use it, causing a crash. `volatile` guarantees a write happens-before subsequent reads, preventing this reordering.

---


## 10. ReentrantLock vs Synchronized


A `ReentrantLock` (from `java.util.concurrent.locks`) is a more advanced, flexible locking mechanism compared to the `synchronized` keyword.

### Key Differences & Features:
1. **Explicit Lock/Unlock:** You must explicitly call `lock()` and call `unlock()` inside a `finally` block to prevent resource leaks.
2. **Fairness Policy:** You can configure a fairness policy: `new ReentrantLock(true)`. A fair lock guarantees that the longest-waiting thread gets the lock first (prevents starvation), whereas `synchronized` is always unfair.
3. **Non-blocking tryLock():** `lock.tryLock()` attempts to acquire the lock. If unavailable, it returns `false` immediately instead of blocking the thread, allowing the thread to do other work.
4. **Interrupted Locking:** `lockInterruptibly()` allows a thread to acquire a lock but remain interruptible, so it can wake up if another thread calls `thread.interrupt()`.
5. **Reentrancy:** Both `synchronized` and `ReentrantLock` are reentrant. A thread holding the lock can re-acquire it recursively without deadlocking itself (increments a hold counter).

---


## 11. Multi-Threading Challenge: Stop Rest of the Threads When One Task Completes


### Problem Statement:
You have 3 tasks running on 3 threads. As soon as **any one** of the tasks completes successfully, the other two tasks should be stopped immediately.

### Solution Using `ExecutorService` and `invokeAny()`:
The most standard and clean way in Java is to submit the tasks as `Callable` to an `ExecutorService` and call `invokeAny()`.
- `invokeAny()` executes the tasks concurrently.
- As soon as one task returns a result successfully, `invokeAny()` returns that result and automatically calls `future.cancel(true)` on all other active threads in the executor, sending them an **interruption signal**.

### Code Example:
```java
import java.util.List;
import java.util.concurrent.*;

public class TaskCancellationDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<String> task1 = () -> {
            Thread.sleep(1000); // Finishes first
            return "Task 1 completed!";
        };

        Callable<String> task2 = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Task 2 was cancelled!");
            }
            return "Task 2 completed!";
        };

        Callable<String> task3 = () -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                System.out.println("Task 3 was cancelled!");
            }
            return "Task 3 completed!";
        };

        try {
            // Run tasks; invokeAny returns as soon as the first one succeeds
            String result = executor.invokeAny(List.of(task1, task2, task3));
            System.out.println("Winner result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow(); // Instantly interrupts any remaining threads
        }
    }
}
```


