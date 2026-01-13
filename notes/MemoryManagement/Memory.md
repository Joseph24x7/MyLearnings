# Memory Management in Java

## 1. List and explain different memory types allocated by JVM

| Memory Area | Stores | Scope | Size Config |
|-------------|--------|-------|-------------|
| **Heap** | Objects, instance variables | Shared across threads | `-Xms` (initial), `-Xmx` (max) |
| **Stack** | Local variables, method calls, partial results | Per thread | `-Xss` (default 512KB-1MB) |
| **Metaspace** | Class metadata, method info, constant pool | Shared | `-XX:MaxMetaspaceSize` |
| **Code Cache** | JIT compiled native code | Shared | `-XX:ReservedCodeCacheSize` |
| **Native Memory** | Native method stacks, direct buffers | Per thread | OS dependent |

**Heap Structure (Generational):**
```
Heap
├── Young Generation (Eden + S0 + S1) - New objects, Minor GC
│   ├── Eden Space (80%) - New allocations
│   ├── Survivor 0 (10%) - Survived 1+ GC
│   └── Survivor 1 (10%) - Swap with S0
└── Old Generation (Tenured) - Long-lived objects, Major GC
```

```java
class Example {
    int instanceVar;           // Heap (with object)
    static int staticVar;      // Metaspace (class-level)
    
    void method() {
        int localVar = 10;     // Stack (primitive)
        Integer boxed = 10;    // Reference in Stack, Integer object in Heap
        Object obj = new Object(); // Reference in Stack, Object in Heap
        String str = "hello";  // Reference in Stack, String in String Pool (Heap)
    }
}
```

**Common JVM Memory Flags:**
```bash
java -Xms512m -Xmx2g -Xss1m -XX:MaxMetaspaceSize=256m -jar app.jar
```

---

## 2. Cyclomatic Complexity

- Measures code complexity based on **independent execution paths**
- **Formula:** `M = E - N + 2P` (Edges - Nodes + 2×Connected Components)
- **Simple Rule:** Count (if, for, while, case, catch, &&, ||, ?:) + 1

| Score | Risk | Action |
|-------|------|--------|
| 1-10 | Low | Maintainable |
| 11-20 | Moderate | Review needed |
| 21-50 | High | Refactor recommended |
| 50+ | Very High | Must refactor |

**Example:**
```java
// Complexity = 4 (1 + if + && + else)
public String check(int x, boolean flag) {
    if (x > 0 && flag) {
        return "positive";
    } else {
        return "other";
    }
}
```

**Tools:** SonarQube, PMD, Checkstyle

---

## 3. JVM Memory Tuning Best Practices

- Set `-Xms` = `-Xmx` to avoid heap resizing overhead
- Young Gen should be 1/3 to 1/4 of total heap
- Monitor GC logs: `-Xlog:gc*:file=gc.log`
- Use `-XX:+HeapDumpOnOutOfMemoryError` in production

---

## 4. Types of OutOfMemoryError

| Error Type | Cause | Fix |
|------------|-------|-----|
| `Java heap space` | Too many objects | Increase `-Xmx`, fix leaks |
| `Metaspace` | Too many classes loaded | Increase `-XX:MaxMetaspaceSize` |
| `GC overhead limit` | 98% time in GC, <2% heap freed | Fix memory leak |
| `Direct buffer memory` | NIO direct buffers exhausted | Increase `-XX:MaxDirectMemorySize` |
| `Unable to create native thread` | OS thread limit | Reduce `-Xss` or increase ulimit |

---

## 5. Steps to Fix OutOfMemoryError

1. **Enable Heap Dump:** `-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/`
2. **Analyze Dump:** Use Eclipse MAT, VisualVM, or JProfiler
3. **Identify Root Cause:**
   - Look for **Dominator Tree** - objects holding most memory
   - Check **Leak Suspects Report** - automatic detection
   - Analyze **Histogram** - object count by class
4. **Common Fixes:**
   - Close resources properly (use try-with-resources)
   - Clear static collections or use `WeakHashMap`
   - Use pagination/streaming for large datasets
   - Review caching strategy (use bounded caches like Caffeine)
   - Check for growing collections in long-running loops

```java
// Bad - Memory Leak
static Map<String, Object> cache = new HashMap<>();

// Good - Bounded Cache
Cache<String, Object> cache = Caffeine.newBuilder()
    .maximumSize(1000)
    .expireAfterWrite(10, TimeUnit.MINUTES)
    .build();
```

---

## 6. Heap Memory vs PermGen/Metaspace

| Aspect | Heap | Metaspace |
|--------|------|-----------|
| **Stores** | Objects, arrays, String pool | Class metadata, method bytecode |
| **Location** | JVM managed | Native memory (outside heap) |
| **Config** | `-Xmx1024m -Xms512m` | `-XX:MaxMetaspaceSize=256m` |
| **GC** | Young/Old Gen GC | Class unloading (rare) |
| **Growth** | Fixed max | Metaspace is unbounded (can grow until OS memory). |

---

## 7. PermGen vs Metaspace (Java 8+ Upgrade)

