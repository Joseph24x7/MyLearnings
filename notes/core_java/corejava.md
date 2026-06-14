# Core Java Interview Questions

---

## 1. Abstract Class vs Interface

| Feature | Abstract Class (AC) | Interface (IF) |
|----------|--------------------|----------------|
| **Instance Variables** | Can have instance variables. | Has only constants. |
| **Constructors** | Can have constructors to instantiate instance variables. | Cannot have constructors. |
| **Access Modifiers** | Methods and fields can have various access modifiers. | Methods are implicitly public. Fields are implicitly public, static, and final. |
| **Multiple Inheritance** | Inherited class can extend only one abstract class. | A class can implement multiple interfaces. |

---

## 2. What will happen if we override static/final?

- **final** → Compilation error. Overriding is not possible because final cannot be modified.
- **static** → No compilation error but leads to method hiding. Overriding is not possible because static doesn't work with objects.

---

## 3. Method Overriding Rules

- **Different return type:** Only same or covariant type is allowed.
- **Different access specifier:** We cannot reduce visibility (public → protected → default → private).
- **Different exception type:** Broader exceptions are not allowed; narrowing/no exception/same exception is allowed.

---

## 4. Predefined Final Classes in Java

- `java.lang.String`
- `java.lang.Math`
- `java.lang.System`
- `java.util.UUID`
- All wrapper classes

---

## 5. Advantages of Being Immutable

- **Security:** As the value cannot be modified, reducing the risk of data tampering.
- **Caching:** Java can optimize memory usage by reusing string instances from String pool.
- **Hashing:** Immutable strings can be used as keys in hash tables (e.g., HashMap) because their hash code remains constant.

---

## 6. Serializable Interface

### What if we don't add Serializable interface to a class undergoing platform interchange?
- The Java runtime will throw a `NotSerializableException`.
- This occurs because the Java serialization mechanism relies on the `Serializable` interface to determine whether an object can be serialized safely.

### What is `serialVersionUID`? What if we do not define it?
- It serves as a version control mechanism for serialized objects.
- If you do not provide a `serialVersionUID`, Java will automatically generate one during compilation.
- When deserializing an object serialized with a different version, a version mismatch can occur, leading to an `InvalidClassException` at runtime.

---

## 7. Java Reflection API

- Allows us to inspect and manipulate the structure and behavior of the Java classes, interfaces, methods, fields, and other class members at runtime.
- **Realtime usage:** JSON serialization and deserialization using Gson or ObjectMapper libraries.

---

## 8. Class Loaders

- It is part of the JRE responsible for loading `.class` files (bytecode) into the JVM memory at runtime.
- Once loaded, the JVM can create an instance, call methods, etc.

### Types of Class Loaders
- **Bootstrap Class Loader:** Loads core Java classes from the JDK (e.g., `java.lang`, `java.util`).
- **System/Application Class Loader:** Loads classes from the application's classpath (e.g., user-defined classes and libraries).

---

## 9. Var Args vs Arrays

| Concept | Var Args | Arrays |
|----------|-----------|--------|
| **Definition** | Accepts zero or more arguments. | Fixed size. |
| **Usage in Method Signature** | Can be used only once and must be the last parameter. | Can be used multiple times and can be placed anywhere. |
| **Invocation** | Can be called with individual elements or an array. | Must be called with an array. |

---

## 10. Association, Aggregation and Composition

- Association is a relation between two separate classes which establishes through their Objects.
- Association can be one-to-one, one-to-many, many-to-one, or many-to-many.
- Composition and Aggregation are the two forms of association.
- It defines how strong their relationships are and whether they are unidirectional or bidirectional.

---

## 12. HashMap vs ConcurrentHashMap

| Feature | HashMap | ConcurrentHashMap |
|---------|---------|-------------------|
| **Structure** | Single hash table | Divided into multiple segments, each acting as a separate hash table |
| **Thread Safety** | Not thread-safe. Multiple threads can lead to data corruption without external synchronization | Thread-safe. When a thread wants to modify a segment, it locks only that segment |
| **Performance** | Better performance in single-threaded scenarios | Better performance in multi-threaded scenarios where many threads need to access and modify the map simultaneously |
| **Concurrency** | No built-in thread safety mechanisms | Allows multiple threads to operate concurrently on different segments |

