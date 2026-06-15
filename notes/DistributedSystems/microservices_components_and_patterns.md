# Microservices Components & Patterns

## 1. Components of microservices:


- It should have its own server.
- It should have its own database.
- It should be fault tolerant.
- It should have its Service Discovery/Registry.
- Containerization
- Orchestration
- Authentication and Authorization
- Logging and Monitoring
- Configuration Management

---


## 2. Need of Eureka in terms of Service Registry:


- Service Discovery: Eureka provides a mechanism for service discovery, allowing services to find and communicate with each other dynamically. In a microservices environment, services often come and go, and having a dynamic service discovery mechanism is essential.

- Dynamic Scaling: Microservices applications often require the ability to scale services up or down based on demand. Eureka enables dynamic scaling by allowing newly instantiated services to register themselves with the registry, and other services can dynamically discover and communicate with them.

- Load Balancing: Eureka can be integrated with load balancing mechanisms to distribute incoming requests among multiple instances of a service. This helps in optimizing resource utilization and improving the overall performance and availability of the application.

- Resilience: Eureka helps in building resilient systems by allowing services to be resilient to failures. If a service instance fails, Eureka can quickly detect the failure and route traffic to healthy instances, minimizing downtime and improving system reliability.

---


## 3. Limitations of Eureka:


- Only the services which are registered to Eureka can do communication to the other services registered into eureka.

---


## 4. Fault Tolerance in Microservices:


- Fault tolerance is the property that enables a system to continue operating properly in the event of the failure of some of its components.
- It falls under circuit breaker design pattern of microservices.
- To implement this: Hystrix, Resilience4J

---


## 5. use of Resilience4J in Fault Tolerance/Circuit breaker?


- ### States of Resilience4J and its purposes:

	- CLOSED: When all the requests are successfully passing through without much failures.
	- OPEN: When the failure rate of requests exceeds a configured threshold, the Circuit Breaker transitions to the OPEN state. In this state, all incoming requests are blocked, and the Circuit Breaker does not allow any requests to pass through.
	- HALF_OPEN: After a specified duration in the OPEN state, the Circuit Breaker transitions to the HALF_OPEN state. In this state, a limited number of requests are allowed to pass through to test whether the backend service has recovered.

- ### Important Properties:

	- slidingWindowSize: This is the number of calls that the Circuit Breaker tracks for the sliding window. It affects how quickly the Circuit Breaker can transition between states.

	- minimumNumberOfCalls: The minimum number of calls that must be evaluated before the Circuit Breaker can transition to an open or closed state. It helps in preventing the Circuit Breaker from opening too quickly.

	- permittedNumberOfCallsInHalfOpenState: The number of permitted calls in the half-open state. When the Circuit Breaker transitions from open to half-open, it allows a limited number of calls to determine if the backend is still failing.

	- failureRateThreshold: The failure rate at which the Circuit Breaker opens. If the failure rate exceeds this threshold, the Circuit Breaker transitions to the open state.

---


## 6. Difference between handling the failures using exception vs handling failures using Resilience4J/Hystrix circuit breakers?


- When the ciruit breaker goes to OPEN state, it skips making a downstream calls
- But with exceptions it will hit the downstream everytime.

- Once the downstream is up, it will goe to HALF_OPEN state, where it will get the responses back from downstream.

---


## 7. API Gateway uses:


- Interface inbetween Front end & Microservice.
- Routing the requests
- Authentication/Authorization
- /Logging/ Monitoring/etc..
- Versioning
- Rate Limiters
- Load Balancing

---


## 8. How to implement API Gateway:


- Netflix API Gateway - Zuul (blocking API Gateway)
- Spring cloud API Gateway (non-blocking API Gateway)

---


## 9. Terminalogies used in API-Gateway?


-> route: routes to the particular microservices contains the id, uri & predicate.
-> predicate: context path of a microservices.

---


## 10. How to handle fault tolerance in a microservice using kafka?


