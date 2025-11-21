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

## 5. Record Patterns
- Enhance pattern matching capabilities by allowing deconstruction of record types.
- Simplify the extraction of components from records in a concise manner.
- Example:
  ```java
  record Point(int x, int y) {}
    Object obj = new Point(10, 20);
    if (obj instanceof Point(int x, int y)) {
        System.out.println("X: " + x + ", Y: " + y);
    }
    ```
- Can be used in `instanceof` checks and `switch` statements.
- Example with switch:
  ```java
  Object obj = new Point(10, 20);
    switch (obj) {
        case Point(int x, int y) -> System.out.println("X: " + x + ", Y: " + y);
        default -> System.out.println("Not a Point");
    }
    ```
- Improves code readability and reduces boilerplate code when working with records.

---
## 6. Pattern Matching for switch (Standardized)
- Extends pattern matching capabilities to `switch` statements.
- Allows matching against types and deconstructing objects directly within `switch` cases.
- Example:
  ```java
  Object obj = "Hello, World!";
    switch (obj) {
        case String s -> System.out.println("It's a string: " + s);
        case Integer i -> System.out.println("It's an integer: " + i);
        default -> System.out.println("Unknown type");
    }
  ```
- Supports type patterns and record patterns.
- Enhances code clarity and reduces the need for explicit casting.
- Example with record patterns:
  ```java
  record Point(int x, int y) {}
    Object obj = new Point(5, 10);
    switch (obj) {
        case Point(int x, int y) -> System.out.println("Point coordinates: (" + x + ", " + y + ")");
        default -> System.out.println("Not a Point");
    }
  ```
- Facilitates more expressive and maintainable code when dealing with multiple types.

---
## 7. Default UTF-8 Charset
- Java 21 introduces UTF-8 as the default charset for standard APIs that previously used the platform's default charset.
- This change ensures consistent behavior across different platforms and environments.
- Affects APIs such as `String`, `Files`, and `InputStreamReader` that deal with character encoding.
- Example:
  ```java
  String text = "Hello, World!";
    byte[] bytes = text.getBytes(); // Uses UTF-8 by default in Java 21
    String decodedText = new String(bytes); // Decodes using UTF-8 by default
    System.out.println(decodedText);
  ```
- Reduces issues related to character encoding mismatches when reading or writing text data.
- Developers can still specify a different charset if needed, but UTF-8 will be the default.

---

## 8. Generational ZGC and Performance Improvements
- Java 21 enhances the Z Garbage Collector (ZGC) with generational capabilities.
- Generational ZGC divides the heap into generations (young and old), optimizing garbage collection for different object lifetimes.
- Improves performance by reducing pause times and increasing throughput for applications with varying object lifetimes.
- Key Features:
  - Concurrent marking and relocation of objects.
  - Region-based memory management.
  - Support for large heaps (up to several terabytes).
- Example JVM options to enable ZGC:
    ```bash
    -XX:+UseZGC
    -XX:ZCollectionInterval=1000
    ```
- Ideal for applications with high throughput and low latency requirements, such as real-time systems and large data processing applications.

---