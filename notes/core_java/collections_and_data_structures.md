# Java Collections & Data Structures

---

## 1. HashMap vs ConcurrentHashMap


| Feature | HashMap | ConcurrentHashMap |
|---------|---------|-------------------|
| **Structure** | Single hash table | Divided into multiple segments, each acting as a separate hash table |
| **Thread Safety** | Not thread-safe. Multiple threads can lead to data corruption without external synchronization | Thread-safe. When a thread wants to modify a segment, it locks only that segment |
| **Performance** | Better performance in single-threaded scenarios | Better performance in multi-threaded scenarios where many threads need to access and modify the map simultaneously |
| **Concurrency** | No built-in thread safety mechanisms | Allows multiple threads to operate concurrently on different segments |

---


## 2. How to Design a Generic Method to Print List Elements?


### Using Java Generics
```java
// Generic method using unbounded wildcard
public static void printList(List<?> list) {
    list.forEach(System.out::println);
}

// Generic method with type parameter
public static <T> void printElements(List<T> list) {
    for (T element : list) {
        System.out.println(element);
    }
}

// Usage
List<String> strings = Arrays.asList("A", "B", "C");
List<Integer> numbers = Arrays.asList(1, 2, 3);

printList(strings);    // Works with String
printElements(numbers); // Works with Integer
```

### Key Points
- `<T>` - Type parameter allowing any type
- `<?>` - Wildcard representing unknown type
- Both approaches accept lists of any type
- `<T extends Comparable<T>>` - Bounded type for constraints

---


## 3. HashMap / HashSet Implementation & Hash Collision Upgrades in Java 8


### HashMap Internal Working
1. **Hashing:** When `put(key, value)` is called, `hashCode()` of key is computed.
2. **Index Calculation:** `index = hash(key) & (n-1)` where n is bucket array size.
3. **Storage:** Entry stored at calculated index. If collision occurs, entry is added to linked list/tree at that bucket.
4. **Retrieval:** `get(key)` uses same hash calculation to find bucket, then traverses list/tree using `equals()`.

---


## 4. HashMap vs IdentityHashMap


| Feature | HashMap | IdentityHashMap |
|---------|---------|-----------------|
| **Key Comparison** | Uses `equals()` method | Uses reference equality (`==`) |
| **Hash Code** | Uses `hashCode()` method | Uses `System.identityHashCode()` |
| **Use Case** | General-purpose map | When reference identity matters |
| **Null Keys** | Allows one null key | Allows one null key |
| **Performance** | Standard performance | Slightly faster (no equals/hashCode calls) |

### Example
```java
String s1 = new String("key");
String s2 = new String("key");

// HashMap - treats s1 and s2 as same key (equals returns true)
HashMap<String, String> hashMap = new HashMap<>();
hashMap.put(s1, "value1");
hashMap.put(s2, "value2");
System.out.println(hashMap.size()); // Output: 1

// IdentityHashMap - treats s1 and s2 as different keys (different references)
IdentityHashMap<String, String> identityMap = new IdentityHashMap<>();
identityMap.put(s1, "value1");
identityMap.put(s2, "value2");
System.out.println(identityMap.size()); // Output: 2
```

### When to Use IdentityHashMap
- Topology-preserving object graph transformations (serialization/deep copy).
- Proxy-based frameworks where object identity matters.
- Maintaining object-metadata mappings.

---


## 5. Is "null" key/values allowed in HashMap/HashSet


- In HashMap, only ONE null key and multiple null values are allowed.
- In HashSet, only ONE null value is allowed.
- The null key is always stored at bucket index 0, and No hashCode() is called for null key
- Note: Hashset uses Hashmap internally and Hashmap user Arrays of LinkedList/Balanced Tree internally.

---


## 6. Is "null" key/values allowed in ConcurrentHashMap


- In ConcurrentHashMap - No Null Key Allowed.
- Ambiguity: `map.get(key)` returning null could mean key doesn't exist OR value is null.
- In concurrent environment, `containsKey()` check followed by `get()` is not atomic.
- Null was intentionally prohibited to avoid these issues in concurrent scenarios.

---


## 7. Array vs ArrayList?


| Feature         | Array                             | ArrayList                                                   |
|-----------------|-----------------------------------|-------------------------------------------------------------|
| **Size**        | Fixed size                        | Dynamic size (resizable)                                    |
| **Type**        | Can hold primitives and objects   | Can only hold objects (uses wrapper classes for primitives) |
| **Performance** | Faster for fixed-size collections | Slightly slower due to dynamic resizing and boxing/unboxing |
| **Memory**      | Less memory overhead              | More memory overhead due to dynamic nature                  |
| **Methods**     | Limited built-in methods          | Rich set of methods for manipulation                        |

