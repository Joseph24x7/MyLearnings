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
