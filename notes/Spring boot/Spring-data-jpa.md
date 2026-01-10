# Spring Data JPA – Interview Quick Reference

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

## 5. JPA vs Hibernate

| Aspect | JPA | Hibernate |
|--------|-----|-----------|
| Type | Specification (standard) | Implementation |
| Vendor Lock-in | ❌ Avoided | ✅ Possible |
| Portability | High | Low |
| Features | Standard only | Rich/Advanced |

**JPA Implementations:** Hibernate, EclipseLink, OpenJPA

**Best Practice:** Use JPA APIs + Hibernate as provider

---

## 6. Hibernate Caching

### Cache Types

| Cache | Scope | Default | Notes |
|-------|-------|---------|-------|
| **L1 (First-Level)** | Session | ✅ Enabled | Cannot disable |
| **L2 (Second-Level)** | SessionFactory | ❌ Disabled | Shared across sessions |
| **Query Cache** | Queries | ❌ Disabled | Depends on L2 |

### L1 Cache (Default)
```java
Session session = sessionFactory.openSession();
session.get(Employee.class, 1); // DB hit
session.get(Employee.class, 1); // Cache hit (same session)
```

### L2 Cache Setup
```properties
hibernate.cache.use_second_level_cache=true
hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
```

```java
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee { }
```

**L2 Cache Providers:** Ehcache, Hazelcast, Infinispan, Redis

### Query Cache
```properties
hibernate.cache.use_query_cache=true
```
```java
query.setCacheable(true);
```

### Cache Concurrency Strategies

| Strategy | Use Case |
|----------|----------|
| READ_ONLY | Static/reference data |
| READ_WRITE | Frequently read & updated |
| NONSTRICT_READ_WRITE | Tolerable stale data |
| TRANSACTIONAL | Strict consistency |

---

## 7. Key Hibernate Properties

| Property | Values | Description |
|----------|--------|-------------|
| `hibernate.show_sql` | true/false | Print SQL to console |
| `hibernate.format_sql` | true/false | Format SQL output |
| `hibernate.hbm2ddl.auto` | create, update, validate, create-drop, none | Schema generation |

### hbm2ddl.auto Values

| Value | Behavior |
|-------|----------|
| create | Drops & recreates schema |
| update | Updates schema |
| validate | Validates schema only |
| create-drop | Drops schema on shutdown |
| none | No action |

**⚠️ Never use `create` or `create-drop` in production!**

```properties
# Common configuration
hibernate.show_sql=true
hibernate.format_sql=true
hibernate.hbm2ddl.auto=validate
hibernate.jdbc.batch_size=50
```

### Connection Pooling (C3P0)
```properties
hibernate.c3p0.min_size=5
hibernate.c3p0.max_size=20
```

---

## 8. JPA Relationship Annotations

### @JoinColumn (Foreign Key)
Used with: `@OneToOne`, `@OneToMany`, `@ManyToOne`

```java
@Entity
public class User {
    @OneToOne
    @JoinColumn(name = "profile_id")  // FK in User table
    private Profile profile;
}

@Entity
public class Order {
    @OneToMany
    @JoinColumn(name = "order_id")  // FK in OrderItem table
    private List<OrderItem> items;
}
```

#### @JoinColumn Attributes

| Attribute | Description |
|-----------|-------------|
| name | FK column name |
| nullable | Whether FK can be null |
| unique | Enforces uniqueness |
| updatable | Allows updates |
| insertable | Allows inserts |

### @JoinTable (Many-to-Many)

```java
@Entity
public class Student {
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
}
```

| Annotation | Use With |
|------------|----------|
| @JoinColumn | @OneToOne, @OneToMany, @ManyToOne |
| @JoinTable | @ManyToMany |

---

## 9. Fetch Types

| Type | Behavior | Default For |
|------|----------|-------------|
| EAGER | Load immediately | @OneToOne, @ManyToOne |
| LAZY | Load on access | @OneToMany, @ManyToMany |

```java
@OneToMany(fetch = FetchType.LAZY)
private List<Order> orders;

@ManyToOne(fetch = FetchType.EAGER)
private Customer customer;
```

**Best Practice:** Use LAZY by default, EAGER only when needed

---

## 10. What is N+1 problem in hibernate, how to overcome it?

### Problem
```java
// 1 query for users + N queries for each user's orders
List<User> users = userRepository.findAll();
for (User u : users) {
    u.getOrders().size();  // triggers N additional queries
}
```

### Solutions

**1. JOIN FETCH (JPQL)**
```java
@Query("SELECT u FROM User u JOIN FETCH u.orders")
List<User> findAllWithOrders();
```

**2. Using FetchType.EAGER**
```java
@OneToMany(fetch = FetchType.EAGER)
private List<Order> orders;
```

---

## 11. Is Data Locking Possible with Hibernate? If Yes, How?

Hibernate provides **two locking mechanisms** to handle concurrent data access.

### 1. Optimistic Locking (Most Common)
- No database lock during read
- Uses a version field
- Fails on update if data was modified by another transaction

```java
@Version
private int version;
```

- Throws `OptimisticLockException` on conflict
- Best for high-read, low-write systems

### 2. Pessimistic Locking
- Locks database rows immediately
- Other transactions must wait

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
```

- Uses SQL `FOR UPDATE`
- Suitable for high-conflict scenarios (e.g., inventory, banking)

| Type | When to Use |
|------|-------------|
| Optimistic | Low contention, better performance |
| Pessimistic | High contention, strict consistency |


---

## 12. Database Performance - Caching Strategy

**Approach (in order):**

1. **L1 Cache** - Auto enabled, within session
2. **L2 Cache** - Cross-session, read-heavy data
3. **Query Cache** - Repeated queries
4. **App-Level Cache** - Spring `@Cacheable`
5. **Distributed Cache** - Redis/Hazelcast for scale

### Spring Cache
```java
@Cacheable("products")
public List<Product> getProducts() { }

@CacheEvict(value = "products", allEntries = true)
public void updateProduct(Product p) { }

@CachePut(value = "product", key = "#product.id")
public Product saveProduct(Product product) { }
```

---

## 13. Hibernate Advantages (Quick Points)

- **ORM** - No boilerplate JDBC code
- **DB Independence** - Dialect-based SQL generation
- **Auto CRUD** - save(), update(), delete(), find()
- **Caching** - L1, L2, Query cache
- **Lazy Loading** - Load data only when needed
- **HQL/Criteria** - DB-independent queries
- **Spring Integration** - Works with @Transactional, Spring Data JPA

---