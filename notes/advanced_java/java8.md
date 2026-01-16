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

## 2. Why we need functional Interfaces in Java 8?

- Functional interfaces are used to enable functional programming in Java.
- They are used to implement lambda expressions and method references.
- They provide a target type for lambda expressions, allowing for more concise and readable code.
- We can avoid creating anonymous inner classes which is a boiler plate code.

### Why only one abstract method?

- Multiple abstract methods would create ambiguity in lambda expressions.
- It ensures that the functional interface represents a single behavior or action.

---

## 3. Why Static and Default Methods was introduced in Interface in Java 8?

- To provide backward compatibility while adding new methods to interfaces. Otherwise, all the existing implementations
  would break.

### Why Default Methods in Interface?

1. Add new methods to interfaces without breaking existing implementations.
2. Otherwise, all the classes implementing that interface would need to implement the new method.

### Why Static Methods in Interface?

1. Acts as a utility methods with the interface.
2. Create instances of implementing classes using Factory Methods.
3. Static methods cannot be overridden, ensuring consistent behavior.

### Example

```java
public interface Payment {

    void pay(double amount);

    // ✅ Backward compatible - default method
    default void validatePaymentDetails() {
        System.out.println("Validating payment details...");
    }

    // ✅ Utility or Factory method - static method
    static Payment of(String type) {
        return switch (type) {
            case "CARD" -> new CardPayment();
            case "UPI" -> new UpiPayment();
            default -> throw new IllegalArgumentException("Invalid type");
        };
    }
}
```

## 4. Intermediate vs Terminal Operations. Does terminal operation execute only once in a stream with intermediate operation?

### Intermediate Operations:

- `filter()`, `map()`, `flatMap()`, `sorted()`, `peek()`, 'limit()', `skip()`
- Note: Once the terminal operation is invoked, we cannot reuse the stream.

### Terminal Operations:

- `forEach()`, `collect()`, `min()`, `max()`, `count()`
- Eager loaded; applied over intermediate operations to produce a result

### Does terminal operation execute only once in a stream with intermediate operation?

- Yes, terminal operations can only be executed once on a stream.
- After a terminal operation is invoked, the stream is considered consumed and cannot be reused.
- Attempting to reuse the stream will result in an `IllegalStateException`.

---

## 5. Examples of Some Predefined Functional Interfaces

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

## 6. Consider class C implements interfaces A,B and has default methods. How to call the default method on interface A from C, when A and B has same default method?

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

## 7. How to implement the chain of predicate operations in java 8?

- You can implement a chain of predicate operations using the `and()`, `or()`, and `negate()` methods provided by the
  `Predicate` interface. Here's an example:

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
- `andThen()` is used to apply one function after another, while `compose()` is used to apply one function before
  another.
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

## 9. map() vs flatMap()

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

---

## 10. collect(Collectors.toList()) vs Stream.toList()

| Feature           | collect(Collectors.toList()) | toList() (Java 16+)                  |
|-------------------|------------------------------|--------------------------------------|
| **Introduced**    | Java 8                       | Java 16                              |
| **Result Type**   | Mutable ArrayList            | Unmodifiable List                    |
| **Null Elements** | Allows null elements         | Does NOT allow null elements         |
| **Modification**  | Can add/remove elements      | Throws UnsupportedOperationException |

### Best Practice

- Use `toList()` when you need an immutable result (safer, more concise).
- Use `collect(Collectors.toList())` when you need to modify the result list.
- Use `collect(Collectors.toUnmodifiableList())` for immutable list in Java 10+.

---

## 11. Stream Reuse - Will This Execute?

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

- Basically Supplier does NOT create a stream yet,it stores a recipe to create a stream. Every call to get() creates a brand-new Stream. 

---