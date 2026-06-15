# Spring Data Transactions

---

## 1. @Transactional Rollback Behavior


### Default Behavior
- Rolls back only for **unchecked exceptions** (`RuntimeException`, `Error`)
- **Checked exceptions** do NOT trigger rollback by default

### Rollback Options

```java
// Default - rollback for unchecked only
@Transactional
public void method() { }

// Rollback for ALL exceptions
@Transactional(rollbackFor = Exception.class)
public void method() throws Exception { }

// Custom rollback rules
@Transactional(
    rollbackFor = { CustomException.class },
    noRollbackFor = { AnotherException.class }
)
public void method() { }
```

---


## 2. Propagation Types


Propagation defines **how a method behaves when a transaction already exists**.

| Propagation | Existing TX | No TX | Use Case |
|-------------|-------------|-------|----------|
| **REQUIRED** (default) | Join | Create | Most common |
| **REQUIRES_NEW** | Suspend & Create | Create | Audit logs, independent commits |
| SUPPORTS | Join | No TX | Optional transaction |
| NOT_SUPPORTED | Suspend | No TX | Non-transactional work |
| MANDATORY | Join | Exception | Must have existing TX |
| NEVER | Exception | No TX | Must NOT have TX |
| NESTED | Savepoint | Savepoint | Partial rollback (JDBC only) |

```java
@Transactional(propagation = Propagation.REQUIRED)      // default
@Transactional(propagation = Propagation.REQUIRES_NEW)  // new independent TX
```

---


## 3. Isolation Levels


Isolation controls **data visibility between concurrent transactions**.

| Level | Dirty Read | Non-Repeatable Read | Phantom Read |
|-------|------------|---------------------|--------------|
| READ_UNCOMMITTED | ✅ | ✅ | ✅ |
| **READ_COMMITTED** (common) | ❌ | ✅ | ✅ |
| REPEATABLE_READ | ❌ | ❌ | ✅ |
| SERIALIZABLE | ❌ | ❌ | ❌ |

```java
@Transactional(isolation = Isolation.READ_COMMITTED)
```

**Definitions:**
- **Dirty Read**: Reading uncommitted data from another TX
- **Non-Repeatable Read**: Same query returns different data (row modified)
- **Phantom Read**: Same query returns different rows (rows added/deleted)

---


## 4. Why @Transactional Doesn't Work on Private Methods?


### Spring AOP Proxy Limitations
- ❌ Private methods
- ❌ Final methods
- ❌ Static methods
- ❌ Self-invocation (calling method within same class)

```java
// ❌ WON'T WORK - self invocation bypasses proxy
@Service
public class MyService {
    public void outer() { 
        inner();  // direct call, not through proxy
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void inner() { }  // transaction IGNORED
}

// ✅ WORKS - call via another bean (goes through proxy)
@Service
public class InnerService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void inner() { }
}

@Service
public class OuterService {
    @Autowired 
    private InnerService innerService;
    
    public void outer() { 
        innerService.inner();  // works!
    }
}
```

### Key Points
- `@Transactional` works via **Spring AOP proxies**
- Always call transactional methods **via another bean**
- Use **public methods** for transactional boundaries

---


## 5. Comprehensive List of Cases When `@Transactional` Does Not Work


Spring manages transactions using AOP (Aspect-Oriented Programming) proxies. Here are all the scenarios where `@Transactional` fails to create or rollback transactions:

1. **Non-Public Methods:** 
   - Placing `@Transactional` on `private`, `protected`, or package-private (default) methods will not work. Spring's proxy mechanism only intercepts `public` method invocations.
2. **Self-Invocation (Internal Call):**
   - Calling a `@Transactional` method from another method within the same class (e.g., `this.innerMethod()`) will bypass the Spring proxy. The transaction code is never executed.
3. **Catching Exceptions Inside the Method:**
   - If a `RuntimeException` occurs but is caught within a `try-catch` block inside the method and not rethrown, Spring AOP does not detect the failure and commits the transaction anyway.
