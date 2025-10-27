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
