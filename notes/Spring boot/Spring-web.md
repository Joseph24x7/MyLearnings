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
- **Global/Application Session**
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
  - `OrderService orderService = new OrderService(paymentService); ✅`
- Dependencies clearly defined

### Field Injection (`@Autowired`)
- Mutable dependencies
- Requires reflection for testing
  - `OrderService orderService = new OrderService(); // paymentService = null ❌`
  - Yes, @Mock + @InjectMocks works with field injection, But it relies on reflection and hides design problems.
- Less explicit dependencies

## 6. What are the main features of Spring Boot?
- Version management via parent POM
- Auto configurations
- Embedded server
- Easy integration
- Excellent microservices support

## 7. How do you configure multiple databases in Spring Boot?
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

## 8. How do you set the project context path?
### Context Path Setting
```properties
server.servlet.context-path=/your-context-path
```
---

## 9. How do you handle backend failures gracefully?
### Exception Handling Strategies
1. **Global Exception Handler** - Use `@ControllerAdvice` with `@ExceptionHandler`
2. **Custom Exceptions** - Create domain-specific exceptions
3. **Proper HTTP Status Codes** - Return appropriate status (4xx, 5xx)
4. **Circuit Breaker** - Use Resilience4j to prevent cascade failures. Provide default responses when service is down
5. **Retry Mechanism** - Retry transient failures with exponential backoff

---

## 10. How to Fix Circular Dependency in Spring?

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

## 11. Does AOP Work on Private Methods?

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

---

## 12. How to create custom filters in Spring Boot?
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

## 13. When to use @Valid and when to use @Validated?
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
## 14. How Auto-configuration works in Spring Boot?
- Spring Boot uses `@EnableAutoConfiguration` to enable auto-configuration
- It scans the classpath for dependencies and configures beans accordingly
- Auto-configuration classes are located in `spring-boot-autoconfigure` module
- You can exclude specific auto-configurations using `@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})`
- You can also create custom auto-configuration by creating a class annotated with `@Configuration` and `@ConditionalOnClass` or other conditional annotations

---

## 15. How you're managing configurable values for different environment?
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

## 16. How to specify two beans by same type without using @Qualifier?
- Use `@Primary` on one of the beans to mark it as the default
```java
@Bean
@Primary
public DataSource primaryDataSource() {
    // Primary datasource configuration
}
@Bean
public DataSource secondaryDataSource() {
    // Secondary datasource configuration
}
```
---

## 17. Difference between @Qualifier and @Resource
### @Qualifier
- Spring-specific annotation
- Used with `@Autowired` to specify which bean to inject when multiple beans of the same type exist
- Requires Spring context
- Example:
```java
@Autowired
@Qualifier("beanName")
private MyService myService;
```
### @Resource
- JSR-250 annotation (Java standard)
- Injects bean by name by default, can also inject by type
- Does not require Spring context
- Example:
```java
@Resource(name = "beanName")
private MyService myService;
```
---

## 18. Purpose of @Conditional?
- Used to conditionally create beans based on certain conditions
- Common conditions:
  - `@ConditionalOnProperty` - based on property value
  - `@ConditionalOnClass` - based on presence of class in classpath
  - `@ConditionalOnMissingBean` - if a bean is missing
  - `@ConditionalOnBean` - if a bean is present
- It can be used at class or method level
- Example for class level condition:
```java
@Configuration
@ConditionalOnProperty(name = "feature.enabled", havingValue = "true")
public class FeatureConfig {
    @Bean
    public FeatureService featureService() {
        return new FeatureService();
    }
}
```
- Example for method level condition:
```java
@Bean
@ConditionalOnMissingBean
public MyService myService() {
    return new MyServiceImpl();
}
```
- It creates MyService bean only if no other MyService bean is present in the context.
- Commonly used in auto-configuration classes to provide default beans that can be overridden by user-defined beans.

---

## 19. Uses of @ConfigurationProperties?
- Binds external configuration (properties/yaml) to Java objects
- Supports nested properties and complex types
- Enables type-safe configuration
- Example:
```java
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private int timeout;
    // getters and setters
}
```
- In `application.properties`:
```properties
app.name=MyApp
app.timeout=30
```
- Advantages:
  - Centralized configuration management
  - Type safety
  - Supports validation with `@Validated`
- If type is not matched while binding, application will fail to start with a descriptive error message.

---

## 20. How will you restrict Filters based on URLs patterns?
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

## 21. How to implement scheduling in Spring Boot?
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
