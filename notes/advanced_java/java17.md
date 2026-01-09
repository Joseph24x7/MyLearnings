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
- Ideal for data transfer objects (DTOs) and value objects.
- Promotes immutability and reduces boilerplate code.
- Can have custom methods and constructors, but fields are final.
- Can implement interfaces but cannot extend other classes.
- Example with custom method:
  ```java
  public record Point(int x, int y) {
      public double distanceFromOrigin() {
          return Math.sqrt(x * x + y * y);
      }
  }
  ```
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
- Useful for modeling closed hierarchies where you want to limit the types that can extend a base class or implement an interface.
- Example with non-sealed subclass:
  ```java
  public sealed class Shape permits Circle, Rectangle, Triangle {}
    public final class Circle extends Shape {}
    public final class Rectangle extends Shape {}
    public non-sealed class Triangle extends Shape {}
  ```
- Non-sealed classes can be extended by any class. If a permitted child is declared non-sealed, it is intentionally opening the hierarchy.
- Sealed classes can improve performance by allowing the compiler to make optimizations based on the known set of subclasses.

---
## 4. Pattern Matching for instanceof
- Simplifies the common pattern of using `instanceof` followed by a cast.
- Example:
  ```java
  if (obj instanceof String str) {
      System.out.println("String length: " + str.length());
  }
  ```
- The variable `str` is automatically cast to `String` within the if block.
- Reduces boilerplate code and improves readability.
- Works with `switch` statements as well (introduced in later versions).
- Example with switch:
  ```java
  switch (obj) {
      case String str -> System.out.println("String length: " + str.length());
      case Integer i -> System.out.println("Integer value: " + i);
      default -> System.out.println("Unknown type");
  }
  ```
- Enhances type safety by ensuring the variable is only accessible within the appropriate scope.

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

## 6. Z Garbage Collector (ZGC)
- A scalable, low-latency garbage collector designed for applications requiring large heaps (multi-terabyte).
- Aims to keep pause times below 10 milliseconds, regardless of heap size.
- Uses concurrent and parallel techniques to minimize the impact of garbage collection on application performance.
- Features:
  - Concurrent marking and relocation of objects.
  - Region-based memory management.
  - Support for large heaps (up to several terabytes).
- Ideal for applications with high throughput and low latency requirements, such as real-time systems and large data processing applications.
- Configurable via JVM options, e.g., `-XX:+UseZGC` to enable ZGC.
- Continuously evolving with improvements in performance and capabilities in subsequent Java versions.

---