---


## 8. How ArrayList internally manages to increase its size?

- When an ArrayList is created, it has an initial capacity (default is 10).
- When elements are added and the current capacity is exceeded, ArrayList increases its size.
- The resizing process involves:
  1. Creating a new array with a larger capacity (usually 1.5 times the current size).
  2. Copying the existing elements to the new array.
  3. Updating the reference of the ArrayList to point to the new array.

---


## 9. What happens if equals() is overridden but hashCode() isn't?


### The Problem
If two objects are equal according to `equals()`, they **must have the same hashCode()**. Violating this contract causes issues in hash-based collections.

### Real-World Issue
```java
@Data
class Employee {
    private Long id;
    private String name;
    
    @Override
    public boolean equals(Object o) {
        // Overridden but hashCode() NOT overridden
        return id.equals(((Employee) o).id);
    }
}

// Using in HashMap
HashMap<Employee, String> map = new HashMap<>();
Employee emp1 = new Employee(1L, "John");
Employee emp2 = new Employee(1L, "John");

map.put(emp1, "Developer");
System.out.println(map.get(emp2));  // Returns null! ❌

// emp1.equals(emp2) = true but emp1.hashCode() != emp2.hashCode()
```

### The Solution
Always override both together:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return Objects.equals(id, employee.id);
}

@Override
public int hashCode() {
    return Objects.hash(id);  // Same as equals()
}
```

### Key Takeaway
- **Contract:** `a.equals(b)` → `a.hashCode() == b.hashCode()`
- **Impact:** HashMap, HashSet fail without this
- **IntelliJ Tip:** Use "Generate equals() and hashCode()" to auto-generate both

---


## 10. What happens in a HashSet if `equals()` is overridden but `hashCode()` is NOT?


If `equals()` is overridden but `hashCode()` is not, two logically "equal" objects will have different hash codes generated by the default `Object.hashCode()` implementation (which is typically based on the object's memory address).

### How HashSet behaves:
1. When you add the first object, `HashSet` computes its hash code and places it in a bucket (say, bucket 4).
2. When you add the second object (which is logically equal), it generates a different hash code (say, bucket 9).
3. Since the hash codes are different, `HashSet` places it in bucket 9 without checking `equals()` on any object in bucket 4.
4. Result: The `HashSet` now contains **duplicate** objects, and its size will be **2** instead of 1, violating the fundamental set property.

### Simple Example:
Using the custom `EmployeeClass` from [EmployeeMgmt.java](file:///c:/Users/Joseph/IdeaProjects/MyLearnings/src/main/java/com/mylearnings/java/java_code/advanced_java/EmployeeMgmt.java):
```java
class EmployeeClass {
    private Integer id;
    private String name;
    private int salary;
    private String dept;

    // Overridden equals() comparing only id
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeClass that)) return false;
        return Objects.equals(id, that.id);
    }
    // hashCode() NOT overridden!
}

// In main method:
HashSet<EmployeeClass> sets = new HashSet<>();
EmployeeClass emp1 = new EmployeeClass(1, "ab", 123, "CSE");
EmployeeClass emp2 = new EmployeeClass(1, "ab", 123, "CSE");

sets.add(emp1);
sets.add(emp2);

System.out.println(emp1.equals(emp2)); // Prints: true
System.out.println(sets.size());        // Prints: 2 (Duplicate exists!)
```

---


## 11. HashMap vs ConcurrentHashMap: Why is HashMap faster in a single-threaded environment?


In a single-threaded environment, `HashMap` is faster because:
1. **Zero Synchronization Overhead:** `HashMap` methods do not use locks, synchronized blocks, or volatile read/write operations. 
2. **No Memory Barriers:** `ConcurrentHashMap` has memory barrier instructions (like volatile reads/writes or CAS operations) to ensure changes are visible across CPU caches of different threads. These instructions bypass CPU cache optimizations, making operations slower.
3. **No Segment/Bucket Locking:** `ConcurrentHashMap` uses bucket-level synchronization (via synchronized blocks on bucket nodes or CAS) which adds execution time for acquiring/releasing locks.

---


## 12. Synchronized Map vs ConcurrentHashMap

| Feature | Synchronized HashMap (`Collections.synchronizedMap()`) | ConcurrentHashMap |
|---|---|---|
| **Locking Mechanism** | Locks the **entire map** object for every read/write operation. | Locks only the **head node** of the specific bucket being modified (fine-grained). |
| **Concurrency Level** | Low (only one thread can access the map at a time; others block). | High (multiple threads can read/write concurrently across different buckets). |
| **Iterator Behavior** | **Fail-Fast** (throws `ConcurrentModificationException` if modified during iteration). | **Fail-Safe** (weakly consistent, does not throw `ConcurrentModificationException`). |
| **Null Support** | Allows `null` key and `null` values. | **Disallows** `null` key and `null` values (prevents ambiguity in concurrent calls). |
| **Performance** | Slow under high thread contention. | High throughput and scalable. |

```java
// Synchronized HashMap - entire map locked on each operation
Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());
syncMap.put("a", "1"); // locks whole map

