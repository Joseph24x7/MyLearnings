# Important Design Patterns:

---

## 1. Singleton Pattern
- Ensures a class has only one instance and provides a global point of access to it.
- Example:
  ```java
  public class Singleton {
      private static Singleton instance;
      private Singleton() {}
      public synchronized static Singleton getInstance() {
          if (instance == null) {
              instance = new Singleton();
          }
          return instance;
      }
  }
    ```
- Use Cases: Configuration management, logging, caching.

## 2. Factory Pattern
- Provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created.
- Example:
  ```java
  public interface Shape {
      void draw();
  }
    public class Circle implements Shape {
        public void draw() {
            System.out.println("Drawing Circle");
        }
    }
    public class Square implements Shape {
        public void draw() {
            System.out.println("Drawing Square");
        }
    }
    public class ShapeFactory {
        public Shape getShape(String shapeType) {
            if (shapeType == null) {
                return null;
            }
            if (shapeType.equalsIgnoreCase("CIRCLE")) {
                return new Circle();
            } else if (shapeType.equalsIgnoreCase("SQUARE")) {
                return new Square();
            }
            return null;
        }
    }
    ```
- Use Cases: Object creation based on dynamic input, managing and maintaining object creation logic.
- Frequently used Predefined Factory Classes in Java: 
  - `NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US)`, 
  - `Calendar cal = Calendar.getInstance()`, 
  - `Optional<String> op = Optional.of("Java");`

## 3. Observer Pattern
- Realtime uses of Observer pattern is Publish-Subscribe systems like Apache Kafka, RabbitMQ etc.
- Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
  - Example:
    ```java
    import java.util.ArrayList;
    import java.util.List;
      interface Observer {
          void update(String message);
      }
      class Subject {
          private List<Observer> observers = new ArrayList<>();
          public void attach(Observer observer) {
              observers.add(observer);
          }
          public void notifyAllObservers(String message) {
              for (Observer observer : observers) {
                  observer.update(message);
              }
          }
      }
      class ConcreteObserver implements Observer {
          private String name;
          public ConcreteObserver(String name) {
              this.name = name;
          }
          public void update(String message) {
              System.out.println(name + " received: " + message);
          }
      }
      ```
- Use Cases: Event handling systems, real-time data feeds, notification systems.

## 4. Strategy Pattern
- Defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.
- Example:
  ```java
  interface Strategy {
      int doOperation(int num1, int num2);
  }
    class Addition implements Strategy {
        public int doOperation(int num1, int num2) {
            return num1 + num2;
        }
    }
    class Subtraction implements Strategy {
        public int doOperation(int num1, int num2) {
            return num1 - num2;
        }
    }
    class Context {
        private Strategy strategy;
        public Context(Strategy strategy) {
            this.strategy = strategy;
        }
        public int executeStrategy(int num1, int num2) {
            return strategy.doOperation(num1, num2);
        }
    }
    ```
- Use Cases: Sorting algorithms, payment methods, data compression techniques.
- Frequently used Predefined Strategy Classes in Java:
- `Comparator<T>` used in sorting collections, 
  ```java
  List<String> list = Arrays.asList("Banana", "Apple", "Cherry");
  list.sort(Comparator.naturalOrder());   // Strategy 1
  list.sort(Comparator.reverseOrder());   // Strategy 2
    ```
