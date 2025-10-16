# Java Multithreading Concepts

## 1. What is multithreading vs multiprocessing?

### Multiprocessing
- Multiple independent processes run concurrently
- Runs on multicore or multiprocessor systems
- Each process has its own memory space

### Multithreading
- Single process divided into multiple threads
- Threads execute independently and concurrently
- Shares process memory space

## 2. What is a race condition?
- Occurs when multiple threads access shared resources concurrently
- Final outcome depends on timing/order of execution
- Can cause:
  - Data corruption
  - Program crashes
  - Incorrect behavior

### Example
- Bank account withdrawal scenario
- Multiple threads trying to withdraw money simultaneously
- Balance might become incorrect due to concurrent access

## 3. What are the ways to create threads and their advantages?

### 1. Extending Thread Class
```java
class ThreadDemo extends Thread {
    @Override
    public void run() {
        // Thread logic here
    }
}
```
#### Advantages
- Direct access to Thread methods
- Simple implementation
- Good for single inheritance scenarios

### 2. Implementing Runnable Interface
```java
class RunnableDemo implements Runnable {
    @Override
    public void run() {
        // Thread logic here
    }
}
```
#### Advantages
- Allows other inheritance
- Better for object-oriented design
- More flexible implementation

### 3. Using ExecutorService
```java
ExecutorService executor = Executors.newFixedThreadPool(5);
executor.submit(() -> {
    // Thread logic here
});
```
#### Advantages
- Thread pool management
- Better resource control
- Task queuing capabilities