---

## 13. How to Design a Generic Method to Print List Elements?

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

## 16. Cloning – Types

- **Shallow Clone**:
  - Creates a new object but copies references of nested objects. Changes in nested objects affect both original and clone.
  - Default `clone()` method behavior.

- **Deep Clone**:
  - Creates a new object and recursively copies all nested objects. Completely independent copy.
  - Manual implementation required.

### Other Ways to Clone
- **Copy Constructor:** `new Employee(existingEmployee)`
- **Serialization:** Serialize and deserialize the object
- **Clone Libraries:** Apache Commons `SerializationUtils.clone()`

---

## 17. HashMap / HashSet Implementation & Hash Collision Upgrades in Java 8

### HashMap Internal Working
1. **Hashing:** When `put(key, value)` is called, `hashCode()` of key is computed.
2. **Index Calculation:** `index = hash(key) & (n-1)` where n is bucket array size.
3. **Storage:** Entry stored at calculated index. If collision occurs, entry is added to linked list/tree at that bucket.
4. **Retrieval:** `get(key)` uses same hash calculation to find bucket, then traverses list/tree using `equals()`.

---

## 18. HashMap vs IdentityHashMap

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

## 19. How to Create an Immutable Class
- **Custom way**:
  - class as `final`
  - Make all fields `private` and `final`.
  - Don't provide setter methods.
  - Initialize all fields via constructor.
  - Perform deep copy for mutable objects in constructor and getters.
- **Java Records**
- **Using Lombok @Value**

---

## 20. Try-With-Resources and Order of Resource Closure
- Try-With-Resources was introduced in Java 7.
- Automatically closes resources that implement `AutoCloseable` or `Closeable`.
- Resources are closed in **reverse order** of their declaration (LIFO - Last In First Out).
- Ensures resources are closed even if an exception occurs.

---

## 21. Is "null" key/values allowed in HashMap/HashSet

- In HashMap, only ONE null key and multiple null values are allowed.
- In HashSet, only ONE null value is allowed.
- The null key is always stored at bucket index 0, and No hashCode() is called for null key
- Note: Hashset uses Hashmap internally and Hashmap user Arrays of LinkedList/Balanced Tree internally.

---

## 22. Is "null" key/values allowed in ConcurrentHashMap

- In ConcurrentHashMap - No Null Key Allowed.
- Ambiguity: `map.get(key)` returning null could mean key doesn't exist OR value is null.
- In concurrent environment, `containsKey()` check followed by `get()` is not atomic.
- Null was intentionally prohibited to avoid these issues in concurrent scenarios.

---

## 23. Static block vs Static method, which is loaded first?
- Static blocks are executed when the class is loaded, before any static methods or variables are accessed.
- Static methods are loaded when they are called, after the static blocks have been executed.
- Therefore, static blocks are loaded first.

### Purpose of Static Block:
- Used for static initialization of a class.
- Can initialize static variables or perform setup tasks that need to be done once when the class is loaded.

---

## 24. Array vs ArrayList?

| Feature         | Array                             | ArrayList                                                   |
|-----------------|-----------------------------------|-------------------------------------------------------------|
| **Size**        | Fixed size                        | Dynamic size (resizable)                                    |
| **Type**        | Can hold primitives and objects   | Can only hold objects (uses wrapper classes for primitives) |
| **Performance** | Faster for fixed-size collections | Slightly slower due to dynamic resizing and boxing/unboxing |
| **Memory**      | Less memory overhead              | More memory overhead due to dynamic nature                  |
| **Methods**     | Limited built-in methods          | Rich set of methods for manipulation                        |

---

## 25. How ArrayList internally manages to increase its size?
- When an ArrayList is created, it has an initial capacity (default is 10).
- When elements are added and the current capacity is exceeded, ArrayList increases its size.
- The resizing process involves:
  1. Creating a new array with a larger capacity (usually 1.5 times the current size).
  2. Copying the existing elements to the new array.
  3. Updating the reference of the ArrayList to point to the new array.

---

## 26. What happens if equals() is overridden but hashCode() isn't?

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

## 27. When to use Schedulers vs Batches?

