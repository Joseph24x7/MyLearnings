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