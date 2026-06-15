# Java OOP & Language Fundamentals

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


## 5. Serializable Interface


### What if we don't add Serializable interface to a class undergoing platform interchange?
- The Java runtime will throw a `NotSerializableException`.
- This occurs because the Java serialization mechanism relies on the `Serializable` interface to determine whether an object can be serialized safely.

### What is `serialVersionUID`? What if we do not define it?
- It serves as a version control mechanism for serialized objects.
- If you do not provide a `serialVersionUID`, Java will automatically generate one during compilation.
- When deserializing an object serialized with a different version, a version mismatch can occur, leading to an `InvalidClassException` at runtime.

---


## 6. Java Reflection API


- Allows us to inspect and manipulate the structure and behavior of the Java classes, interfaces, methods, fields, and other class members at runtime.
- **Realtime usage:** JSON serialization and deserialization using Gson or ObjectMapper libraries.

---


## 7. Class Loaders


- It is part of the JRE responsible for loading `.class` files (bytecode) into the JVM memory at runtime.
- Once loaded, the JVM can create an instance, call methods, etc.

### Types of Class Loaders
- **Bootstrap Class Loader:** Loads core Java classes from the JDK (e.g., `java.lang`, `java.util`).
- **System/Application Class Loader:** Loads classes from the application's classpath (e.g., user-defined classes and libraries).

---


## 8. Var Args vs Arrays


| Concept | Var Args | Arrays |
|----------|-----------|--------|
| **Definition** | Accepts zero or more arguments. | Fixed size. |
| **Usage in Method Signature** | Can be used only once and must be the last parameter. | Can be used multiple times and can be placed anywhere. |
| **Invocation** | Can be called with individual elements or an array. | Must be called with an array. |

---


## 9. Association, Aggregation and Composition


- Association is a relation between two separate classes which establishes through their Objects.
- Association can be one-to-one, one-to-many, many-to-one, or many-to-many.
- Composition and Aggregation are the two forms of association.
- It defines how strong their relationships are and whether they are unidirectional or bidirectional.

---


## 10. Cloning – Types


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


## 11. Try-With-Resources and Order of Resource Closure

- Try-With-Resources was introduced in Java 7.
- Automatically closes resources that implement `AutoCloseable` or `Closeable`.
- Resources are closed in **reverse order** of their declaration (LIFO - Last In First Out).
- Ensures resources are closed even if an exception occurs.

---


## 12. Static block vs Static method, which is loaded first?

- Static blocks are executed when the class is loaded, before any static methods or variables are accessed.
- Static methods are loaded when they are called, after the static blocks have been executed.
- Therefore, static blocks are loaded first.

### Purpose of Static Block:
- Used for static initialization of a class.
- Can initialize static variables or perform setup tasks that need to be done once when the class is loaded.

---


## 13. When to use Schedulers vs Batches?


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


## 14. Advantages of Using Records


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


## 15. Java 9: Escaping References & The Cleaner API


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