- Use Replication: Kafka supports data replication for fault tolerance. Configure your Kafka topics to have multiple replicas to ensure data durability. If a Kafka broker or instance fails, another replica can take over, ensuring data availability.
- Consumer Group: Use Kafka consumer groups for your microservices. If one consumer instance fails, Kafka will automatically rebalance the workload among the remaining consumers in the group. This ensures that no messages are lost.
- Circuit Breakers and Retries: Implement circuit breakers and retry mechanisms in your microservices. If a service or component experiences temporary issues, retries can help recover from transient failures. Circuit breakers can prevent your system from making repeated requests to a failing service.
- Backup and Restore: Set up backup and restore processes for Kafka data. Regularly back up your Kafka topics and configurations so that you can recover data in case of catastrophic failures.

---


## 11. API Gateway vs Load Balancing vs Service Registry:


- ### API Gateway:
	- Centralized API Management
	- Routing and Load Balancing
	- Security, rate limiting, and traffic encryption (HTTPS/SSL).
	- Analytics and Monitoring

- ### Load Balancing:
	- Scalability: Load balancers can automatically scale the number of server instances based on traffic load, allowing for efficient resource utilization.
	- Optimized Performance: They can route traffic to the closest or least loaded server, optimizing response times and reducing latency.
	- Health Checks: Load balancers can continuously monitor the health of server instances and route traffic only to healthy instances.

- ### Service Registry:
	- keep track of available services and their instances, making it easy for services to discover and communicate with each other dynamically.
	- Load Balancing.
	- Resilience: In case of instance failures, service registries can update their information and redirect traffic to healthy instances, improving system resilience.
	- Consistency: Service registries ensure that services always have access to up-to-date information about the available instances, which can be critical in dynamic, containerized environments.

---


## 12. How to Monitor and Secure Microservices Applications?


### Monitoring Microservices

#### 1. **Metrics Collection**
- Use Prometheus for collecting metrics
- Metrics to track:
  - Request latency (response time)
  - Error rates
  - Throughput (requests/second)
  - Resource usage (CPU, memory, disk)
  - Cache hit/miss rates
  - Database connection pool status

**Implementation:**
```java
@Service
public class OrderService {
    
    private final MeterRegistry meterRegistry;
    
    public OrderService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // Register custom metrics
        Gauge.builder("orders.pending", this::getPendingOrderCount)
            .description("Number of pending orders")
            .register(meterRegistry);
    }
    
    public void createOrder(Order order) {
        Timer.Sample sample = Timer.start(meterRegistry);
        
        try {
            // Business logic
            orderRepository.save(order);
            meterRegistry.counter("orders.created").increment();
        } catch (Exception e) {
            meterRegistry.counter("orders.failed").increment();
            throw e;
        } finally {
            sample.stop(Timer.builder("orders.creation.time")
                .publishPercentiles(0.5, 0.95, 0.99)
                .register(meterRegistry));
        }
    }
}
```

#### 2. **Distributed Tracing**
- Use ELK Stack (Elasticsearch, Logstash, Kibana) or Jaeger
- Trace requests across multiple services
- Identify bottlenecks and failures

```java
// application.properties
spring.application.name=order-service
management.endpoints.web.exposure.include=metrics,prometheus
spring.sleuth.sampler.probability=1.0  // 100% sampling for demo
spring.zipkin.base-url=http://localhost:9411  // Zipkin server
```

#### 3. **Health Checks**
```java
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {
    
    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            // Check if critical dependencies are available
            boolean paymentServiceUp = checkPaymentService();
            
            if (paymentServiceUp) {
                builder.up().withDetail("payment-service", "UP");
            } else {
                builder.down().withDetail("payment-service", "DOWN");
            }
        } catch (Exception e) {
            builder.down().withException(e);
        }
    }
    
    private boolean checkPaymentService() {
        // Check payment service availability
        return true;
    }
}
```

#### 4. **Logging**
- Centralized logging with correlation IDs
- Use structured logging (JSON format)

