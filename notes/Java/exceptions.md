# Java Exceptions and Errors

---

## 1. Steps to Fix OutOfMemoryError
- Optimize the data structures by reducing the time & space complexity.
- Check for memory leaks (not closing connections/resources).
- Use profiling tools to visualize memory usage (like VisualVM, YourKit, JProfiler).
- Increase Heap Size or PermGen/Metaspace Size.
- Increase the cloud or hardware instances.

---

## 2. Can You Replicate StackOverflowError? If Yes, How
- A `StackOverflowError` occurs when the call stack, which is used to keep track of method calls, becomes too deep.

- Example:
```java
public class StackOverflowExample {
    public static void main(String[] args) {
        recursiveMethod();
    }

    public static void recursiveMethod() {
        recursiveMethod(); // This recursive call will eventually lead to a StackOverflowError.
    }
}
```

---

## 3. Can You Replicate OutOfMemoryError? If Yes, How
- One common way to trigger an OutOfMemoryError is by creating a large number of objects or allocating a large amount of memory that exceeds the available heap space.

- Example:
```java
public class OutOfMemoryExample {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();

        try {
            while (true) {
                list.add(new byte[1_000_000]); // Allocate 1MB of memory for each object
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError occurred!");
        }
    }
}
```

---

## 4. Difference Between StackOverflowError and OutOfMemoryError
- **OutOfMemoryError**: Happens when the JVM runs out of heap memory for object allocation.
- **StackOverflowError**: Occurs when method calls become too deep, usually due to uncontrolled recursion.
