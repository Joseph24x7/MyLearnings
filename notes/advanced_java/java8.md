# Java Core Concepts (Java 8 → Java 25)

---

## 1. Java 8 Features:
- Lambda Expressions, Streams API, Functional Interfaces
- Default and Static Methods in Interfaces
- Optional Class - used to do null check in a more structured way.
- Date and Time API (java.time package)
- Improved Concurrency APIs - CompletableFuture, Fork/Join Framework
- Method References - basically a compact and easy form of the lambda expression
- Metaspace (replacing PermGen)

---

## 2. Intermediate vs Terminal Operations

### Intermediate Operations:
- `filter()`, `map()`, `flatMap()`, `sorted()`, `peek()`
- Lazy-loaded; requires terminal operations to be executed

### Terminal Operations:
- `forEach()`, `collect()`, `min()`, `max()`, `count()`
- Eager loaded; applied over intermediate operations to produce a result

---

## 3. Examples of Some Predefined Functional Interfaces

### Consumer<T>: (used in list.foreach())
- Represents an operation that takes an input argument of type T and returns no result.
- Example: `Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());`

### Supplier<T>: (used in orElseGet() which is part of optional)
- It does not take any input but produces a result of type T.
- Example: `Supplier<Integer> getRandomNumber = () -> new Random().nextInt();`

### Function<T, R>: (used in stream().map())
- Represents a function that takes an input of type T and produces a result of type R.
- Example: `Function<String, Integer> strLength = str -> str.length();`

### Predicate<T>: (used in stream().filter())
- Represents a predicate (boolean-valued function) that takes an input of type T.
- Example: `Predicate<Integer> isEven = num -> num % 2 == 0;`

### Comparator<T>:
- Used for comparing two objects to determine their order.
- Example: `Comparator<String> byLength = (s1, s2) -> Integer.compare(s1.length(), s2.length());`

### Runnable/Callable:
- Represents a task that can be executed, with Runnable not returning a result and Callable returning a result.
- Example: `Runnable task = () -> System.out.println("Task executed");`
- Example: `Callable<Integer> callableTask = () -> 42;`

---

## 4. parallelStream() vs CompletableFuture

### parallelStream():
- It is a part of the Java Stream API and is used to process collections in parallel.
- It automatically divides the workload into multiple threads, making it easier to work with collections.
- Example:
  ```java
  List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
  int sum = numbers.parallelStream().mapToInt(Integer::intValue).sum();
  ```

### CompletableFuture:
- It is a part of the java.util.concurrent package and provides a way to handle asynchronous programming.
- It allows you to create, combine, and manage multiple asynchronous tasks with more control.
- Example:
  ```java
  CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      // Simulate a long-running task
      return 42;
  });
  int result = future.get();
  ```

## 5. Consider class C implements interfaces A,B and has default methods. How to call the default method on interface A from C, when A and B has same default method?
- You can use the syntax `A.super.methodName()` to call the default method from interface A. For example:
```java
interface A {
    default void display() {
        System.out.println("Default method from A");
    }
}
interface B {
    default void display() {
        System.out.println("Default method from B");
    }
}
class C implements A, B {
    @Override
    public void display() {
        A.super.display(); // Calls default method from interface A
    }
}
```
---

## 6. What is Metaspace in Java 8? How is it different from PermGen?
- Metaspace is the memory space in Java 8 and later versions that stores class metadata.
- It replaces the PermGen space used in earlier versions of Java.
- Metaspace is allocated in native memory, while PermGen was part of the heap memory.
- Native memory refers to the memory directly managed by the operating system/CPU.
- Metaspace can dynamically grow as needed, whereas PermGen had a fixed size that could lead to `OutOfMemoryError` if exceeded.
- configurable via JVM options like `-XX:MaxMetaspaceSize`.

## 7. How to implement the chain of predicate operations in java 8?
- You can implement a chain of predicate operations using the `and()`, `or()`, and `negate()` methods provided by the `Predicate` interface. Here's an example:
```java
Predicate<Integer> isEven = num -> num % 2 == 0;
Predicate<Integer> isGreaterThanTen = num -> num > 10;
Predicate<Integer> isEvenAndGreaterThanTen = isEven.and(isGreaterThanTen);
boolean result = isEvenAndGreaterThanTen.test(12); // true
```
- `negate()` can be used to reverse the condition of a predicate:
```java
Predicate<Integer> isOdd = isEven.negate();
boolean result = isOdd.test(3); // true
```

