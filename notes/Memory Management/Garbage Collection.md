# Java Garbage Collection

## 1. What is Garbage Collection?
- Automatic memory management by JVM
- Manages objects in heap memory
- Removes objects no longer referenced by the program

### Manual Garbage Collection
```java
System.gc();
```
- Not recommended to call explicitly
- No guarantee of immediate finalization
- JVM decides when to actually perform collection

## 2. What are the Garbage Collection algorithms?

### 1. Serial Garbage Collector (Serial GC)
- Single-threaded collector
- Suitable for small memory requirements
- Uses "stop-the-world" approach
- Pauses all application threads during collection

### 2. Parallel Garbage Collector (Parallel GC)
- Uses multiple threads for collection
- Optimized for multi-core processors
- Focuses on high throughput
- Still uses "stop-the-world" pauses

### 3. G1 (Garbage First) Collector
- Default garbage collector
- Balances latency and throughput
- Features:
  - Handles large heaps efficiently
  - Divides heap into regions
  - Collects from regions with least live objects
  - Maintains low pause times

### 4. Z Garbage Collector (ZGC)
- Low-latency collector (Java 11+)
- Designed for minimal pause times
- Ideal for large heaps
- Performs most work concurrently
- Best for strict latency requirements

### Modifying Garbage Collector
```bash
java -XX:+UseG1GC -jar YourApplication.jar
```
