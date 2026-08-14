# Java Core Concepts (from Java 17 → 21)

---

## 1. Java 21 Features (from Java 17 → 21):

- Virtual Threads (Project Loom) – lightweight concurrency
- Sequenced Collections (ordered access to lists, sets, maps)
- Record Patterns (enhanced pattern matching)
- Pattern Matching for switch (standardized)
- Default UTF-8 Charset
- Generational ZGC and performance improvements

---

## 2. Virtual Threads vs Platform Threads:

| Feature             | Virtual Threads                                                 | Platform Threads                                      |
|---------------------|-----------------------------------------------------------------|-------------------------------------------------------|
| Lightweight         | Yes, managed by the JVM                                         | No, managed by the OS                                 |
| Usage               | Task spends most time waiting for I/O (DB, HTTP, disk, network) | Task spends almost all time computing, little waiting |
| Example Use Case    | Web servers (Tomcat, Spring Boot), Kafka consumers,             | CPU-heavy simulations like Hashing, encryption        |
|                     | HTTP clients, REST microservices, batch I/O pipelines           | complex number crunching                              |
| Scalability         | Can create millions of threads                                  | Limited by OS thread limits                           |
| Blocking Operations | Non-blocking, uses continuations                                | Blocking, tied to OS threads                          |

---

## 3. Creating and Using Virtual Threads:

- Creating Virtual Threads:
    - Using `Thread.ofVirtual().start(Runnable)` to create and start a virtual thread.
    - Using `Executors.newVirtualThreadPerTaskExecutor()` to create an executor service that uses virtual threads.
    - Virtual threads can be used just like platform threads, but they are much more lightweight.
- Limitation:
    - Virtual threads are not suitable for CPU-bound tasks that require intensive computation without blocking.
    - They are best suited for I/O-bound tasks where threads spend significant time waiting.
    - We cannot limit number of virtual threads like we do with platform threads in a thread pool.
    - If we need to limit concurrency, we can use a semaphore or other concurrency control mechanisms.
- Example:
  ```java
  // Creating a virtual thread directly
  Thread virtualThread = Thread.ofVirtual().start(() -> {
      System.out.println("Hello from Virtual Thread!");
  });
  virtualThread.join(); // Wait for the virtual thread to finish

  // Using an executor service with virtual threads
  try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      Future<String> future = executor.submit(() -> {
          // Simulate a blocking I/O operation
          Thread.sleep(1000);
          return "Result from Virtual Thread";
      });
      System.out.println(future.get()); // Wait for the result
  } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
  }
  ```

---

## 4. Sequenced Collections Interfaces:

- Introduced to provide a consistent way to access elements in a defined order.
- Works only with collections that maintain a specific order of elements, such as lists and linked collections.
- New interfaces:
    - `SequencedCollection<E>`: Extends `Collection<E>` and provides methods for ordered access.
    - `SequencedSet<E>`: Extends `Set<E>` and `SequencedCollection<E>`, ensuring unique elements in a defined order.
    - `SequencedMap<K, V>`: Extends `Map<K, V>` and provides ordered access to key-value pairs.
- Key Methods:
    - `getFirst()`: Returns the first element in the collection.
    - `getLast()`: Returns the last element in the collection.
    - `addFirst(E e)`: Adds an element to the beginning of the collection.
    - `addLast(E e)`: Adds an element to the end of the collection.
    - `removeFirst()`: Removes and returns the first element.
    - `removeLast()`: Removes and returns the last element.
    - 'reversed()': Returns a reversed view of the collection.

- Example:
  ```java
  SequencedMap<String, Integer> sequencedMap = new LinkedHashMap<>();
  sequencedMap.put("One", 1);
  sequencedMap.put("Two", 2);
  sequencedMap.put("Three", 3);
    System.out.println("First Entry: " + sequencedMap.getFirst());
    System.out.println("Last Entry: " + sequencedMap.getLast());
    ```

---
## 5. What are the new features you are aware of in Java 17 and Java 21?

Here is an overview of the key features introduced in both LTS releases:

