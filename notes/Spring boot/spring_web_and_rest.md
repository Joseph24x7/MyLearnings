# Spring Web & REST APIs

## 1. What are the main features of Spring Boot?

- Version management via parent POM
- Auto configurations
- Embedded server
- Easy integration
- Excellent microservices support


## 2. How do you configure multiple databases in Spring Boot?

### Multiple Database Configuration
- Create multiple:
  - Data sources
  - Session factories
  - Transaction managers
- Use `@Bean` and `@Qualifier`
- Specify entity scan per data source
  - `@EnableJpaRepositories(basePackages = "com.example.db1.repository", entityManagerFactoryRef = "db1EntityManagerFactory", transactionManagerRef = "db1TransactionManager")`
  - `basePackages = "com.example.db1.repository"` is the package where your repository interfaces for db1 are located.

### Primary Database Setup
- Use `@Primary` annotation
- Fallback to secondary if primary unavailable

### Environment-Specific Database
- Use `@Profile` for environment configuration

### Time-Based Database Switching
- Use `@Conditional` for property-based switching
- Example: 12am-12pm one database, 12pm-12am another


## 3. How do you set the project context path?

### Context Path Setting
```properties
server.servlet.context-path=/your-context-path
```
---


## 4. How do you handle backend failures gracefully?

### Exception Handling Strategies
1. **Global Exception Handler** - Use `@ControllerAdvice` with `@ExceptionHandler`
2. **Custom Exceptions** - Create domain-specific exceptions
3. **Proper HTTP Status Codes** - Return appropriate status (4xx, 5xx)
4. **Circuit Breaker** - Use Resilience4j to prevent cascade failures. Provide default responses when service is down
5. **Retry Mechanism** - Retry transient failures with exponential backoff

---


## 5. How to create custom filters in Spring Boot?

- by implementing Filter interface or OncePerRequestFilter class
- FilterRegistrationBean or `@Order` for ordering
- It will be applied to all requests by default, can be restricted using URL patterns
- Example:
```java
@Component
@Order(1) // Lesser number = higher precedence
public class CustomFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Custom filtering logic
        filterChain.doFilter(request, response);
    }
}
```

---


## 6. When to use @Valid and when to use @Validated?

- `@Valid` is a Jakarta annotation, used for single object validation. Does all the basic validation.
- `@Validated` is a Spring-specific annotation, supports validation groups and can be applied at class level
  - Imagine this requirement: 
    - Create User - name, email is validated
    - Update User - id is validated
- Example for method level Validated annotation with groups:
```java

public interface CreateGroup {}
public interface UpdateGroup {}

public class User {

  @NotNull(groups = UpdateGroup.class)
  private Long id;

  @NotNull(groups = CreateGroup.class)
  private String name;

  @Email(groups = CreateGroup.class)
  private String email;
}

@PostMapping("/create")
public ResponseEntity<?> create(
        @Validated(CreateGroup.class) @RequestBody User user) {
  return ResponseEntity.ok("created");
}

@PostMapping("/update")
public ResponseEntity<?> update(
        @Validated(UpdateGroup.class) @RequestBody User user) {
  return ResponseEntity.ok("updated");
}
```
- Example for class level Validated annotation:
```java
@Service
@Validated 
public class PaymentService {

  public void pay(@NotNull String orderId) { // Because Spring does NOT validate service methods by default.
    // Validation happens BEFORE method execution
  }
}
```
---

## 7. How you're managing configurable values for different environment?

- Use `application.properties` or `application.yml` for default configuration
- Create environment-specific files like `application-dev.properties`, `application-prod.properties`
- Activate profiles using `spring.profiles.active` property
```properties
spring.profiles.active=dev
```
- Use `@Profile` annotation to load beans conditionally based on active profile
```java
@Profile("dev")
@Bean
public DataSource devDataSource() {
    // Dev datasource configuration
}
```
### where we need to pass this `spring.profiles.active` property?
- As a command-line argument: `--spring.profiles.active=dev` (OR)
- As an environment variable: `SPRING_PROFILES_ACTIVE=dev`

---


## 8. How will you restrict Filters based on URLs patterns?

- Use `FilterRegistrationBean` to register filter with specific URL patterns
- Example:
```java
@Bean
public FilterRegistrationBean<CustomFilter> customFilterRegistration() {
    FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new CustomFilter());
    registrationBean.addUrlPatterns("/api/*"); // Restrict to /api/*
    registrationBean.setOrder(1); // Set precedence
    return registrationBean;
}
```

---


## 9. How to implement scheduling in Spring Boot?

- Use `@EnableScheduling` on a configuration class to enable scheduling support
- Use `@Scheduled` annotation on methods to define scheduled tasks
- Example:
```java
@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    public void scheduledTask() {
        System.out.println("Scheduled task executed at " + new Date());
    }
}
```
- Scheduling options:
  - `fixedRate` - Take medicine every 6 hours. Use fixedRate when Polling DB, Monitoring health, Retry Jobs.
  - `cron` - Take medicine at 6 AM and 6 PM. Use cron when Daily reports, Night batch jobs, Data cleanup.
  - `fixedDelay` - Wait for 10 minutes after the last execution completes before starting again.