```java
@Service
public class OrderService {
    
    @Autowired
    private Logger logger;
    
    public void createOrder(Order order) {
        String correlationId = MDC.get("correlationId");
        
        logger.info(
            "Creating order",
            "correlationId", correlationId,
            "orderId", order.getId(),
            "customerId", order.getCustomerId()
        );
    }
}
```

#### 5. **Alerting**
- Configure alerts for:
  - High error rates (> 5%)
  - High latency (> 2s)
  - Service unavailability
  - High resource usage (CPU > 80%, Memory > 90%)
  - Circuit breaker opened

### Securing Microservices

#### 1. **Authentication & Authorization**
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://auth-server.com
          jwk-set-uri: https://auth-server.com/.well-known/jwks.json
```

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
                .jwt();
        
        return http.build();
    }
}
```

#### 2. **Service-to-Service Communication (mTLS)**
- Use mutual TLS (mTLS) for encrypted communication between services
- Use service mesh (Istio, Linkerd) for automatic mTLS

#### 3. **API Gateway Security**
- Authentication at gateway level
- Rate limiting
- Request/response validation
- IP whitelisting

```java
@Configuration
public class ApiGatewayConfig {
    
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("order-service",
                r -> r.path("/api/orders/**")
                    .filters(f -> f
                        .rewritePath("/api/orders/?(?<segment>.*)", "/${segment}")
                        .requestRateLimiter(config -> config
                            .setKeyResolver(exchange -> 
                                Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()))
                            .setRateLimiter(redisRateLimiter())))
                    .uri("http://order-service"))
            .build();
    }
}
```

#### 4. **Data Encryption**
- Encrypt sensitive data at rest
- Use TLS for data in transit
- Hash passwords using BCrypt

```java
@Configuration
public class EncryptionConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

#### 5. **Secrets Management**
- Use HashiCorp Vault or AWS Secrets Manager
- Store database passwords, API keys in vault, not in code

```yaml
spring:
  cloud:
    vault:
      host: localhost
      port: 8200
      scheme: http
      authentication: TOKEN
      token: mytoken
```

#### 6. **Dependency Scanning**
- Use OWASP DependencyCheck
- Regular vulnerability scanning
- Keep dependencies updated

```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>8.4.0</version>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

#### 7. **Web Security Headers**
```java
@Configuration
public class SecurityHeadersConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers()
            .contentSecurityPolicy("default-src 'self'")
            .and()
            .xssProtection()
            .and()
            .frameOptions().sameOrigin();
        
        return http.build();
    }
}
```

### Monitoring Tools Stack

```
┌─────────────────────────────────────────┐
│      Microservices Monitoring Stack      │
├─────────────────────────────────────────┤
│ Prometheus      → Metrics collection     │
│ Grafana         → Metrics visualization  │
│ ELK Stack       → Logs aggregation       │
│ Jaeger/Zipkin   → Distributed tracing    │
│ AlertManager    → Alerting               │
│ Vault           → Secrets management     │
│ Istio/Linkerd   → Service mesh           │
└─────────────────────────────────────────┘
```

---


## 13. How will you do rate limiting to avoid throttling?


### Rate Limiting Strategies

**1. Token Bucket Algorithm**
```
- Bucket capacity: 100 tokens
- Refill rate: 10 tokens/second
- Each request costs 1 token
- When empty: Request rejected (429 Too Many Requests)
```

**2. Sliding Window**
```
- Count requests in last 60 seconds
- Max: 1000 requests/minute
- Oldest requests dropped as window slides
```

**3. Fixed Window**
```
- Requests per minute: 1000
- Reset every 60 seconds
- Simple but can spike at boundaries
```

