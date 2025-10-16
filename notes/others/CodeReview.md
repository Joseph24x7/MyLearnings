# Code Review Best Practices

## 1. How to handle methods with too many parameters?

### Issues with Many Parameters
- Methods with > 10 parameters are code smells
- Indicates potential design problems
- Makes code hard to maintain

### Solutions
1. **Use Builder Pattern**
```java
public class RequestBuilder {
    private String param1;
    private String param2;
    // ... more parameters
    
    public RequestBuilder withParam1(String value) {
        this.param1 = value;
        return this;
    }
    
    public Request build() {
        return new Request(this);
    }
}
```

2. **Create Parameter Objects**
```java
public class UserParameters {
    private String name;
    private String email;
    private String address;
    // ... more fields
}
```

3. **Split Method**
- Break into smaller, focused methods
- Each method handles specific functionality
- Improves readability and maintainability

## 2. What are common Sonar issues?

### 1. Unused Code
- Dead code
- Unused imports
- Unused variables
- Unused methods

### 2. Code Duplication
- Repeated code blocks
- Copy-pasted logic
- Similar implementations

### 3. Complex Code
- High cyclomatic complexity
- Deep nesting
- Long methods
- Large classes

### 4. Security Vulnerabilities
- SQL injection risks
- XSS vulnerabilities
- Insecure configurations
- Weak cryptography

### 5. Null Pointer Dereference
- Unchecked null values
- Missing null checks
- Potential NPEs

### 6. Coding Conventions
- Naming conventions
- Code formatting
- Documentation standards
- Best practices violations
