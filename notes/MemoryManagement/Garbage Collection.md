# Java Garbage Collection

## 1. What is Garbage Collection?

### Overview
- Objects in the heap are managed by the Java Virtual Machine (JVM) and are subject to automatic garbage collection when they are no longer referenced by the program.

### Manual Trigger
```java
System.gc();
```
- You can explicitly trigger garbage collection, but it's generally not recommended.
- Calling System.gc() does not guarantee that the finalize() method of objects awaiting finalization will be called immediately.

---

## 2. Garbage Collection Algorithms

### Serial Garbage Collector (Serial GC)
- Simple, single-threaded garbage collector
- Suitable for applications with small memory requirements
- Uses a "stop-the-world" approach
- All application threads are paused during garbage collection

### Parallel Garbage Collector (Parallel GC)
- Also known as the throughput collector
- Uses multiple threads for garbage collection
- Suitable for multi-core processors
- Stops application threads during collection
- Designed for applications where high throughput is more important than low-latency

### G1 (Garbage First) Garbage Collector (Default)
- Widely used garbage collector
- Provides good balance between low-latency and high throughput
- Efficiently handles large heap sizes
- Default collector in recent Java versions
- Divides heap into regions
- Collects garbage from regions with least live objects
- Helps maintain low pause times

### Z Garbage Collector (ZGC)
- Low-latency garbage collector (Java 11+)
- Designed for minimal disruption
- Maintains consistently low pause times even for large heaps
- Performs most work concurrently
- Well-suited for applications with stringent latency requirements

### Configuration Example
```bash
java -XX:+UseG1GC -jar YourApplication.jar
```

---

## 3. Making Objects Eligible for Garbage Collection

### Methods
1. **Nullify References**
   - Set all available object references to null

2. **Reference Reassignment**
   - Make the reference variable refer to another object

---

## 4. What is the purpose of overriding finalize() method?

- Finalize method (finalizer) is defined in java.lang.Object
- Called by Garbage collector just before collecting eligible objects
- Provides last chance for cleanup and resource release

---

## 5. Islands of Isolation

- Objects no longer reachable by the application
- Still referenced by each other
- Creates a cycle of objects
- Cannot be collected by the garbage collector

---

## 6. Daemon Thread Characteristics
- Runs as a Daemon thread in the background
- Continuously frees up heap memory
- Destroys unreachable objects
- Manages objects no longer referenced by the program

---
