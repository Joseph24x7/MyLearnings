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