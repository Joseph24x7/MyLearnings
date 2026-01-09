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
- **Extension Class Loader:** Loads classes from the JDK extensions directory (e.g., `javax.swing`).
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
