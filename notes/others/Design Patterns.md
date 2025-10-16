# Design Patterns in Software Engineering

## 1. Strategic Design Pattern
- Domain-Driven Design (DDD) approach
- Key components:
  - Bounded Contexts
  - Context Mapping
  - Ubiquitous Language
- Benefits:
  - Better system organization
  - Clear domain boundaries
  - Improved communication

## 2. Fluent Interface Pattern
- Method chaining approach
- Example:
```java
StringBuilder builder = new StringBuilder()
    .append("Hello")
    .append(" ")
    .append("World")
    .toString();
```
- Benefits:
  - Readable code
  - Intuitive API
  - Reduced verbosity

## 3. Facade Design Pattern
- Provides simplified interface
- Hides complex subsystem
- Example:
```java
public class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;
    
    public void start() {
        cpu.freeze();
        memory.load();
        hardDrive.read();
        cpu.execute();
    }
}
```

## 4. Proxy Design Pattern
- Controls access to objects
- Types:
  - Virtual Proxy
  - Protection Proxy
  - Remote Proxy
- Use cases:
  - Lazy loading
  - Access control
  - Remote resource management

## 5. Factory and Abstract Factory
### Factory Method
- Creates objects without exposing creation logic
```java
public interface Animal {
    void makeSound();
}

public class AnimalFactory {
    public Animal createAnimal(String type) {
        if ("dog".equals(type)) return new Dog();
        if ("cat".equals(type)) return new Cat();
        return null;
    }
}
```

### Abstract Factory
- Creates families of related objects
- Provides interface for creating objects
- Ensures compatibility between objects

## 6. Builder Design Pattern
- Constructs complex objects step by step
- Example:
```java
public class Computer {
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
    }
    
    public static class Builder {
        private String cpu;
        private int ram;
        private int storage;
        
        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
    }
}
```
- Benefits:
  - Flexible object construction
  - Immutable objects
  - Clear parameter naming
