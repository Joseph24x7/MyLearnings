# Java Immutability & Design Patterns

---

## 1. Advantages of Being Immutable


- **Security:** As the value cannot be modified, reducing the risk of data tampering.
- **Caching:** Java can optimize memory usage by reusing string instances from String pool.
- **Hashing:** Immutable strings can be used as keys in hash tables (e.g., HashMap) because their hash code remains constant.

---


## 2. How to Create an Immutable Class

- **Custom way**:
  - class as `final`
  - Make all fields `private` and `final`.
  - Don't provide setter methods.
  - Initialize all fields via constructor.
  - Perform deep copy for mutable objects in constructor and getters.
- **Java Records**
- **Using Lombok @Value**

---


## 3. Design Patterns & SOLID Principles Quick Reference


### 1. Strategy Pattern
- **Definition:** Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
- **Example:** `Comparator<T>` in Java collections.
```java
// Interchangeable sorting strategies
list.sort(Comparator.naturalOrder());
list.sort(Comparator.reverseOrder());
```

### 2. Fluent Interface Pattern
- **Definition:** An API design that relies on method chaining to make code more readable (often used in builders).
- **Example:**
```java
User user = new UserBuilder()
                .setName("John")
                .setEmail("john@example.com")
                .build();
```

### 3. Facade Pattern
- **Definition:** Provides a simplified interface to a complex subsystem.
- **Example:** A `BookingFacade` simplifying calls to `PaymentService`, `FlightService`, and `HotelService`.
```java
public class BookingFacade {
    public void bookTrip(TripDetails trip) {
        paymentService.pay();
        flightService.reserve();
        hotelService.book();
    }
}
```

### 4. Proxy Pattern
- **Definition:** Provides a placeholder/surrogate for another object to control access to it (e.g. lazy loading, security, logging).
- **Example:** Spring AOP uses JDK Dynamic Proxies or CGLIB to intercept method calls for `@Transactional` or `@Cacheable`.

### 5. Factory vs Abstract Factory Pattern
- **Factory Method:** Creates objects of a single family through inheritance (a method overridden by subclasses).
- **Abstract Factory:** Creates families of related/dependent objects without specifying their concrete classes (a factory of factories).
- **Example:**
```java
// Factory Method
Shape circle = ShapeFactory.getShape("CIRCLE");

// Abstract Factory
Button macButton = GUIFactory.getFactory("MAC").createButton();
```

### 6. Builder Pattern
- **Definition:** Separates the construction of a complex object from its representation, allowing step-by-step creation.
- **Example:**
```java
public class Product {
    private String name;
    private double price;
    
    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
    }
    
    public static class Builder {
        private String name;
        private double price;
        public Builder name(String name) { this.name = name; return this; }
        public Builder price(double price) { this.price = price; return this; }
        public Product build() { return new Product(this); }
    }
}
```

### 7. SOLID Principles Explained Simply

| Principle | Meaning | Real-world Example |
|-----------|---------|--------------------|
| **S**ingle Responsibility (SRP) | A class should have only one reason to change. | `Invoice` class calculates price, `InvoicePrinter` prints it (not combined in one class). |
| **O**pen/Closed (OCP) | Software entities should be open for extension but closed for modification. | Using interfaces so you can add a `UpiPayment` strategy without modifying existing `PaymentProcessor` code. |
| **L**iskov Substitution (LSP) | Subclasses should be substitutable for their base classes without breaking correctness. | If `Ostrich` extends `Bird` but cannot fly, calling `bird.fly()` on an Ostrich throws an exception, violating LSP. Refactor so only `FlyingBird` has the `fly()` method. |
| **I**nterface Segregation (ISP) | Clients should not be forced to depend on interfaces they do not use. | Instead of a giant `Worker` interface with `code()` and `manage()`, split it into `Coder` and `Manager` interfaces. |
| **D**ependency Inversion (DIP) | Depend on abstractions, not on concretions. | Injected services via interfaces, e.g. `@Autowired private PaymentGateway gateway;` instead of `private PayPalGateway gateway = new PayPalGateway();`. |

---


## 4. Creational Design Patterns in Detail

Creational patterns deal with object creation mechanisms, trying to create objects in a manner suitable to the situation.

1. **Singleton:** Restricts instantiation of a class to a single instance.
2. **Factory Method:** Defines an interface for creating a single object, but lets subclasses decide which class to instantiate.
3. **Abstract Factory:** Creates families of related objects.
4. **Builder:** Constructs complex objects step-by-step.
5. **Prototype:** Creates new objects by copying an existing instance (using `clone()`).

---


## 5. How to Make a Class Immutable in Java


To make a class immutable, follow these concrete design steps:
1. **Declare the class as `final`** so it cannot be extended (prevents subclasses from overriding methods and introducing mutability).
2. **Make all fields `private` and `final`** to restrict direct access and enforce single-initialization.
3. **Do not provide setter methods** for any fields.
4. **Initialize all fields via constructor** performing deep copies for mutable objects.
5. **Perform Defensive Copying** in getter methods for mutable fields (e.g. return a copy of a `List` or `Date` object rather than the original reference).

### Simple Code Example:
```java
public final class ImmutableUser {
    private final String name;
    private final List<String> roles; // Mutable object

    public ImmutableUser(String name, List<String> roles) {
        this.name = name;
        // Defensive copy during initialization
        this.roles = new ArrayList<>(roles);
    }

    public String getName() {
        return name;
    }

    public List<String> getRoles() {
        // Defensive copy during retrieval
        return new ArrayList<>(roles);
    }
}
```

---