- Example with cron:
```java
@Scheduled(cron = "0 0 * * * ?") // Runs every hour
public void hourlyTask() {
    System.out.println("Hourly task executed at " + new Date());
}
```
---


## 10. What is the need for Charset conversions while calling backend?

- Ensures correct encoding/decoding of characters
- Prevents data corruption during transmission
- Common charsets: UTF-8, ISO-8859-1
- Example: Converting request/response body to UTF-8
```java
@RequestMapping(value = "/example", produces = "application/json; charset=UTF-8")
public ResponseEntity<String> example() {
    String response = "{\"message\":\"Hello, World!\"}";
    return ResponseEntity.ok(response);
}
```

---


## 11. How will you Create custom HTTPStatus classes with new codes?

- Extend `HttpStatus` enum is not possible as it's final
- Use `ResponseEntity` to return custom status codes
- Only 3 digit status codes are valid
- Example:
```java
@GetMapping("/custom-status")
public ResponseEntity<String> customStatus() {
    return new ResponseEntity<>("Custom Status Response", HttpStatus.valueOf(701)); // Custom HTTP status code 701
}
```
- Note: Custom status codes should be used judiciously and documented well, as they may not be recognized by all clients.

---


## 12. What are the best practices to write Rest API?

- Use proper HTTP methods (GET, POST, PUT, DELETE)
- Use meaningful resource names (nouns)
- Implement versioning (e.g., /api/v1/resource)
- Use appropriate HTTP status codes
- Provide clear and consistent error messages
- Use pagination for large datasets
- Secure APIs with authentication and authorization
- Document APIs using tools like Swagger/OpenAPI
- Use HATEOAS for hypermedia-driven APIs
- Validate input data and sanitize outputs
- Implement caching for performance optimization
- Monitor and log API usage and errors
- Design for scalability and maintainability
- Use JSON as the standard data format
- Ensure idempotency for PUT and DELETE methods
- Example of a well-designed REST endpoint:

---


## 13. Ways to implement versioning in Rest API?

- **URI Versioning**: Include version in the URL path
  - Example: `/api/v1/resource`
- **Request Parameter Versioning**: Use query parameters to specify version
  - Example: `/api/resource?version=1`
- **Header Versioning**: Use custom headers to specify version
  - Example: `X-API-Version: 1`
- **Content Negotiation Versioning**: Use `Accept` header to specify version
  - Example: `Accept: application/vnd.example.v1+json`
- Choose versioning strategy based on use case and client requirements
- Frequently used: URI Versioning and Header Versioning
- We can implement Content Negotiation Versioning using `@RequestMapping` with `produces` attribute:
```java
@GetMapping(value = "/resource", produces = "application/vnd.example.v1+json")
public ResponseEntity<String> getResourceV1() {
    return ResponseEntity.ok("Resource Version 1");
}
@GetMapping(value = "/resource", produces = "application/vnd.example.v2+json")
public ResponseEntity<String> getResourceV2() {
    return ResponseEntity.ok("Resource Version 2");
}
```
- client should set the `Accept` header accordingly to get the desired version.
- Example: `Accept: application/vnd.example.v1+json` for version 1 and `Accept: application/vnd.example.v2+json` for version 2.

---


## 14. Difference between REST API and GraphQL


| Feature | REST | GraphQL |
|---------|------|---------|
| **Endpoints** | Multiple (`/users`, `/orders`) | Single (`/graphql`) |
| **Over-fetching** | ✅ Problem (get extra data) | ❌ No problem |
| **Under-fetching** | ✅ Problem (multiple requests) | ❌ No problem |
| **Caching** | ✅ Easy (HTTP cache) | ❌ Hard (POST-based) |
| **Versioning** | Required (`/v1`, `/v2`) | Not needed |
| **Best For** | Simple APIs | Complex, interconnected data |

**Example:**
```
REST: GET /api/users/1 → Returns all user fields (over-fetching)
GraphQL: query { user(id: 1) { name email } } → Exact fields needed
```

---


## 15. Spring MVC vs Spring WebFlux: Under-the-Hood & Advantages


### 1. Architectural Difference (Under the Hood):
- **Spring MVC (Imperative):** 
  - Uses the **Thread-per-Request** model (usually on Tomcat).
  - Every incoming HTTP request blocks a thread. If the thread queries a database or calls an external API, it blocks until a response is received. 
  - Limits scalability under high concurrent I/O.
- **Spring WebFlux (Reactive):**
  - Uses a **Non-blocking Event Loop** model (usually on Netty).
  - A small, fixed number of threads (matching CPU cores) handles all requests. 
  - Instead of blocking while waiting for I/O, threads register callbacks and move on to process other events. When the I/O completes, the event loop triggers the callback to resume the response.