### Schedulers (For Time-Based Tasks)
```java
@Scheduled(fixedRate = 5000)  // Every 5 seconds
public void sendReminderEmails() {
    List<User> users = userService.getUsersWithDueReminders();
    users.forEach(user -> emailService.send(user));
}
```

**Use When:**
- Task runs at **specific times** (daily, hourly, etc.)
- Examples: Send newsletters, generate reports, cleanup temp files
- **Time:** fixedRate, fixedDelay, cron

**Pros:**
- Simple scheduled execution
- No infrastructure needed

**Cons:**
- Single instance only (in clustered environment, runs on each instance)
- Limited scalability for large datasets

### Batches (For Data Processing)
```java
@Component
public class OrderProcessingBatchJob {
    
    @Scheduled(cron = "0 2 * * * ?")  // 2 AM daily
    public void processOrders() {
        int batchSize = 1000;
        int page = 0;
        
        while (true) {
            List<Order> orders = orderRepository.findPending(PageRequest.of(page, batchSize));
            if (orders.isEmpty()) break;
            
            orders.stream().parallel().forEach(order -> {
                paymentService.process(order);
                order.setStatus("COMPLETED");
            });
            orderRepository.saveAll(orders);
            page++;
        }
    }
}
```

**Use When:**
- Processing **large datasets** in chunks
- Examples: ETL jobs, bulk data migration, inventory sync
- Need **transaction control** and **error handling**

**Pros:**
- Can process huge data in chunks
- Better error handling and restart capability
- Supports parallelization

**Cons:**
- More complex setup
- Needs batch job framework (Spring Batch)

### Comparison Table

| Aspect | Scheduler | Batch |
|--------|-----------|-------|
| **Data Volume** | Small | Large |
| **Frequency** | Fixed intervals | Bulk processing |
| **Example** | Send email, Cleanup | ETL, Bulk import |
| **Scalability** | Limited | High |
| **Failure Handling** | Basic | Advanced |

---

## 28. Advantages of Using Records

Records are lightweight immutable data carriers introduced in Java 16+.

### Before Records
```java
public final class Point {
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int x() { return x; }
    public int y() { return y; }
    
    @Override
    public boolean equals(Object o) { /* ... */ }
    
    @Override
    public int hashCode() { /* ... */ }
    
    @Override
    public String toString() { /* ... */ }
}
```

### With Records (Java 16+)
```java
public record Point(int x, int y) { }
```

### Advantages

| Advantage | Benefit |
|-----------|---------|
| **Concise** | 1 line vs 20+ lines of boilerplate |
| **Immutable by Default** | All fields are final, thread-safe |
| **Auto-generated Methods** | equals(), hashCode(), toString() auto-created |
| **Compact Constructor** | Optional constructor for validation |
| **Pattern Matching** | Works with `instanceof` (Java 17+) |

### Example with Validation
```java
public record Order(long id, String productName, double price) {
    // Compact constructor for validation
    public Order {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}

// Usage
Order order = new Order(1, "Laptop", 1000);
System.out.println(order);  // Order[id=1, productName=Laptop, price=1000.0]
```

### When to Use Records
✅ DTOs (Data Transfer Objects)
✅ Value objects
✅ API responses
✅ Configuration holders

❌ NOT suitable for entities with complex behavior
❌ When you need mutable fields

---

## 29. What happens in a HashSet if `equals()` is overridden but `hashCode()` is NOT?

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

## 30. HashMap vs ConcurrentHashMap: Why is HashMap faster in a single-threaded environment?

In a single-threaded environment, `HashMap` is faster because:
1. **Zero Synchronization Overhead:** `HashMap` methods do not use locks, synchronized blocks, or volatile read/write operations. 
2. **No Memory Barriers:** `ConcurrentHashMap` has memory barrier instructions (like volatile reads/writes or CAS operations) to ensure changes are visible across CPU caches of different threads. These instructions bypass CPU cache optimizations, making operations slower.
3. **No Segment/Bucket Locking:** `ConcurrentHashMap` uses bucket-level synchronization (via synchronized blocks on bucket nodes or CAS) which adds execution time for acquiring/releasing locks.

---

## 31. Design Patterns & SOLID Principles Quick Reference

### 1. Strategy Pattern
- **Definition:** Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
- **Example:** `Comparator<T>` in Java collections.
```java
// Interchangeable sorting strategies
list.sort(Comparator.naturalOrder());
list.sort(Comparator.reverseOrder());
```