| PermGen (Java 7-) | Metaspace (Java 8+) |
|-------------------|---------------------|
| Fixed size, contiguous heap space | Auto-grows, native memory |
| `OutOfMemoryError: PermGen space` | `OutOfMemoryError: Metaspace` |
| `-XX:PermSize`, `-XX:MaxPermSize` | `-XX:MetaspaceSize`, `-XX:MaxMetaspaceSize` |
| Required tuning for app servers | Self-tuning, less config needed |
| GC during Full GC only | More efficient class unloading |

**Why Changed?**
- PermGen had **fixed size** causing frequent OOM in apps with dynamic class loading (Spring, Hibernate, OSGi)
- Difficult to tune - too small = OOM, too large = wasted memory
- Metaspace uses **native memory** - grows as needed, limited only by OS

**Migration:**
```bash
# Java 7
java -XX:PermSize=128m -XX:MaxPermSize=256m -jar app.jar

# Java 8+
java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar app.jar
```

---

## 8. What is Memory Leak? How to Analyze and Fix?

**Memory Leak:** Objects no longer needed but still referenced, preventing GC from reclaiming memory.

**Common Causes & Fixes:**

| Cause | Example | Fix |
|-------|---------|-----|
| Unclosed resources | Streams, connections | `try-with-resources` |
| Static collections | `static List cache` | Use bounded cache, WeakHashMap |
| Inner class references | Non-static inner class | Make inner class static |
| Listeners not removed | Event listeners | `removeListener()` on cleanup |
| ThreadLocal not cleared | `ThreadLocal<User>` | Call `remove()` in finally block |
| String.intern() abuse | Interning user data | Avoid interning dynamic strings |

```java
// Memory Leak Example
class Service {
    static List<byte[]> cache = new ArrayList<>();  // Grows forever
    
    void process() {
        cache.add(new byte[1024 * 1024]);  // 1MB added, never removed
    }
}

// Fixed with Bounded Cache
class Service {
    static Map<String, byte[]> cache = new LinkedHashMap<>(100, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > 100;  // LRU eviction
        }
    };
}
```

**Analysis Tools:**
- **Eclipse MAT** - Heap dump analysis, leak suspect reports
- **VisualVM** - Real-time monitoring, sampling
- **JProfiler/YourKit** - Commercial, comprehensive profiling
- **jmap** - `jmap -dump:format=b,file=heap.hprof <pid>`

---

## 9. Can you replicate StackOverflowError?

**Cause:** Stack memory exhausted due to deep/infinite recursion.

```java
// Infinite recursion
public void recursiveMethod() {
    recursiveMethod();  // Each call adds a stack frame
}
// Throws: java.lang.StackOverflowError

// Deep recursion (large input)
public int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);  // Fails for large n
}

// Fix: Use iteration or tail recursion optimization
public int factorialIterative(int n) {
    int result = 1;
    for (int i = 2; i <= n; i++) result *= i;
    return result;
}
```

**Stack size:** Default ~512KB-1MB. Increase with `-Xss2m` if needed.

---

## 10. Can you replicate OutOfMemoryError?

**Heap Space:**
```java
List<byte[]> list = new ArrayList<>();
while (true) {
    list.add(new byte[1024 * 1024]);  // 1MB each, never released
}
// Throws: java.lang.OutOfMemoryError: Java heap space
```

**Metaspace (Dynamic Class Loading):**
```java
while (true) {
    Enhancer enhancer = new Enhancer();  // CGLIB
    enhancer.setSuperclass(Object.class);
    enhancer.create();  // Creates new class each time
}
// Throws: java.lang.OutOfMemoryError: Metaspace
```

**Direct Buffer:**
```java
List<ByteBuffer> buffers = new ArrayList<>();
while (true) {
    buffers.add(ByteBuffer.allocateDirect(1024 * 1024));
}
// Throws: java.lang.OutOfMemoryError: Direct buffer memory
```

---

## 11. Heap vs Stack Memory

| Aspect | Heap | Stack |
|--------|------|-------|
| **Stores** | Objects, arrays | Primitives, references, method frames |
| **Scope** | Shared across all threads | Thread-specific (isolated) |
| **Lifetime** | Until GC reclaims | Until method returns |
| **Access Speed** | Slower (pointer lookup) | Faster (LIFO, contiguous) |
| **Size** | Large (GBs possible) | Small (512KB-1MB default) |
| **Allocation** | Dynamic, complex | Simple, automatic |
| **Thread Safety** | Requires synchronization | Inherently thread-safe |
| **Config** | `-Xmx`, `-Xms` | `-Xss` |
| **Error** | `OutOfMemoryError` | `StackOverflowError` |

**Visual:**
```
Thread 1 Stack    Thread 2 Stack         Heap (Shared)
┌──────────────┐  ┌──────────────┐       ┌─────────────────┐
│ main()       │  │ run()        │       │ Object A        │
│  int x = 5   │  │  int y = 10  │  ──►  │ Object B        │
│  ref → ──────┼──┼──────────────┼───────┤ Object C        │
│ method1()    │  │ method2()    │       │ String Pool     │
└──────────────┘  └──────────────┘       └─────────────────┘
```