### 2. Key WebFlux Concepts:
- **Mono<T>:** A reactive publisher that emits 0 or 1 element.
- **Flux<T>:** A reactive publisher that emits 0 to N elements (e.g. data streams).
- **Backpressure:** A mechanism allowing a slow consumer to signal a fast producer to slow down, preventing the system from running out of memory.

### 3. Advantages of Spring WebFlux:
1. **High Concurrency & Scalability:** Can handle hundreds of thousands of concurrent connections with very low memory footprint (since it doesn't spin up a thread per connection).
2. **Efficient Resource Utilization:** Best suited for **I/O-heavy applications** (e.g. gateways, streaming services, calling multiple downstream REST microservices).
3. **Resilient Streaming:** Supports streaming data natively (Server-Sent Events, WebSockets) with backpressure controls.

---


## 16. What happens when a Spring Boot application starts? (Startup Steps)


When you run `SpringApplication.run(YourApp.class, args)`, the following sequential steps take place:

1. **Bootstrap & Initializer Setup:** 
   - The JVM starts, and `SpringApplication` creates the initial context.
   - Detects the type of web application (e.g., Servlet-based or Reactive/WebFlux).
   - Loads initializers (`ApplicationContextInitializer`) and listeners from `META-INF/spring.factories`.
2. **Start the SpringApplicationRunListeners:**
   - Listeners are started to broadcast application lifecycle events (e.g., application starting, environment prepared).
3. **Environment Preparation:**
   - Configures the runtime `Environment` (loads system properties, environment variables, and config files like `application.properties`/`application.yml`).
4. **Print Banner:**
   - Prints the default Spring Boot ASCII banner to the console.
5. **Create Application Context:**
   - Instantiates the context: `AnnotationConfigServletWebServerApplicationContext` (for standard REST APIs) or `AnnotationConfigReactiveWebServerApplicationContext` (for WebFlux).
6. **Prepare Context (Bean Definitions & Configurations):**
   - Injects the Environment, applies Initializers, and loads configuration classes.
   - Scans the classpath (starting from the package of your `@SpringBootApplication` class) to locate `@Component`, `@Service`, `@Repository`, and `@Configuration` classes, registering them as bean definitions.
7. **Refresh Context (Bean Instantiation & DI):**
   - This is the most crucial step. It initializes the beans, resolves dependencies (Dependency Injection), and sets up the IoC container.
   - Triggers post-processors, instantiates all singleton beans, and runs auto-configurations.
8. **Start Embedded Web Server:**
   - Under servlet web context, Spring Boot automatically boots up the embedded server (e.g., Tomcat or Netty) and binds to the configured port (default `8080`).
9. **Execution of Runners:**
   - Locates and executes any beans implementing `CommandLineRunner` or `ApplicationRunner` to run startup setup code.

---


## 17. Handling High CPU Usage in Production


If a Spring Boot application exhibits high CPU usage after a deployment, follow this systematic resolution plan:

### 1. Identify the Culprit (Thread & Process Dump):
- Get the application process ID: `jcmd` or `ps -ef | grep java`.
- Identify which thread is consuming CPU:
  - Linux: Run `top -H -p <pid>` to list threads ordered by CPU usage. Convert the high CPU thread ID (decimal) to hexadecimal.
- Capture thread dumps: `jcmd <pid> Thread.dump_to_file thread_dump.txt` or `jstack <pid> > thread_dump.txt`.
- Open the thread dump file and search for the hexadecimal thread ID to locate the exact class name and line of code causing the high CPU.

### 2. Common Causes & Fixes:
- **Infinite Loops:** An unchecked `while(true)` or circular references causing recursion. *Fix:* Correct the logical check.
- **High Garbage Collection Overhead:** If JVM memory is full (Out of Memory or close to it), the garbage collector runs continuously, eating CPU. *Fix:* Capture a heap dump (`jmap -dump:live,format=b,file=heap.bin <pid>`) and analyze it with Eclipse MAT or VisualVM to find memory leaks. Increase heap size (`-Xmx`).
- **Thread Contention:** Multiple threads competing for locks (synchronized blocks). *Fix:* Transition to lock-free operations, `ConcurrentHashMap`, or reduce lock scopes.

---


## 18. PUT vs POST in REST APIs


| Feature | PUT | POST |
|---------|-----|------|
| **Primary Intent** | **Replace or Create** a resource at a specific URI. | **Create** a new subordinate resource. |
| **URI Design** | Specifies the exact URI: `/api/users/12` (Client decides ID). | Target collection URI: `/api/users` (Server decides ID). |
| **Idempotency** | **Idempotent.** Sending the same PUT request multiple times has the same effect as sending it once. | **Non-Idempotent.** Sending the same POST request multiple times will create duplicate resources. |
| **Caching** | Responses are not cacheable. | Responses can be cached if specific headers are set. |




