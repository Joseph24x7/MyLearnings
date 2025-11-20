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

## 2. Virtual Threads
- Lightweight threads managed by the Java Virtual Machine (JVM) rather than the operating system.
- Allow for a large number of concurrent threads with minimal resource overhead.
- Ideal for I/O-bound applications, such as web servers and microservices, where many threads may be waiting for I/O operations to complete.
- Example:
  ```java
  Runnable task = () -> {
      // Simulate I/O operation
      try {
          Thread.sleep(1000);
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }
      System.out.println("Task completed by " + Thread.currentThread().getName());
  };

  // Creating and starting a virtual thread
  Thread virtualThread = Thread.ofVirtual().start(task);
  virtualThread.join();
  ```
- Virtual threads can be created using `Thread.ofVirtual().start()` or `Executors.newVirtualThreadPerTaskExecutor()`.
- Example using Executor and CompletableFuture:
- ```java
  ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
  CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
      // Simulate I/O operation
      try {
          Thread.sleep(1000);
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }
      System.out.println("Task completed by " + Thread.currentThread().getName());
  }, executor);
  future.join();
  executor.shutdown();
  ```
- Virtual threads are designed to work seamlessly with existing Java APIs, making it easier to adopt them without significant code changes.

---
## 3. Thread.ofVirtual() vs Thread.ofPlatform()
- `Thread.ofVirtual()` creates a virtual thread, which is lightweight and managed by the JVM. It is suitable for high-concurrency applications where many threads may be waiting for I/O operations.
- `Thread.ofPlatform()` creates a platform (or traditional) thread, which is managed by the operating system. It is suitable for CPU-bound tasks that require direct access to system resources.
- Virtual threads have lower overhead and can be created in large numbers, while platform threads are more resource-intensive and limited by the operating system.
- Example:
  ```java
  // Creating a virtual thread
  Thread virtualThread = Thread.ofVirtual().start(() -> {
      System.out.println("Virtual thread: " + Thread.currentThread().getName());
  });
    // Creating a platform thread
    Thread platformThread = Thread.ofPlatform().start(() -> {
        System.out.println("Platform thread: " + Thread.currentThread().getName());
    });
    virtualThread.join();
    platformThread.join();
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