---

## 8. How to use andThen and compose methods from functional interfaces?
- The `andThen()` and `compose()` methods are used to combine functions in Java 8.
- `andThen()` is used to apply one function after another, while `compose()` is used to apply one function before another.
- `andThen()` works with both Consumer and Function interfaces, while 'compose()' works only with Function interface.
- Example of `andThen()`:
```java
Function<Integer, Integer> multiplyByTwo = x -> x * 2;
Function<Integer, Integer> addThree = x -> x + 3;
Function<Integer, Integer> combinedFunction = multiplyByTwo.andThen(addThree);
int result = combinedFunction.apply(5); // (5 * 2) + 3 = 13
```
- Example of `compose()`:
```java
Function<Integer, Integer> multiplyByTwo = x -> x * 2;
Function<Integer, Integer> addThree = x -> x + 3;
Function<Integer, Integer> combinedFunction = addThree.compose(multiplyByTwo);
int result = combinedFunction.apply(5); // (5 * 2) + 3 = 13
```
---

## 9. Supplier vs Consumer

### Supplier<T>
- **Purpose:** Supplies/produces a value without taking any input.
- **Method:** `T get()`
- **Use Case:** Lazy initialization, factory methods, generating values.

```java
Supplier<Double> randomSupplier = () -> Math.random();
System.out.println(randomSupplier.get()); // Outputs random number

Supplier<LocalDate> dateSupplier = LocalDate::now;
System.out.println(dateSupplier.get()); // Outputs current date
```

### Consumer<T>
- **Purpose:** Consumes/accepts a value without returning anything.
- **Method:** `void accept(T t)`
- **Use Case:** Performing actions on data, logging, printing.

```java
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello World"); // Outputs: Hello World

Consumer<List<Integer>> listPrinter = list -> list.forEach(System.out::println);
listPrinter.accept(Arrays.asList(1, 2, 3)); // Outputs: 1 2 3
```

### Key Differences

| Feature | Supplier<T> | Consumer<T> |
|---------|-------------|-------------|
| **Input** | None | Takes one input |
| **Output** | Returns a value | Returns nothing (void) |
| **Method** | `T get()` | `void accept(T t)` |
| **Use Case** | Producing/generating values | Consuming/processing values |

---

## 10. What is a Functional Interface?

### Definition
- An interface that contains **exactly one abstract method**.
- Can have any number of default and static methods.
- Can be used as the target for lambda expressions and method references.
- Optionally annotated with `@FunctionalInterface` (recommended for compile-time checking).

### Examples of Built-in Functional Interfaces
```java
// Predicate<T> - boolean test(T t)
Predicate<Integer> isPositive = n -> n > 0;

// Function<T, R> - R apply(T t)
Function<String, Integer> stringLength = String::length;

// Consumer<T> - void accept(T t)
Consumer<String> printer = System.out::println;

// Supplier<T> - T get()
Supplier<Double> random = Math::random;

// BiFunction<T, U, R> - R apply(T t, U u)
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
```

### Custom Functional Interface
```java
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b); // Single abstract method
    
    // Can have default methods
    default int add(int a, int b) {
        return a + b;
    }
    
    // Can have static methods
    static int multiply(int a, int b) {
        return a * b;
    }
}

// Usage with lambda
Calculator subtractor = (a, b) -> a - b;
System.out.println(subtractor.calculate(10, 5)); // Output: 5
```

---

## 11. map() vs flatMap()

### map()
- **Purpose:** Transforms each element to exactly one output element.
- **Signature:** `<R> Stream<R> map(Function<T, R> mapper)`
- **Use Case:** One-to-one transformation.

```java
List<String> names = Arrays.asList("john", "jane", "doe");
List<String> upperNames = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
// Output: [JOHN, JANE, DOE]
```

### flatMap()
- **Purpose:** Transforms each element to zero or more elements and flattens the result.
- **Signature:** `<R> Stream<R> flatMap(Function<T, Stream<R>> mapper)`
- **Use Case:** One-to-many transformation, flattening nested structures.

```java
List<List<Integer>> nestedList = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4),
    Arrays.asList(5, 6)
);

// Using map - returns Stream<List<Integer>>
List<Stream<Integer>> mapped = nestedList.stream()
    .map(List::stream)
    .collect(Collectors.toList());

// Using flatMap - returns Stream<Integer> (flattened)
List<Integer> flattened = nestedList.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
// Output: [1, 2, 3, 4, 5, 6]
```