### Java 17 Key Features:
1. **Records:** Immutable data carriers eliminating boilerplate classes.
2. **Sealed Classes:** Restricts class/interface inheritance hierarchies.
3. **Switch Expressions:** Standardized yields, arrow syntax, and case labels.
4. **Pattern Matching for `instanceof`:** Binds variables automatically inside conditional scopes.
5. **Text Blocks:** Easy multi-line string literals (`"""`).

### Java 21 Key Features:
1. **Virtual Threads (JEP 444):** Lightweight, concurrent threads mapped to OS threads, allowing millions of concurrent tasks.
2. **Pattern Matching for Switch (JEP 441):** Allows checking object types directly in switch blocks.
3. **Record Patterns (JEP 440):** Destructuring of Record values directly in pattern matching.
4. **Sequenced Collections (JEP 431):** New interfaces introducing `getFirst()`, `getLast()`, and `reversed()` methods.
5. **Generational ZGC (JEP 439):** Separates memory into young and old generations to increase throughput.

---


## 6. Virtual Threads — Deep Dive & Disadvantages



### What is a Virtual Thread?
A Virtual Thread (Project Loom, Java 21) is a **lightweight thread managed by the JVM**, not the OS.

- Traditional platform thread = 1 OS thread = ~1MB stack memory. Limited to ~thousands.
- Virtual thread = JVM manages it on a small number of **carrier (OS) threads**. Can create **millions**.
- When a virtual thread blocks (e.g., DB call, HTTP call), the JVM **unmounts** it from the carrier thread, freeing the carrier thread to run other virtual threads.

```java
// Create 1 million virtual threads - no problem!
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 1_000_000; i++) {
        executor.submit(() -> {
            Thread.sleep(1000); // blocks - virtual thread unmounts, carrier thread is freed
            return "done";
        });
    }
}
```

### Disadvantages of Virtual Threads

1. **Not suitable for CPU-bound tasks**
   - Virtual threads shine for I/O-bound work (waiting). If your task is pure computation (no blocking), virtual threads offer no benefit over platform threads — you still block the carrier thread.
   ```java
   // Bad use case for virtual threads
   Thread.ofVirtual().start(() -> {
       heavyCryptographyCalculation(); // CPU-bound, no I/O, carrier thread is blocked
   });
   ```

2. **Cannot limit concurrency like thread pools**
   - With platform threads: `Executors.newFixedThreadPool(10)` limits 10 concurrent tasks.
   - With virtual threads: you can't say "max 10 virtual threads". You need a `Semaphore` for that.
   ```java
   Semaphore semaphore = new Semaphore(10);
   executor.submit(() -> {
       semaphore.acquire();
       try { doWork(); } finally { semaphore.release(); }
   });
   ```

3. **Thread-local variables can be problematic**
   - With millions of virtual threads, heavy use of `ThreadLocal` variables (storing large objects) can lead to high memory usage since each virtual thread gets its own copy.
   - Java 21 introduces **Scoped Values** as a replacement for ThreadLocal in virtual thread contexts.

4. **Pinning — synchronized blocks can pin the virtual thread to the carrier**
   - If a virtual thread enters a `synchronized` block or calls a `native` method, it gets **pinned** to the carrier thread and cannot be unmounted — negating the benefit.
   - Solution: Replace `synchronized` with `ReentrantLock` in virtual thread code.
   ```java
   // BAD with virtual threads - pins carrier thread
   synchronized(lock) { callDatabase(); }

   // GOOD with virtual threads
   reentrantLock.lock();
   try { callDatabase(); } finally { reentrantLock.unlock(); }
   ```

5. **Not a silver bullet for all performance problems**
   - Virtual threads improve throughput for blocking I/O, but if your bottleneck is DB connection pool exhaustion, network bandwidth, or CPU — virtual threads won't help.

| Situation | Use Virtual Threads? |
|---|---|
| REST API with many concurrent users (I/O bound) | ✅ Yes |
| CPU-heavy number crunching | ❌ No |
| Streaming with reactive (WebFlux already non-blocking) | ⚠️ Redundant |
| Kafka consumers (high-volume, I/O waiting) | ✅ Yes |

