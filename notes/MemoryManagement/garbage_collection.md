# Java Garbage Collection

## 1. What is Garbage Collection?

- Automatic memory management process that identifies and removes unused objects from heap memory
- Runs on a **daemon thread** in background
- Cannot be forced, only requested via `System.gc()` (not guaranteed)
- Uses **Mark and Sweep** algorithm internally

---

## 2. What are all different Garbage Collection Algorithms?

| GC Type | Flag | Best For |
|---------|------|----------|
| Serial GC | `-XX:+UseSerialGC` | Single-threaded, small apps |
| Parallel GC | `-XX:+UseParallelGC` | Multi-threaded, throughput focus |
| G1 GC | `-XX:+UseG1GC` | Large heap, low latency (Default Java 9+) |
| ZGC | `-XX:+UseZGC` | Ultra-low latency (<10ms pause) |
| Shenandoah | `-XX:+UseShenandoahGC` | Low pause times |

**Pipeline Configuration:**
```bash
java -XX:+UseG1GC -Xmx4g -jar app.jar
```

---

## 3. Making Objects Eligible for Garbage Collection

```java
// 1. Nullifying reference
obj = null;

// 2. Reassigning reference
obj = new Object();

// 3. Object created inside method (after method ends)
void method() { Object o = new Object(); }

// 4. Islands of Isolation
```

---

## 4. What is the purpose of overriding finalize() method?

- Called by GC before object destruction
- Used for cleanup (closing resources)
- **Deprecated in Java 9** - Use `try-with-resources` or `Cleaner` instead

```java
@Override
protected void finalize() throws Throwable {
    // cleanup code - NOT RECOMMENDED
}
```

---

## 5. Islands of Isolation

- Group of objects referencing each other but **not reachable** from any live thread
- All objects in the island become eligible for GC

```java
class Node {
    Node next;
}
Node a = new Node();
Node b = new Node();
a.next = b;
b.next = a;
a = null;
b = null;  // Island of isolation - both eligible for GC
```

---

## 6. Daemon Thread Characteristics

| Feature | Daemon Thread | User Thread |
|---------|---------------|-------------|
| Purpose | Background tasks (GC) | Main application logic |
| JVM Exit | JVM exits even if running | JVM waits for completion |
| Creation | `thread.setDaemon(true)` | Default |
| Priority | Low | Normal |

```java
Thread t = new Thread(() -> {});
t.setDaemon(true);  // Must be set before start()
t.start();
```

---

## 6. Z Garbage Collector (ZGC)

- A scalable, low-latency garbage collector designed for applications requiring large heaps (multi-terabyte).
- Aims to keep pause times below 10 milliseconds, regardless of heap size.
- Uses concurrent and parallel techniques to minimize the impact of garbage collection on application performance.
- Features:
    - Concurrent marking and relocation of objects.
    - Region-based memory management.
    - Support for large heaps (up to several terabytes).
- Ideal for applications with high throughput and low latency requirements, such as real-time systems and large data
  processing applications.
- Configurable via JVM options, e.g., `-XX:+UseZGC` to enable ZGC.
- Continuously evolving with improvements in performance and capabilities in subsequent Java versions.

## 7. Epsilon (No-Op) Garbage Collector

- Introduced in Java 11, Epsilon GC is a no-op garbage collector that does not reclaim memory.
- Primarily used for performance testing and memory pressure testing.
- Example Usage:
  ```bash
  java -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -Xmx512m -jar yourapp.jar
  ```
- Note: Since Epsilon GC does not reclaim memory, it is not suitable for production environments

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