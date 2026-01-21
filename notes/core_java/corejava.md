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