### Implementation in Spring Boot
```java
@RestController
public class OrderController {
    
    @PostMapping("/orders")
    @RateLimiter(name = "orderLimit")  // 100 req/minute
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.create(order));
    }
}

// application.yml
resilience4j:
  ratelimiter:
    instances:
      orderLimit:
        registerHealthIndicator: true
        limitForPeriod: 100           # Max requests
        limitRefreshPeriod: 1m        # Time period
        timeoutDuration: 5s           # Timeout for waiting
```

### Using API Gateway (Apigee/Kong)
```yaml
# Apigee policy
<RateLimit>
  <Identifier ref="request.header.api_key"/>
  <Allow count="100"/>
  <Interval>1</Interval>
  <TimeUnit>minute</TimeUnit>
</RateLimit>
```

### Rate Limiting Headers
```
Request:
GET /api/orders HTTP/1.1
X-API-Key: your-api-key

Response:
HTTP/1.1 200 OK
X-RateLimit-Limit: 100         # Max requests
X-RateLimit-Remaining: 87      # Requests left
X-RateLimit-Reset: 1644326800  # Unix timestamp
```

---


## 14. Do your API gateway do rate limiting? (Apigee/Kong)


### Yes, API gateways have built-in rate limiting:

**Apigee:**
```
✅ RateLimit policy
✅ Traffic management at API level
✅ Per API key, per user, per IP
✅ Distributed rate limiting (multi-region)
✅ Reset counter policies
```

**Kong:**
```
✅ Rate-Limiting plugin
✅ Redis for distributed state
✅ Per consumer, per service
✅ Multiple rate limiting strategies
```

**NGINX:**
```
✅ limit_req_zone
✅ Simple rate limiting
✅ Per IP address
```

### Example: Apigee Rate Limit
```xml
<RateLimit async="true">
  <Identifier ref="request.header.api_key"/>
  <Allow count="1000"/>
  <Interval>1</Interval>
  <TimeUnit>minute</TimeUnit>
  <Counters>
    <Counter ref="api_key"/>
  </Counters>
</RateLimit>

<!-- Fallback policy when rate limit exceeded -->
<AssignMessage async="true">
  <Set>
    <StatusCode>429</StatusCode>
    <ReasonPhrase>Too Many Requests</ReasonPhrase>
  </Set>
</AssignMessage>
```

### Pros of Gateway Rate Limiting
- ✅ Protects backend services
- ✅ Centralized configuration
- ✅ Distributed state (Redis backend)
- ✅ Multiple strategies available
- ✅ No code changes needed

---


## 15. What are the common annotations used in Spring Cloud?

Here are the key annotations used for configuring Spring Cloud microservices:

- `@EnableDiscoveryClient` / `@EnableEurekaClient`: Registers the application with a service registry (e.g. Eureka) so other microservices can discover it.
- `@EnableFeignClients`: Scans for Feign clients to enable declarative REST communication between microservices.
- `@RefreshScope`: Reloads configuration properties annotated with `@Value` dynamically at runtime without needing to restart the application (triggered via `/actuator/refresh`).
- `@EnableConfigServer`: Declares the service as a central Spring Cloud Config Server hosting properties for all microservices.

---

## 16. How will you design an e-commerce application using the Saga pattern?

The **Saga Pattern** manages distributed transactions across multiple microservices via a sequence of local transactions:

### 1. Happy Path Flow:
- **Order Service** creates an order in `PENDING` state.
- **Payment Service** processes payment.
- **Inventory Service** reserves stock.
- If all succeed, Order Service changes status to `CONFIRMED`.

### 2. Failure Handling (Compensating Transactions):
- If **Inventory Service** fails (out of stock):
  - It triggers a compensating transaction backwards.
  - **Payment Service** runs a refund.
  - **Order Service** cancels the order (status changed to `CANCELLED`).

### 3. Implementation Approaches:
- **Choreography-based (Event-Driven):** Services communicate asynchronously by listening to events on message brokers (e.g. Kafka/RabbitMQ) without a central coordinator.
- **Orchestration-based:** A central coordinator service (Saga Orchestrator) directs the flow, calling each service explicitly and coordinating rollbacks if a step fails.
