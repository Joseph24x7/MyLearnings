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


| Feature | Synchronized Map (e.g. `Collections.synchronizedMap()`) | ConcurrentHashMap |
|---------|---------------------------------------------------------|-------------------|
| **Locking Mechanism** | Locks the **entire map** object for every read/write operation. | Uses **fine-grained locking** (locks only the head node of a specific bucket chain/segment or uses CAS for updates). |
| **Concurrency Level** | Low. Only one thread can access the map (read or write) at any time. Others wait. | High. Multiple threads can read/write concurrently in different buckets without blocking. |
| **Iterator Behavior** | Iterator is **Fail-Fast** (throws `ConcurrentModificationException` if modified during iteration). Requires manual synchronization. | Iterator is **Fail-Safe** (reflects changes, does not throw exception). |
| **Null Support** | Allows null keys and values (depending on the backing map). | Does **not** allow null keys or values. |

---


## 13. HashMap Rehashing Mechanics (Capacity 16 & Resizing)


When a `HashMap` is initialized with a default capacity of **16** and a load factor of **0.75**:

1. **Resizing Threshold:** The threshold is calculated as `capacity * loadFactor` (`16 * 0.75 = 12`).
2. **Rehash Trigger:** 
   - When you insert the **13th key-value pair**, the map exceeds its threshold.
   - It automatically doubles its capacity to **32**.
   - A new bucket array of size 32 is created.
3. **Rehashing Process:** All existing entries are traversed, their hash is re-evaluated with the new capacity (`hash & (32 - 1)`), and they are relocated to the new buckets.

### Time Complexity:
- **Normal Insertion:** `O(1)` average case.
- **Resizing Insertion:** `O(N)` worst case (where N is the number of elements in the map) because all existing elements must be copied to the new bucket array.

---


## 14. Common Data Structures & Their Time Complexities


| Data Structure | Search (Avg / Worst) | Insertion (Avg / Worst) | Deletion (Avg / Worst) | Under the Hood / Usage |
|----------------|-----------------------|-------------------------|------------------------|------------------------|
| **ArrayList** | `O(1)` (by index)<br>`O(N)` (by value) | `O(1)` (amortized)<br>`O(N)` (if resizing / insert at index) | `O(N)` (requires shifting elements) | Resizable array. Good for random access. |
| **LinkedList** | `O(N)` | `O(1)` (at head/tail)<br>`O(N)` (if inserting in middle) | `O(1)` (at head/tail)<br>`O(N)` (if deleting in middle) | Doubly Linked List. Good for frequent insertion/deletion. |
| **HashMap / HashSet** | `O(1)` / `O(N)` (worst case if hash collision / treeify) | `O(1)` / `O(N)` | `O(1)` / `O(N)` | Hashing with buckets (Linked List/Red-Black Tree). |
| **TreeMap / TreeSet** | `O(log N)` | `O(log N)` | `O(log N)` | Red-Black Tree. Maintains sorted order. |
| **PriorityQueue** | `O(N)` (find arbitrary value)<br>`O(1)` (peek minimum) | `O(log N)` (offer) | `O(log N)` (poll) | Binary Heap. Used for scheduling / Dijkstra. |

---


## 15. How does ConcurrentHashMap work internally in Java?

In Java 8+, `ConcurrentHashMap` uses a combination of **volatile variables**, **CAS (Compare-And-Swap) operations**, and **synchronized blocks** at the bucket node level:

1. **Bucket-Level Locking (Fine-Grained):** Rather than locking the entire map (like `Hashtable` or `Collections.synchronizedMap()`), it only locks the **head node** of the specific bucket being written to.
2. **Lock-Free Reads:** Read operations (`get()`) are completely lock-free. The value and next pointers of a node are declared `volatile`, ensuring that any write to a node is immediately visible to other threads.
3. **Optimized Write Operations:**
   - **CAS (Compare-And-Swap):** If a bucket is empty, a write is performed using a lock-free CAS operation to place the new node.
   - **Synchronized Head Node:** If the bucket is not empty (collision), the thread synchronizes *only* on the head node of that bucket (`synchronized(headNode)`).
4. **Concurrent Resizing:** When the map resizes, multiple threads can help transfer bucket entries to the new table concurrently (helper threads), speeding up the rehashing process.