4. **Checked Exceptions (Default Behavior):**
   - By default, transactions are only rolled back for **unchecked exceptions** (`RuntimeException` and `Error`). **Checked exceptions** (extending `Exception`) do not trigger a rollback unless explicitly configured: `@Transactional(rollbackFor = Exception.class)`.
5. **Wrong Transaction Manager:**
   - If the application configures multiple databases or transaction managers, and `@Transactional` is not specified with the correct qualifier (e.g., `@Transactional("transactionManagerA")`), the wrong context may be used, resulting in no transaction on the target database.
6. **Invoking Class Not Managed by Spring:**
   - If the class containing the `@Transactional` method is instantiated manually via `new MyService()` instead of being injected as a Spring bean (`@Autowired` or constructor injection), Spring AOP proxies are not active.

---


## 6. Code Review Case Study: Common Transaction & DB Anti-Patterns


### Problem File 1: Transactional Misuse
```java
@Service
public class OrderService {

    public void createOrder() {
        // Calling private transactional method (Anti-Pattern #1)
        saveOrderData(); 
    }

    @Transactional
    private void saveOrderData() { // Fails because method is private (Anti-Pattern #2)
        try {
            orderRepository.save(new Order());
            paymentService.processPayment(); 
        } catch (Exception e) {
            // Catching exception prevents transaction rollback (Anti-Pattern #3)
            logger.error("Payment failed", e); 
        }
    }
}
```
#### Fixes:
1. Make `saveOrderData()` `public` and place it in a separate bean or call it directly via public invocation from outside this class.
2. Remove the try-catch block or rethrow the exception (e.g., `throw new RuntimeException(e)`) to let the transaction interceptor catch the failure and trigger the rollback.

### Problem File 2: DB Calls in Controller & SQL Injection
```java
@RestController
public class UserController {
    
    @Autowired
    private DataSource dataSource; // Anti-Pattern #1: Injecting DataSource in Controller

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam String name) throws SQLException {
        // Anti-Pattern #2: Making direct database calls in the Controller level
        // Anti-Pattern #3: Vulnerable to SQL Injection via string concatenation (+)
        String sql = "SELECT * FROM users WHERE name = '" + name + "'";
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        // ... parse list and return ...
    }
}
```
#### Fixes:
1. Move database access code out of the Controller and delegate to a `@Service` class and `@Repository` class (following proper MVC layered architecture).
2. Stop string concatenation. Use **Prepared Statements** or Spring Data JPA repositories with query parameters to eliminate SQL injection:
   `userRepository.findByName(name)` or using parameterized `PreparedStatement` with `?` placeholders.

---


## 7. Scenario: Rollback with Mixed Database and Non-DB Calls (Kafka/Redis)


Consider the following snippet:
```java
@Transactional
public void save(){
  saveToDB();     // DB Write
  pushToKafka();  // Kafka Message Push
  storeInRedis(); // Redis Write
}
```
### Will rollback happen if any method call fails?
1. **If `saveToDB()` fails:** Yes. It throws a RuntimeException. The transaction rolls back, and database changes are not committed. Kafka and Redis calls are never reached, so no side-effects occur.
2. **If `pushToKafka()` or `storeInRedis()` fails:** Yes, the database transaction **will rollback** (because the exception propagates out of the `@Transactional` method, preventing commit). 
3. **The Distributed Consistency Issue (Critical Interview Point):**
   - **Database** changes are transactional and **will rollback**.
   - **Kafka / Redis** do not participate in the database transaction manager. Once a message is pushed to Kafka, it is sent instantly and **cannot be rolled back** by the DB transaction manager.
   - If `storeInRedis()` throws an exception, the database write is rolled back, but the Kafka message has already been published to the broker!
   - **How to resolve this:** Use the **Transactional Outbox Pattern**. Save the Kafka event to an `outbox` database table within the same DB transaction. A separate scheduler / Debezium engine polls the table and publishes messages to Kafka *only* after the DB transaction has successfully committed.