// ConcurrentHashMap - only the affected bucket is locked
Map<String, String> concMap = new ConcurrentHashMap<>();
concMap.put("a", "1"); // only locks bucket for key 'a'
```

---

## 13. HashMap Rehashing Mechanics (Capacity 16, Resizing & Threshold)

Default initial capacity = **16**, Load factor = **0.75**.

1. **Threshold Calculation:** `threshold = capacity * loadFactor` (`16 * 0.75 = 12`).
2. **Rehash Trigger:**
   - Inserting keys 1 to 12 → normal O(1) insertion.
   - Inserting the **13th key** → exceeds threshold 12, triggering capacity doubling (16 → **32**).
3. **Rehashing Process:**
   - A new bucket array of size 32 is allocated.
   - All existing 12 entries are re-evaluated using `hash & (newCapacity - 1)` and moved to the new array.

### Time Complexity:
- **Normal Insertion:** `O(1)` average.
- **Resizing Insertion:** `O(N)` for the single resize operation (amortized `O(1)` across all insertions).
- **Worst Case (All collisions):** `O(log N)` in Java 8+ (converted to Red-Black Tree when bucket chain > 8).

---

## 14. Common Data Structures & Their Time Complexities

| Data Structure | Get / Search | Insert | Delete | Real-World Usage / Under the Hood |
|---|---|---|---|---|
| **ArrayList** | `O(1)` (index)<br>`O(N)` (value) | `O(1)` (amortized)<br>`O(N)` (resize/middle) | `O(N)` (shift elements) | Resizable array. DB query results, random access. |
| **LinkedList** | `O(N)` | `O(1)` (head/tail)<br>`O(N)` (middle) | `O(1)` (head/tail)<br>`O(N)` (middle) | Doubly Linked List. Frequent insertions/deletions. |
| **HashMap / HashSet** | `O(1)` avg<br>`O(log N)` worst | `O(1)` avg<br>`O(log N)` worst | `O(1)` avg<br>`O(log N)` worst | Array of buckets + Linked List / Red-Black Tree. Caching, deduplication. |
| **LinkedHashMap** | `O(1)` | `O(1)` | `O(1)` | Maintains insertion/access order. LRU Cache implementation. |
| **PriorityQueue** | `O(1)` (peek)<br>`O(N)` (search) | `O(log N)` | `O(log N)` | Binary Heap. Task scheduling, Top-K problems. |
| **ArrayDeque / Deque** | `O(N)` (search) | `O(1)` (head/tail) | `O(1)` (head/tail) | Resizable array. BFS traversal, stack/queue operations. |
| **TreeMap / TreeSet** | `O(log N)` | `O(log N)` | `O(log N)` | Red-Black Tree. Sorted maps, range queries. |

---

## 15. How does ConcurrentHashMap work internally in Java 8+?

In Java 8+, `ConcurrentHashMap` uses **volatile variables**, **CAS (Compare-And-Swap) operations**, and **synchronized blocks** at the bucket node level:

1. **Bucket-Level Locking (Fine-Grained):** Locks only the **head node** of the specific bucket being modified, rather than the whole table.
2. **Lock-Free Reads:** Read operations (`get()`) are completely lock-free because node values and `next` pointers are declared `volatile`.
3. **CAS for Empty Buckets:** If a bucket is empty, new nodes are inserted using a lock-free CAS operation (`sun.misc.Unsafe` / `VarHandle`).
4. **Synchronized Head Node for Collisions:** If a bucket already contains nodes, the thread synchronizes *only* on the head node (`synchronized(headNode)`).
5. **Concurrent Resizing:** Multiple threads can assist in transferring bucket entries to the new table concurrently during a table expansion.

