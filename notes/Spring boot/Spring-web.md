# Spring Web Concepts

## 1. What are the available bean scopes?
### Available Scopes
- **Singleton** (default)
  - Single instance throughout application lifecycle
  - Managed by Spring container
- **Prototype**
  - New instance created per request
  - Fresh object every time
- **Request**
  - New instance per HTTP request
  - Web application specific
- **Session**
  - New instance per HTTP session
  - Web application specific
- **Global Session**
  - Shared across all users and sessions

## 2. What happens if you inject a prototype bean into a singleton bean?
- Possible but loses prototype behavior
- Only one instance created overall

## 3. What is the bean lifecycle in Spring?
1. Container started
2. Bean instantiated (`@Component`)
3. Dependencies injected (`@Autowired`)
4. `init()` method / `@PostConstruct`
5. Actual process
6. `destroy()` / `@PreDestroy`

## 4. What are the dependency injection methods in Spring?
1. Constructor Injection (`@RequiredArgsConstructor`)
2. Setter Injection
3. Field Injection (`@Autowired`)
4. Method Injection (`@Bean`)
5. XML injection

## 5. Constructor vs Field Injection
### Constructor Injection (`@RequiredArgsConstructor`)
- Enforces immutability
- Better for unit testing
- Dependencies clearly defined

### Field Injection (`@Autowired`)
- Mutable dependencies
- Requires reflection for testing
- Less explicit dependencies

## 6. BeanFactory vs ApplicationContext
- Both create beans
- BeanFactory: Lazy loading
- ApplicationContext: Eager loading

## 7. What are the main features of Spring Boot?
- Version management via parent POM
- Auto configurations
- Embedded server
- Easy integration
- Excellent microservices support

## 8. How do you configure multiple databases in Spring Boot?
### Multiple Database Configuration
- Create multiple:
  - Data sources
  - Session factories
  - Transaction managers
- Use `@Bean` and `@Qualifier`
- Specify entity scan per data source

### Primary Database Setup
- Use `@Primary` annotation
- Fallback to secondary if primary unavailable

### Environment-Specific Database
- Use `@Profile` for environment configuration

### Time-Based Database Switching
- Use `@Conditional` for property-based switching
- Example: 12am-12pm one database, 12pm-12am another

## 9. How do you set the project context path?
### Context Path Setting
```properties
server.servlet.context-path=/your-context-path
```

## 10. What are common dependencies in a Spring Boot project?
1. Web
2. Test
3. Actuators
4. DevTools
5. JPA
6. WebFlux
7. Redis
8. Lombok
9. MapStruct
10. TestContainers

---

## 11. How do you handle backend failures gracefully?
### Exception Handling Strategies
1. **Global Exception Handler** - Use `@ControllerAdvice` with `@ExceptionHandler`
2. **Custom Exceptions** - Create domain-specific exceptions
3. **Proper HTTP Status Codes** - Return appropriate status (4xx, 5xx)
4. **Circuit Breaker** - Use Resilience4j to prevent cascade failures
5. **Retry Mechanism** - Retry transient failures with exponential backoff
6. **Fallback Methods** - Provide default responses when service is down

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage(), "NOT_FOUND"));
    }
}
```

---

## 12. Design a REST Application with Simple CRUD Operations
### REST Controller Design
```java
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid UserDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 13. How to Handle Payment Gateway Sync and Async Calls?
### Synchronous Call (Blocking)
- Use `RestTemplate` or `WebClient.block()`
- Wait for payment confirmation before proceeding
- Suitable for real-time confirmation requirements

### Asynchronous Call (Non-Blocking)
- Use `WebClient` with reactive streams or `CompletableFuture`
- Payment gateway sends webhook callback on completion
- Store transaction status as PENDING, update via webhook

```java
// Sync call
PaymentResponse response = restTemplate.postForObject(url, request, PaymentResponse.class);

// Async call with webhook
@PostMapping("/initiate")
public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest req) {
    webClient.post().uri(gatewayUrl).bodyValue(req).retrieve().subscribe();
    return ResponseEntity.ok("Payment initiated, awaiting callback");
}

@PostMapping("/webhook")
public void handleCallback(@RequestBody PaymentCallback callback) {
    paymentService.updateStatus(callback.getTransactionId(), callback.getStatus());
}
```

---

## 14. What is @Qualifier and When to Use It?

### Scenario
When multiple beans of same type exist, Spring doesn't know which one to inject.

```java
@Component("mysqlRepo")
public class MySQLRepository implements UserRepository { }

@Component("mongoRepo") 
public class MongoRepository implements UserRepository { }
```

### Problem - Ambiguity
```java
@Autowired
private UserRepository userRepository; // Which one? Error!
```

### Solution - Use @Qualifier
```java
@Autowired
@Qualifier("mysqlRepo")
private UserRepository userRepository; // Injects MySQLRepository
```

### Alternative - Use @Primary
```java
@Component
@Primary
public class MySQLRepository implements UserRepository { }
// This becomes the default when no @Qualifier specified
```

---

## 15. How to Fix Circular Dependency in Spring?

### Scenario - Circular Dependency
```java
@Service
public class ServiceA {
    @Autowired
    private ServiceB serviceB; // A depends on B
}

@Service
public class ServiceB {
    @Autowired
    private ServiceA serviceA; // B depends on A - CIRCULAR!
}
```

### Solutions

**1. Use @Lazy (Quick Fix)**
```java
@Service
public class ServiceA {
    @Autowired
    @Lazy
    private ServiceB serviceB; // Delays injection until first use
}
```
**2. Refactor - Extract Common Logic (Best Practice)**
```java
@Service
public class CommonService { /* shared logic */ }

@Service
public class ServiceA {
    @Autowired private CommonService commonService;
}

@Service
public class ServiceB {
    @Autowired private CommonService commonService;
}
```
---

## 16. Does AOP Work on Private Methods?

### Answer: NO, AOP Does NOT Work on Private Methods

### Reason
- Spring AOP uses **proxy-based mechanism**
- Proxies can only intercept **public methods** called from outside the class
- Private methods are not visible to proxy

### What Doesn't Work
```java
@Service
public class MyService {
    @Transactional  // Won't work!
    private void privateMethod() { }
    
    @Cacheable      // Won't work!
    private void anotherPrivate() { }
}
```

### Workarounds
1. Make the method **public** and move to another class
2. Use **AspectJ weaving** (compile-time or load-time) instead of Spring AOP
3. For `@Transactional`, call via another bean (not self-invocation)

