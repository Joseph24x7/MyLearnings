package com.mylearnings.java.designpatterns.creational;

// Step 1: Define a common interface for the products
interface Product {
    void display();
}

// Step 2: Create concrete implementations of the products
class ConcreteProductA implements Product {
    @Override
    public void display() {
        System.out.println("Product A");
    }
}

class ConcreteProductB implements Product {
    @Override
    public void display() {
        System.out.println("Product B");
    }
}

// Step 3: Create a factory interface
interface ProductFactory {
    Product createProduct();
}

// Step 4: Implement concrete factories
class ConcreteProductAFactory implements ProductFactory {
    @Override
    public Product createProduct() {
        return new ConcreteProductA();
    }
}

class ConcreteProductBFactory implements ProductFactory {
    @Override
    public Product createProduct() {
        return new ConcreteProductB();
    }
}

// Step 5: Client code that uses the factory to create products
public class FactoryDesignPattern {
    public static void main(String[] args) {
        // Use factory to create Product A
        ProductFactory productAFactory = new ConcreteProductAFactory();
        Product productA = productAFactory.createProduct();
        productA.display();

        // Use factory to create Product B
        ProductFactory productBFactory = new ConcreteProductBFactory();
        Product productB = productBFactory.createProduct();
        productB.display();
    }
}

// Some Predefined Examples
// Session session = sessionFactory.createSession();
// session.save(entity);

// Abstract Factory is dealing with multiple factories.