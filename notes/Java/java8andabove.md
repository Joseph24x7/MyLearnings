# Java Core Concepts (Java 8 → Java 25)

---

## 1. Java 8 to Java 25 Features

### -> Java 8 Features:
- Lambda Expressions, Streams API, Functional Interfaces
- Default and Static Methods in Interfaces
- Optional Class - used to do null check in a more structured way.
- Date and Time API (java.time package)
- Improved Concurrency APIs - CompletableFuture, Fork/Join Framework
- Method References - basically a compact and easy form of the lambda expression
- Metaspace (replacing PermGen)

### -> Java 11 Features (from Java 8 → 11):
- var Keyword for Local Variable Type Inference
- HTTP Client (standardized from incubator)
- Epsilon (No-Op) Garbage Collector
- Immutable Collections (List.of(), Set.of(), Map.of())

### -> Java 17 Features (from Java 11 → 17):
- records - immutable data carriers
- Sealed Classes and Interfaces - Allows you to control which classes can extend or implement them.
- Pattern Matching for instanceof
- Switch Expressions (standard feature)
- Text Blocks (multi-line strings)
- Z Garbage Collector (Production Ready)

### -> Java 21 Features (from Java 17 → 21):
- Virtual Threads (Project Loom) – lightweight concurrency
- Sequenced Collections (ordered access to lists, sets, maps)
- Record Patterns (enhanced pattern matching)
- Pattern Matching for switch (standardized)
- Default UTF-8 Charset
- Generational ZGC and performance improvements

### -> Java 25 Features (from Java 21 → 25):
- Flexible Constructor Bodies Initialization
- Module Import Declarations
- Compact Source Files & Instance Main Methods
- Compact Object Headers
- Generational Shenandoah GC
- Key Derivation Function API
- Scoped Values

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