### Key Differences

| Feature | map() | flatMap() |
|---------|-------|-----------|
| **Transformation** | One-to-one | One-to-many (then flatten) |
| **Output** | Single element per input | Zero or more elements per input |
| **Flattening** | No | Yes |
| **Use Case** | Simple transformations | Nested structures, Optional handling |

### flatMap with Optional
```java
Optional<String> getName(int id) { /* ... */ }

// Without flatMap - returns Optional<Optional<String>>
Optional<Optional<String>> nested = Optional.of(1).map(this::getName);

// With flatMap - returns Optional<String>
Optional<String> flat = Optional.of(1).flatMap(this::getName);
```

---

## 12. Why Static and Default Methods in Interface vs Abstract Class

### Why Default Methods in Interface?
1. **Backward Compatibility:** Add new methods to interfaces without breaking existing implementations.
2. **Multiple Inheritance of Behavior:** Classes can inherit default implementations from multiple interfaces.
3. **Optional Methods:** Provide default behavior that implementations can override if needed.

### Why Static Methods in Interface?
1. **Utility Methods:** Group related utility methods with the interface.
2. **Factory Methods:** Create instances of implementing classes.
3. **No Inheritance:** Static methods cannot be overridden, ensuring consistent behavior.

### Interface vs Abstract Class

| Feature | Interface (with default/static) | Abstract Class |
|---------|--------------------------------|----------------|
| **Multiple Inheritance** | Yes (can implement multiple interfaces) | No (can extend only one class) |
| **State (Instance Variables)** | Only constants (public static final) | Can have instance variables |
| **Constructor** | No constructors | Can have constructors |
| **Access Modifiers** | Methods are public by default | Can have any access modifier |
| **Use Case** | Define contracts with optional default behavior | Share common state and behavior |

### Example
```java
public interface Vehicle {
    void start(); // Abstract method
    
    // Default method - backward compatible addition
    default void honk() {
        System.out.println("Beep beep!");
    }
    
    // Static utility method
    static int getWheelCount(String type) {
        return switch (type) {
            case "car" -> 4;
            case "bike" -> 2;
            default -> 0;
        };
    }
}
```

---

## 14. collect(Collectors.toList()) vs Stream.toList()

| Feature | collect(Collectors.toList()) | toList() (Java 16+) |
|---------|------------------------------|---------------------|
| **Introduced** | Java 8 | Java 16 |
| **Result Type** | Mutable ArrayList | Unmodifiable List |
| **Null Elements** | Allows null elements | Does NOT allow null elements |
| **Modification** | Can add/remove elements | Throws UnsupportedOperationException |

### Example
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// collect(Collectors.toList()) - Returns mutable list
List<Integer> mutableList = numbers.stream()
    .filter(n -> n > 2)
    .collect(Collectors.toList());
mutableList.add(10); // Works fine

// toList() - Returns unmodifiable list (Java 16+)
List<Integer> immutableList = numbers.stream()
    .filter(n -> n > 2)
    .toList();
immutableList.add(10); // Throws UnsupportedOperationException
```

### Best Practice
- Use `toList()` when you need an immutable result (safer, more concise).
- Use `collect(Collectors.toList())` when you need to modify the result list.
- Use `collect(Collectors.toUnmodifiableList())` for immutable list in Java 10+.

---

## 15. Stream Reuse - Will This Execute?

### Code
```java
List<Integer> lists = List.of(1, 2, 3, 4, 5, 6);
Stream<Integer> integerStream = lists.stream().map(i -> i * 2);
integerStream.forEach(System.out::println);
List<Integer> finalList = integerStream.collect(Collectors.toList());
```

### Answer: **IllegalStateException** - Stream has already been operated upon or closed

- **Streams can only be consumed once.**
- After a terminal operation (`forEach`, `collect`, `reduce`, etc.), the stream is closed.
- Attempting to reuse the stream throws `IllegalStateException`.

### Correct Approach
```java
List<Integer> lists = List.of(1, 2, 3, 4, 5, 6);

// Option 1: Create stream each time
lists.stream().map(i -> i * 2).forEach(System.out::println);
List<Integer> finalList = lists.stream().map(i -> i * 2).collect(Collectors.toList());

// Option 2: Store the supplier
Supplier<Stream<Integer>> streamSupplier = () -> lists.stream().map(i -> i * 2);
streamSupplier.get().forEach(System.out::println);
List<Integer> finalList = streamSupplier.get().collect(Collectors.toList());
```