### 2. Fluent Interface Pattern
- **Definition:** An API design that relies on method chaining to make code more readable (often used in builders).
- **Example:**
```java
User user = new UserBuilder()
                .setName("John")
                .setEmail("john@example.com")
                .build();
```

### 3. Facade Pattern
- **Definition:** Provides a simplified interface to a complex subsystem.
- **Example:** A `BookingFacade` simplifying calls to `PaymentService`, `FlightService`, and `HotelService`.
```java
public class BookingFacade {
    public void bookTrip(TripDetails trip) {
        paymentService.pay();
        flightService.reserve();
        hotelService.book();
    }
}
```

### 4. Proxy Pattern
- **Definition:** Provides a placeholder/surrogate for another object to control access to it (e.g. lazy loading, security, logging).
- **Example:** Spring AOP uses JDK Dynamic Proxies or CGLIB to intercept method calls for `@Transactional` or `@Cacheable`.

### 5. Factory vs Abstract Factory Pattern
- **Factory Method:** Creates objects of a single family through inheritance (a method overridden by subclasses).
- **Abstract Factory:** Creates families of related/dependent objects without specifying their concrete classes (a factory of factories).
- **Example:**
```java
// Factory Method
Shape circle = ShapeFactory.getShape("CIRCLE");

// Abstract Factory
Button macButton = GUIFactory.getFactory("MAC").createButton();
```

### 6. Builder Pattern
- **Definition:** Separates the construction of a complex object from its representation, allowing step-by-step creation.
- **Example:**
```java
public class Product {
    private String name;
    private double price;
    
    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
    }
    
    public static class Builder {
        private String name;
        private double price;
        public Builder name(String name) { this.name = name; return this; }
        public Builder price(double price) { this.price = price; return this; }
        public Product build() { return new Product(this); }
    }
}
```

### 7. SOLID Principles Explained Simply

| Principle | Meaning | Real-world Example |
|-----------|---------|--------------------|
| **S**ingle Responsibility (SRP) | A class should have only one reason to change. | `Invoice` class calculates price, `InvoicePrinter` prints it (not combined in one class). |
| **O**pen/Closed (OCP) | Software entities should be open for extension but closed for modification. | Using interfaces so you can add a `UpiPayment` strategy without modifying existing `PaymentProcessor` code. |
| **L**iskov Substitution (LSP) | Subclasses should be substitutable for their base classes without breaking correctness. | If `Ostrich` extends `Bird` but cannot fly, calling `bird.fly()` on an Ostrich throws an exception, violating LSP. Refactor so only `FlyingBird` has the `fly()` method. |
| **I**nterface Segregation (ISP) | Clients should not be forced to depend on interfaces they do not use. | Instead of a giant `Worker` interface with `code()` and `manage()`, split it into `Coder` and `Manager` interfaces. |
| **D**ependency Inversion (DIP) | Depend on abstractions, not on concretions. | Injected services via interfaces, e.g. `@Autowired private PaymentGateway gateway;` instead of `private PayPalGateway gateway = new PayPalGateway();`. |

---

## 32. Creational Design Patterns in Detail
Creational patterns deal with object creation mechanisms, trying to create objects in a manner suitable to the situation.

1. **Singleton:** Restricts instantiation of a class to a single instance.
2. **Factory Method:** Defines an interface for creating a single object, but lets subclasses decide which class to instantiate.
3. **Abstract Factory:** Creates families of related objects.
4. **Builder:** Constructs complex objects step-by-step.
5. **Prototype:** Creates new objects by copying an existing instance (using `clone()`).

---

## 33. Synchronized Map vs ConcurrentHashMap

| Feature | Synchronized Map (e.g. `Collections.synchronizedMap()`) | ConcurrentHashMap |
|---------|---------------------------------------------------------|-------------------|
| **Locking Mechanism** | Locks the **entire map** object for every read/write operation. | Uses **fine-grained locking** (locks only the head node of a specific bucket chain/segment or uses CAS for updates). |
| **Concurrency Level** | Low. Only one thread can access the map (read or write) at any time. Others wait. | High. Multiple threads can read/write concurrently in different buckets without blocking. |
| **Iterator Behavior** | Iterator is **Fail-Fast** (throws `ConcurrentModificationException` if modified during iteration). Requires manual synchronization. | Iterator is **Fail-Safe** (reflects changes, does not throw exception). |
| **Null Support** | Allows null keys and values (depending on the backing map). | Does **not** allow null keys or values. |

