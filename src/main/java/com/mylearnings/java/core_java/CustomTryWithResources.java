package com.mylearnings.java.core_java;

public class CustomTryWithResources {
    public static void main(String[] args) throws Exception {

        // Using try-with-resources to automatically close resources
        try (CustomResource ignored = new CustomResource()) {
            System.out.println("Hello World");
        }
    }
}

// The 'CustomResource' class implements the 'AutoCloseable' interface to support try-with-resources.
class CustomResource implements AutoCloseable {
    @Override
    public void close() {
        // Implement the 'close' method to define how the 'CustomResource' resource should be closed.
    }
}
