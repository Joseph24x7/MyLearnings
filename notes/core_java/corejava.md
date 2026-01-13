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

## 11. Ways to Create Immutable Class

- `@Value`
- Manually creating
- `record` – introduced in Java 16 (stable)

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

## 14. Underlying Data Structures HashMap Uses

| Java Version | Data Structure |
|--------------|----------------|
| **Before Java 8** | Array of linked lists (buckets) |
| **Java 8+** | Array of linked lists that convert to balanced trees (Red-Black Trees) when threshold is exceeded |

### Key Components
- **Array (Bucket Array):** Main structure holding entries, default initial capacity is 16.
- **Linked List:** Used to handle collisions within each bucket.
- **Red-Black Tree:** Replaces linked list when bucket size exceeds threshold (8 entries) for O(log n) lookup.

---

## 16. Cloning – Types

### Types of Cloning

| Type | Description | Implementation |
|------|-------------|----------------|
| **Shallow Clone** | Creates a new object but copies references of nested objects. Changes in nested objects affect both original and clone. | Default `clone()` method behavior |
| **Deep Clone** | Creates a new object and recursively copies all nested objects. Completely independent copy. | Manual implementation required |

### Implementation Example
```java
// Shallow Clone
class Employee implements Cloneable {
    String name;
    Address address;  // Reference type
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();  // Shallow copy
    }
}

// Deep Clone
class Employee implements Cloneable {
    String name;
    Address address;
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Employee cloned = (Employee) super.clone();
        cloned.address = (Address) address.clone();  // Clone nested object
        return cloned;
    }
}
```

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

### HashSet Implementation
- **Backed by HashMap:** HashSet internally uses HashMap.
- **Elements as Keys:** Set elements are stored as keys in the HashMap.
- **Dummy Value:** A constant dummy object (`PRESENT`) is used as value for all entries.

```java
// HashSet internally
private transient HashMap<E, Object> map;
private static final Object PRESENT = new Object();

public boolean add(E e) {
    return map.put(e, PRESENT) == null;
}
```

### Hash Collision Upgrades in Java 8

| Aspect | Before Java 8 | Java 8+ |
|--------|---------------|---------|
| **Collision Handling** | Only Linked List | Linked List → Red-Black Tree |
| **Worst Case Lookup** | O(n) | O(log n) |
| **Treeify Threshold** | N/A | 8 (converts to tree when bucket has > 8 entries) |
| **Untreeify Threshold** | N/A | 6 (converts back to list when entries reduce to 6) |
| **Min Tree Capacity** | N/A | 64 (bucket array must be ≥ 64 for treeification) |

### Why This Upgrade?
- **DoS Attack Prevention:** Malicious inputs with same hash could degrade HashMap to O(n).
- **Performance Guarantee:** Red-Black Tree ensures O(log n) even in worst case.
- **Key Requirement:** Keys should implement `Comparable` for tree ordering; otherwise, identity hash is used.


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

### Requirements for Immutable Class
1. Declare the class as `final` (prevent inheritance).
2. Make all fields `private` and `final`.
3. Don't provide setter methods.
4. Initialize all fields via constructor.
5. Perform deep copy for mutable objects in constructor and getters.

### Manual Implementation
```java
public final class ImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    public ImmutablePerson(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        // Deep copy of mutable object
        this.hobbies = new ArrayList<>(hobbies);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobbies() {
        // Return defensive copy
        return new ArrayList<>(hobbies);
    }
}
```

### Using Java Record (Java 16+)
```java
public record Person(String name, int age) {
    // Automatically immutable, final fields, getters, equals, hashCode, toString
}
```

### Using Lombok @Value
```java
@Value
public class Person {
    String name;
    int age;
    // Lombok generates final fields, getters, equals, hashCode, toString
}
```

---

## 20. Try-With-Resources and Order of Resource Closure

### What is Try-With-Resources?
- Introduced in Java 7.
- Automatically closes resources that implement `AutoCloseable` or `Closeable`.
- Resources are closed in **reverse order** of their declaration (LIFO - Last In First Out).
- Ensures resources are closed even if an exception occurs.

### Code Example
```java
try (FileInputStream s1 = new FileInputStream("file1.txt");
     FileInputStream s2 = new FileInputStream("file2.txt")) {
    // Use resources
}
// s2 is closed first, then s1
```

### Order of Closure: **s2 closes first, then s1**

### Why Reverse Order?
- Resources opened later may depend on resources opened earlier.
- Closing in reverse order ensures dependent resources are closed first.
- Similar to stack unwinding.

### Complete Example with Custom Resources
```java
class Resource implements AutoCloseable {
    private String name;
    
    public Resource(String name) {
        this.name = name;
        System.out.println("Opening: " + name);
    }
    
    @Override
    public void close() {
        System.out.println("Closing: " + name);
    }
}

// Usage
try (Resource r1 = new Resource("First");
     Resource r2 = new Resource("Second");
     Resource r3 = new Resource("Third")) {
    System.out.println("Using resources");
}

// Output:
// Opening: First
// Opening: Second
// Opening: Third
// Using resources
// Closing: Third
// Closing: Second
// Closing: First
```

---

## 21. Null Keys and Values in HashMap and HashSet

### HashMap - Null Keys and Values

| Feature | Allowed? |
|---------|----------|
| **Null Key** | Yes, only ONE null key |
| **Null Values** | Yes, multiple null values |

### Mechanism for Storing Null Key in HashMap
```java
// HashMap handles null key specially
// In put() method:
if (key == null)
    return putForNullKey(value); // Stored at index 0 (bucket 0)

// The null key is always stored at bucket index 0
// No hashCode() is called for null key
```

### HashMap Example
```java
HashMap<String, String> map = new HashMap<>();
map.put(null, "value1");      // Allowed - null key
map.put("key1", null);        // Allowed - null value
map.put("key2", null);        // Allowed - another null value
map.put(null, "value2");      // Overwrites previous null key entry

System.out.println(map.get(null)); // Output: value2
System.out.println(map.size());    // Output: 3
```

### HashSet - Null Elements

| Feature | Allowed? |
|---------|----------|
| **Null Element** | Yes, only ONE null element |

### Mechanism for Storing Null in HashSet
- HashSet is backed by HashMap internally.
- Elements are stored as keys in the HashMap with a dummy value (`PRESENT`).
- Since HashMap allows one null key, HashSet allows one null element.

```java
// HashSet internally:
private transient HashMap<E, Object> map;
private static final Object PRESENT = new Object();

public boolean add(E e) {
    return map.put(e, PRESENT) == null; // Element becomes the key
}
```

### HashSet Example
```java
HashSet<String> set = new HashSet<>();
set.add("one");
set.add(null);     // Allowed - null element
set.add(null);     // No effect - duplicate null
set.add("two");

System.out.println(set.contains(null)); // Output: true
System.out.println(set.size());         // Output: 3 (one, null, two)
```

### ConcurrentHashMap - No Null Allowed
```java
ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put(null, "value"); // NullPointerException
concurrentMap.put("key", null);   // NullPointerException
```

### Why ConcurrentHashMap Doesn't Allow Null?
- Ambiguity: `map.get(key)` returning null could mean key doesn't exist OR value is null.
- In concurrent environment, `containsKey()` check followed by `get()` is not atomic.
- Null was intentionally prohibited to avoid these issues in concurrent scenarios.

