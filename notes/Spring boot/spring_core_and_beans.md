# Spring Core & Beans

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


## 6. How to Fix Circular Dependency in Spring?


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


## 7. Does AOP Work on Private Methods?


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


## 8. How Auto-configuration works in Spring Boot?

- Spring Boot uses `@EnableAutoConfiguration` to enable auto-configuration
- It scans the classpath for dependencies and configures beans accordingly
- Auto-configuration classes are located in `spring-boot-autoconfigure` module
- You can exclude specific auto-configurations using `@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})`
- You can also create custom auto-configuration by creating a class annotated with `@Configuration` and `@ConditionalOnClass` or other conditional annotations

---


## 9. How to specify two beans by same type without using @Qualifier?

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


## 10. Difference between @Qualifier and @Resource

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


## 11. Purpose of @Conditional?

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


## 12. Uses of @ConfigurationProperties?

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


## 13. Tightly coupled vs Loosely coupled Spring Beans?

### Tightly Coupled Beans
- Direct instantiation using `new` keyword
- Hard to test and maintain
- Example:
```java
@Service
public class MessageService {
    private EmailService emailService = new EmailService(); // Tightly coupled
}
``` 
### Loosely Coupled Beans
- Use dependency injection (`@Autowired`, constructor injection)
- Easier to test and maintain
- Example:
```java
@Service
public class MessageService {
    private final NotificationService notificationService;
    @Autowired
    public MessageService(NotificationService notificationService) {
        this.notificationService = notificationService; // Loosely coupled
    }
}

@Service
public interface NotificationService {
    void sendNotification(String message);
}

@Service
public class EmailService implements NotificationService {
    @Override
    public void sendNotification(String message) {
        // Send email logic
    }
}

```
- Benefits of Loosely Coupled Beans:
  - Easier to swap implementations
  - Improved maintainability and flexibility

---


## 14. Auto-Configuration in Spring Boot: Scenario & Explanation


### What is Auto-Configuration?
- Auto-configuration attempts to automatically configure Spring beans based on the jar dependencies present on the classpath.
- It is enabled by `@EnableAutoConfiguration` (which is part of the `@SpringBootApplication` wrapper).
- It runs *after* user-defined beans are registered, using conditional annotations like `@ConditionalOnClass`, `@ConditionalOnMissingBean`, and `@ConditionalOnProperty` to avoid overriding user configurations.

### Real-world Scenario:
- **Scenario:** Database access configuration.
- **How it works:** When you add the `spring-boot-starter-data-jpa` and `postgresql` driver dependencies, Spring Boot's auto-configuration detects them on the classpath. It automatically instantiates and configures a `DataSource`, an `EntityManagerFactory`, and a `TransactionManager` using defaults (or the credentials defined in `application.properties`). If you manually define a `DataSource` bean in a `@Configuration` class, Spring Boot detects it and backs off (thanks to `@ConditionalOnMissingBean`), utilizing your custom datasource instead.

---


## 15. Resolving Multiple Beans for a Single `@Autowired` & Annotation Preference


When multiple beans of the same type exist in the context, Spring throws a `NoUniqueBeanDefinitionException` during `@Autowired` injection.

### How to Resolve it:
1. **Using `@Qualifier("beanName")`:** Explicitly names which bean to inject at the injection point.
2. **Using `@Primary`:** Placed on one of the bean definition classes/methods to declare it as the default choice.
3. **By Variable Name:** Spring will try to resolve the bean by matching the field/argument variable name with the bean ID (secondary fallback).

### Preference between `@Primary` and `@Qualifier`:
- **`@Qualifier` takes preference.**
- **Reason:** `@Primary` defines a general default fallback bean, whereas `@Qualifier` is an explicit, localized instruction at the point of injection. Local and specific instructions always override global defaults.

---