---

## 34. HashMap Rehashing Mechanics (Capacity 16 & Resizing)

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

## 35. Common Data Structures & Their Time Complexities

| Data Structure | Search (Avg / Worst) | Insertion (Avg / Worst) | Deletion (Avg / Worst) | Under the Hood / Usage |
|----------------|-----------------------|-------------------------|------------------------|------------------------|
| **ArrayList** | `O(1)` (by index)<br>`O(N)` (by value) | `O(1)` (amortized)<br>`O(N)` (if resizing / insert at index) | `O(N)` (requires shifting elements) | Resizable array. Good for random access. |
| **LinkedList** | `O(N)` | `O(1)` (at head/tail)<br>`O(N)` (if inserting in middle) | `O(1)` (at head/tail)<br>`O(N)` (if deleting in middle) | Doubly Linked List. Good for frequent insertion/deletion. |
| **HashMap / HashSet** | `O(1)` / `O(N)` (worst case if hash collision / treeify) | `O(1)` / `O(N)` | `O(1)` / `O(N)` | Hashing with buckets (Linked List/Red-Black Tree). |
| **TreeMap / TreeSet** | `O(log N)` | `O(log N)` | `O(log N)` | Red-Black Tree. Maintains sorted order. |
| **PriorityQueue** | `O(N)` (find arbitrary value)<br>`O(1)` (peek minimum) | `O(log N)` (offer) | `O(log N)` (poll) | Binary Heap. Used for scheduling / Dijkstra. |

---

## 36. How to Make a Class Immutable in Java

To make a class immutable, follow these concrete design steps:
1. **Declare the class as `final`** so it cannot be extended (prevents subclasses from overriding methods and introducing mutability).
2. **Make all fields `private` and `final`** to restrict direct access and enforce single-initialization.
3. **Do not provide setter methods** for any fields.
4. **Initialize all fields via constructor** performing deep copies for mutable objects.
5. **Perform Defensive Copying** in getter methods for mutable fields (e.g. return a copy of a `List` or `Date` object rather than the original reference).

### Simple Code Example:
```java
public final class ImmutableUser {
    private final String name;
    private final List<String> roles; // Mutable object

    public ImmutableUser(String name, List<String> roles) {
        this.name = name;
        // Defensive copy during initialization
        this.roles = new ArrayList<>(roles);
    }

    public String getName() {
        return name;
    }

    public List<String> getRoles() {
        // Defensive copy during retrieval
        return new ArrayList<>(roles);
    }
}
```

---

## 37. Java 9: Escaping References & The Cleaner API

An **escaping reference** occurs when an object's internal reference is exposed to the outside world, allowing external modification or premature access. 

In **Java 9**, the `Cleaner` API was introduced to replace the terminally deprecated `finalize()` method. However, implementing `Cleaner` introduces a major escaping reference trap:

### The Cleaner API Trap:
- The cleaning runnable registered with `Cleaner` must **never** hold a reference to the object being cleaned.
- If it holds a reference (e.g. capturing `this` in a lambda or using a non-static inner class), the object is kept alive forever, preventing it from becoming phantom reachable. As a result, the garbage collector can never reclaim the object, and the cleaning action is never executed—causing a resource leak.

### Bad Code (Reference Escapes):
```java
cleaner.register(this, () -> {
    // ❌ Reference escape! 'this' is captured inside the lambda
    System.out.println("Cleaning resource: " + this.resourceId); 
});
```

### Good Code (No Reference Escape):
```java
public class SafeResource implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    public SafeResource(State state) {
        // Pass a static inner class holding only the required State, NOT the parent object
        this.cleanable = cleaner.register(this, new CleaningAction(state));
    }

    // Static nested class does NOT hold a reference to the outer class
    private static class CleaningAction implements Runnable {
        private final State state;
        CleaningAction(State state) { this.state = state; }

        @Override
        public void run() {
            System.out.println("Cleaning up resource with state: " + state.id);
        }
    }
    
    @Override
    public void close() { cleanable.clean(); }
}
```
