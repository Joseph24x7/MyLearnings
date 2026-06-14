# Java Core Concepts (Java 11 → 17)

---

## 1. Java 17 Features (from Java 11 → 17):

- records - immutable data carriers
- Sealed Classes and Interfaces - Allows you to control which classes can extend or implement them.
- Pattern Matching for instanceof
- Switch Expressions (standard feature)
- Text Blocks (multi-line strings)
- Z Garbage Collector (ZGC)

---

## 2. Records

- Special kind of class in Java that acts as a transparent carrier for immutable data.
- Automatically provides implementations for methods like `equals()`, `hashCode()`, and `toString()`.
- Example:
  ```java
  public record Point(int x, int y) {}
  ```
- Usage:
  ```java
    Point p = new Point(1, 2);
    System.out.println(p.x()); // Accessor method
    ```
- Records are final and cannot be extended.
- Can have custom methods and constructors, but fields are final.

 ```java
  public record Point(int x, int y) {
    public double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }
}
  ```

- Can implement interfaces but cannot extend other classes.
- Non-final classes could be modified, that's why records doesn't allow inheritance with classes.
- Example with custom method:

---

## 3. Sealed Classes and Interfaces

- Allow you to control which classes can extend or implement them.
- Use the `sealed` keyword to declare a sealed class or interface.
- Use the `permits` clause to specify the allowed subclasses.
- Example:
  ```java
  public sealed class Shape permits Circle, Rectangle {}
    public final class Circle extends Shape {}
    public final class Rectangle extends Shape {}
    ```
- Subclasses must be declared as `final`, `sealed`, or `non-sealed`.
- Enhances encapsulation and maintainability by restricting class hierarchies.
- Useful for modeling closed hierarchies where you want to limit the types that can extend a base class or implement an
  interface.
- Example with non-sealed subclass:
  ```java
  public sealed class Shape permits Circle, Rectangle, Triangle {}
    public final class Circle extends Shape {}
    public final class Rectangle extends Shape {}
    public non-sealed class Triangle extends Shape {}
  ```
- Non-sealed classes can be extended by any class. If a permitted child is declared non-sealed, it is intentionally
  opening the hierarchy.
- Sealed classes can improve performance by allowing the compiler to make optimizations based on the known set of
  subclasses.

---

## 4. Pattern Matching for instanceof

- Simplifies the common pattern of using `instanceof` followed by a cast, which Reduces boilerplate code and improves readability.
- Enhances type safety by ensuring the variable is only accessible within the appropriate scope.
- Example:
  ```java
  if (obj instanceof String str) {
      System.out.println("String length: " + str.length());
  }
  ```
- The variable `str` is automatically cast to `String` within the if block.
- Works with `switch` statements as well (introduced in later versions).
- Example with switch:
  ```java
  switch (obj) {
      case String str -> System.out.println("String length: " + str.length());
      case Integer i -> System.out.println("Integer value: " + i);
      default -> System.out.println("Unknown type");
  }
  ```

---

## 5. Text Blocks

- Introduced to simplify the creation of multi-line string literals.
- Use triple quotes `"""` to define a text block.
- Example:
  ```java
  String json = """
      {
          "name": "John",
          "age": 30,
          "city": "New York"
      }""";
  ```
- Preserves the formatting and indentation of the text block.
- Reduces the need for escape sequences for quotes and newlines.
- Improves readability for large strings, such as JSON, XML, or SQL queries.
- Supports expressions and concatenation.
- Example with expression:
  ```java
  String name = "John";
  String greeting = """
      Hello, %s!
      Welcome to the system.
      """.formatted(name);
  ```
- Text blocks automatically handle line terminators based on the platform.

---

## 6. Java 17 Features In Practice: When & What Have We Used?

When discussing Java 17 in interviews, highlight these real-world uses:

1. **Records:** Used extensively for **DTOs (Data Transfer Objects)**, Controller API Request/Response models, and database projection payloads. They eliminate boilerplate like `equals()`, `hashCode()`, `toString()`, getters, and constructors.
2. **Text Blocks (`"""`):** Used to write readable, multi-line native SQL queries, JSON payloads for integration tests, or HTML email templates directly inside the code without ugly string concatenation.
3. **Switch Expressions:** Simplifies complex conditional routing by returning values directly from switch branches.
4. **Pattern Matching for `instanceof`:** Cleans up boilerplate code in custom converters or exception handlers by automatically casting the matched variable.
5. **Sealed Classes:** Used to model strict domain hierarchies (e.g. `PaymentStatus` permits `Success`, `Failed`, `Pending`) ensuring no arbitrary subclassing can bypass safety boundaries.

---

## 7. Deep Dive: What are Records & When to Use Them?

A **Record** is a special type of class in Java (introduced as standard in Java 16/17) designed to be a transparent, immutable data carrier.

### Key Characteristics:
- Instantiated like a class: `public record UserDto(String username, int age) {}`
- Automatically generates:
  - Private, final fields.
  - Getter methods (using field names, e.g., `user.username()` instead of `getUsername()`).
  - Canonical constructor initializing all fields.
  - `equals()`, `hashCode()`, and `toString()` based on the state fields.
- Cannot extend other classes (they implicitly extend `java.lang.Record`), and are automatically `final`.

### When to Use Records:
- ✅ **DTOs & API Payloads:** Perfect for transferring read-only data between controller, service, and external services.
- ✅ **Map Keys:** Since they are immutable and have a built-in `hashCode()` and `equals()`, they make safe keys in a `HashMap`.
- ✅ **Local Tuples:** Excellent for returning multiple values from a method locally without declaring a heavy boilerplate class.

### When NOT to Use Records:
- ❌ **JPA Entities:** Hibernate/JPA requires entities to have non-final fields, a no-args constructor, and proxying capabilities (which `final` classes do not allow).
- ❌ **Mutable state:** If the fields need to be changed after initialization, use a standard mutable class.