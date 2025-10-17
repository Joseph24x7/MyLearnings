# Memory Management in Java

## 1. Types of memory

- Heap Memory: It is where objects, including instances of classes and arrays, are allocated during runtime. 

- Stack Memory: 
	- Contains the reference to that heap object. Stack memory is generally smaller and faster to access than heap memory.
	- Primitive data type, stored in stack memory
	- Local variables stored in the stack memory

- Method Area (PermGen/Metaspace): 
	- This area is responsible for storing class definitions, method and field information, and other metadata.
	- In Java 8 and later versions, the "PermGen" space was replaced with "Metaspace," which serves a similar purpose but is not limited by a fixed size and can dynamically expand. 

- Native Memory: Native memory is typically used for purposes like managing I/O, native method calls, and direct buffers.

- Program Counter (PC) Register: Each thread in Java has its own Program Counter (PC) register, which keeps track of the currently executing instruction.

- Registers: Registers are small, fast storage locations within the CPU and it is not directly managed by Java developers, they play a crucial role in the execution of Java code.

Note: Except Native Memory and Registers, Rest of all are managed by JVM.

---

## 2. Time Complexity:

- O(1) - Constant Time Complexity - it takes a constant amount of time to retrieve the first element.
- O(N) - Linear Time Complexity - This means that the runtime of an algorithm or code grows linearly with the size of the input.
- O(N^2) - Quadratic Time Complexity - This means that the runtime of an algorithm or code grows with the square of the input size.
- O(log N) - Logarithmic Time Complexity - This means that the runtime of an algorithm or code increases logarithmically with the input size.

---

## 3. Space Complexity:

- O(1) - Constant Space Complexity - This means that the amount of memory used by an algorithm or code does not depend on the size of the input data; it's constant.
- O(N) - Linear Space Complexity - This means that the memory usage of an algorithm or code grows linearly with the size of the input.

---

## 4. Cyclometric Complexity:

- Cyclomatic complexity is a software metric used to measure the complexity of a program's control flow. 
- In Java, you can calculate cyclomatic complexity using the Control Flow Graph (CFG) of a method. 
- The formula for cyclomatic complexity is: M = E - N + 2P ( E- Edges, N - Nodes or Blocks, P-connectors/pipes)

---

## 5. What steps u will take to fix outofmemoryerror:

- Optimize the Data Structures by reducing the time & space complexity.
- Check for Memory Leaks ( not closing the connections/resources )
- Increase Heap Size (or) Increase PermGen/Metaspace Size
- Increase the cloud or hardware instances.

---

## 6. Heap Memory vs PermGen/Metaspace memory:

- PermGen/Metaspace: This area is responsible for storing class definitions, method and field information, and other metadata.
	- XX:MaxPermSize or -XX:MaxMetaspaceSize
- Heap Memory: It is where objects, including instances of classes and arrays, are allocated during runtime.
	- java -Xmx1024m -Xms512m
- Both can be modified in VM Arguements in IDEs for the Java Applications.

---

## 7. "PermGen" vs "Metaspace" (OR) Memory upgrades in Java 8 and above: 

- In older versions of Java (up to Java 7), there was a "PermGen" (Permanent Generation) area for storing class metadata and constant pool information. 
- In Java 8 and later versions, the PermGen space was replaced with "Metaspace," which serves a similar purpose.
- "PermGen" has the limited space and fixed size whereas "Metaspace" can dynamically expand.
- Both are responsible for storing class definitions, method and field information, and other metadata.

---

## 8. What is memory leak? How to analyze and Fix?

- A memory leak in a software application occurs when the application allocates memory for objects or data but fails to release or deallocate that memory when it's no longer needed.
- It will lead to an "OutOfMemoryError" or degrade system performance.
- Performance Testing has to be done to identify such issues.
- may be because we may not be closing the connections/resources (or) deallocate any memory. fixing this will avoid memory leak.

---

## 9. can we create internal memory in jvm?

- We can modify the size of "heap" / "PermGen" / "Metaspace" memory.
- But we cannot create our own internal memory.

---

## 10. can u replicate stackoverflowerror on our own? if yes how

- A StackOverflowError occurs when the call stack, which is used to keep track of method calls, becomes too deep.

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

## 11. can u replicate outofmemoryerror on our own? if yes how

- One common way to trigger an OutOfMemoryError is by creating a large number of objects or allocating a large amount of memory that exceeds the available heap space. 

```java
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
```

---

## 12. dfifference between stackoverflow and outofmemoryerror?

- OutOfMemoryError: Happens when the JVM runs out of heap memory for object allocation.
- StackOverflowError: Occurs when method calls become too deep, usually due to uncontrolled recursion.

---
