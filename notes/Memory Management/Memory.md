# Java Memory Management

## 1. What are the different types of memory in Java?

### 1. Heap Memory
- Where objects are allocated during runtime
- Stores instances of classes and arrays
- Managed by JVM

### 2. Stack Memory
- Contains references to heap objects
- Smaller and faster to access than heap memory
- Stores:
  - Primitive data types
  - Local variables
  - Object references

### 3. Method Area (PermGen/Metaspace)
- Stores:
  - Class definitions
  - Method information
  - Field information
  - Other metadata
- Note: PermGen was replaced with Metaspace in Java 8+
  - Metaspace can dynamically expand
  - Not limited by fixed size

### 4. Native Memory
- Used for:
  - I/O operations
  - Native method calls
  - Direct buffers
- Not managed by JVM

### 5. Program Counter (PC) Register
- Each thread has its own PC register
- Tracks currently executing instruction
- Managed by JVM

### 6. Registers
- Small, fast storage locations within CPU
- Not directly managed by Java developers
- Critical for code execution
- Not managed by JVM

> **Note**: All memory areas except Native Memory and Registers are managed by the JVM.
