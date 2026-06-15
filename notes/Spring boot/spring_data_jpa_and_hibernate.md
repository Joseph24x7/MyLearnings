# Spring Data JPA & Hibernate

---

## 1. JPA vs Hibernate


| Aspect | JPA | Hibernate |
|--------|-----|-----------|
| Type | Specification (standard) | Implementation |
| Vendor Lock-in | ❌ Avoided | ✅ Possible |
| Portability | High | Low |
| Features | Standard only | Rich/Advanced |

**JPA Implementations:** Hibernate, EclipseLink, OpenJPA

**Best Practice:** Use JPA APIs + Hibernate as provider

---


## 2. Hibernate Caching


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


## 3. Key Hibernate Properties


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


## 4. JPA Relationship Annotations


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


## 5. Fetch Types


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


## 6. What is N+1 problem in hibernate, how to overcome it?


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


## 7. Is Data Locking Possible with Hibernate? If Yes, How?


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


## 8. Database Performance - Caching Strategy


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


## 9. Hibernate Advantages (Quick Points)


- **ORM** - No boilerplate JDBC code
- **DB Independence** - Dialect-based SQL generation
- **Auto CRUD** - save(), update(), delete(), find()
- **Caching** - L1, L2, Query cache
- **Lazy Loading** - Load data only when needed
- **HQL/Criteria** - DB-independent queries
- **Spring Integration** - Works with @Transactional, Spring Data JPA

---


## 10. Annotations used in Hibernate


### Entity & Mapping Annotations

| Annotation | Purpose |
|-----------|---------|
| `@Entity` | Mark class as JPA entity (database table) |
| `@Table(name="...")` | Specify custom table name |
| `@Id` | Mark primary key field |
| `@GeneratedValue(strategy=...)` | Auto-generate ID (IDENTITY, SEQUENCE, TABLE) |
| `@Column(name="...", nullable=...)` | Map field to table column |
| `@Transient` | Exclude field from persistence |
| `@Temporal(TemporalType.DATE)` | Map java.util.Date/Calendar to DATE/TIME |
| `@Enumerated(EnumType.STRING)` | Store enum as STRING or ORDINAL |
| `@Lob` | Store large binary/text data (BLOB/CLOB) |
| `@Version` | Optimistic locking versioning |

### Relationship Annotations

| Annotation | Purpose |
|-----------|---------|
| `@OneToOne` | One-to-one relationship |
| `@OneToMany` | One entity has many related entities |
| `@ManyToOne` | Many entities map to one entity |
| `@ManyToMany` | Both sides have many relationships |
| `@JoinColumn(name="...")` | Specify foreign key column |
| `@JoinTable(...)` | Junction table for many-to-many |
| `@FetchType.LAZY` | Load data on demand |
| `@FetchType.EAGER` | Load data immediately (N+1 problem risk) |
| `@Cascade(...)` | Cascade operations (PERSIST, MERGE, DELETE) |

### Example - Relationships:

**One-to-Many:**
```java
@Entity
public class Department {
    @Id
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;
}

@Entity
public class Employee {
    @Id
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
```

**Many-to-Many:**
```java
@Entity
public class Student {
    @Id
    private Long id;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
}

@Entity
public class Course {
    @Id
    private Long id;
    
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students;
}
```

### Constraint Annotations

| Annotation | Purpose |
|-----------|---------|
| `@NotNull` | Field cannot be null |
| `@NotBlank` | String cannot be empty |
| `@NotEmpty` | Collection cannot be empty |
| `@Size(min=..., max=...)` | String/Collection size validation |
| `@Min(value=...)` | Numeric minimum value |
| `@Max(value=...)` | Numeric maximum value |
| `@Email` | Valid email format |
| `@Pattern(regexp=...)` | Regex pattern validation |
| `@Unique` | Column values must be unique (custom or DB constraint) |

### Inheritance Annotations

| Annotation | Strategy | Table Structure |
|-----------|----------|------------------|
| `@Inheritance(strategy=InheritanceType.SINGLE_TABLE)` | Single table for all | One table with discriminator |
| `@Inheritance(strategy=InheritanceType.JOINED)` | Joined table | One table per class + join |
| `@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)` | Table per class | One table per concrete class |

**Single Table Strategy Example:**
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {
    @Id
    private Long id;
    
    private String brand;
}

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {
    private int numDoors;
}

@Entity
@DiscriminatorValue("MOTORCYCLE")
public class Motorcycle extends Vehicle {
    private boolean hasSidecar;
}
```

### Caching Annotations

| Annotation | Purpose |
|-----------|---------|
| `@Cacheable` | Enable L1 caching for entity |
| `@Cache(usage=CacheConcurrencyStrategy....)` | L2 cache strategy |
| `@QueryHint(name="org.hibernate.cacheable")` | Enable query caching |

### Other Important Annotations

| Annotation | Purpose |
|-----------|---------|
| `@PrePersist` | Run before entity is inserted |
| `@PostPersist` | Run after entity is inserted |
| `@PreUpdate` | Run before entity is updated |
| `@PostUpdate` | Run after entity is updated |
| `@PreRemove` | Run before entity is deleted |
| `@PostRemove` | Run after entity is deleted |
| `@PostLoad` | Run after entity is loaded |

**Lifecycle Example:**
```java
@Entity
public class AuditedEntity {
    
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = getCurrentUser();
    }
    
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = getCurrentUser();
    }
}
```

### Query Annotations

| Annotation | Purpose |
|-----------|---------|
| `@Query("JPQL query")` | Custom JPQL query |
| `@Query(nativeQuery=true)` | Native SQL query |
| `@Modifying` | Mark query as UPDATE/DELETE |
| `@Transactional` | Make method transactional |
| `@Param(...)` | Named query parameter |

**Query Example:**
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId")
    List<Employee> findByDepartmentId(@Param("deptId") Long deptId);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE employees SET salary = salary * 1.1 WHERE department_id = :deptId")
    void increaseSalary(@Param("deptId") Long deptId);
}
```

### Best Practices with Annotations

1. **Use proper FetchType:**
   - `LAZY` for collections (default for `@OneToMany`, `@ManyToMany`)
   - `EAGER` only when necessary to avoid N+1 queries

2. **Cascade operations carefully:**
   ```java
   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Child> children;  // Deletes child if removed from list
   ```

3. **Avoid circular references:**
   ```java
   // In one side use @JsonBackReference, other use @JsonManagedReference
   @JsonManagedReference
   @OneToMany(mappedBy = "department")
   private List<Employee> employees;
   
   @JsonBackReference
   @ManyToOne
   private Department department;
   ```


## 11. Hibernate Cascading Types


Cascading defines how operations performed on a parent entity propagate to its associated child entities. In Spring Data JPA, we specify this using the `cascade` attribute of relationship annotations (e.g. `@OneToMany(cascade = CascadeType.ALL)`).

### Cascading Types:
1. **`CascadeType.PERSIST`:** When the parent entity is saved (`persist()`), all associated child entities are automatically saved.
2. **`CascadeType.MERGE`:** When the parent state is merged (`merge()`), child entity states are also merged.
3. **`CascadeType.REMOVE`:** When the parent entity is deleted (`remove()`), all its associated child entities are deleted from the database.
4. **`CascadeType.REFRESH`:** When the parent is reloaded/refreshed (`refresh()`), the child entities are also reloaded from the database.
5. **`CascadeType.DETACH`:** When the parent is detached from the Hibernate Session/Persistence Context (`detach()`), the children are also detached.
**One-to-Many:**
```java
@Entity
public class Department {
    @Id
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;
}

@Entity
public class Employee {
    @Id
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
```

**Many-to-Many:**
```java
@Entity
public class Student {
    @Id
    private Long id;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
}

@Entity
public class Course {
    @Id
    private Long id;
    
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students;
}
```

### Constraint Annotations

| Annotation | Purpose |
|-----------|---------|
| `@NotNull` | Field cannot be null |
| `@NotBlank` | String cannot be empty |
| `@NotEmpty` | Collection cannot be empty |
| `@Size(min=..., max=...)` | String/Collection size validation |
| `@Min(value=...)` | Numeric minimum value |
| `@Max(value=...)` | Numeric maximum value |
| `@Email` | Valid email format |
| `@Pattern(regexp=...)` | Regex pattern validation |
| `@Unique` | Column values must be unique (custom or DB constraint) |

### Inheritance Annotations

| Annotation | Strategy | Table Structure |
|-----------|----------|------------------|
| `@Inheritance(strategy=InheritanceType.SINGLE_TABLE)` | Single table for all | One table with discriminator |
| `@Inheritance(strategy=InheritanceType.JOINED)` | Joined table | One table per class + join |
| `@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)` | Table per class | One table per concrete class |

**Single Table Strategy Example:**
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {
    @Id
    private Long id;
    
    private String brand;
}

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {
    private int numDoors;
}

@Entity
@DiscriminatorValue("MOTORCYCLE")
public class Motorcycle extends Vehicle {
    private boolean hasSidecar;
}
```

### Caching Annotations

| Annotation | Purpose |
|-----------|---------|
| `@Cacheable` | Enable L1 caching for entity |
| `@Cache(usage=CacheConcurrencyStrategy....)` | L2 cache strategy |
| `@QueryHint(name="org.hibernate.cacheable")` | Enable query caching |

### Other Important Annotations

| Annotation | Purpose |
|-----------|---------|
| `@PrePersist` | Run before entity is inserted |
| `@PostPersist` | Run after entity is inserted |
| `@PreUpdate` | Run before entity is updated |
| `@PostUpdate` | Run after entity is updated |
| `@PreRemove` | Run before entity is deleted |
| `@PostRemove` | Run after entity is deleted |
| `@PostLoad` | Run after entity is loaded |

**Lifecycle Example:**
```java
@Entity
public class AuditedEntity {
    
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = getCurrentUser();
    }
    
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = getCurrentUser();
    }
}
```

### Query Annotations

| Annotation | Purpose |
|-----------|---------|
| `@Query("JPQL query")` | Custom JPQL query |
| `@Query(nativeQuery=true)` | Native SQL query |
| `@Modifying` | Mark query as UPDATE/DELETE |
| `@Transactional` | Make method transactional |
| `@Param(...)` | Named query parameter |

**Query Example:**
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId")
    List<Employee> findByDepartmentId(@Param("deptId") Long deptId);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE employees SET salary = salary * 1.1 WHERE department_id = :deptId")
    void increaseSalary(@Param("deptId") Long deptId);
}
```

### Best Practices with Annotations

1. **Use proper FetchType:**
   - `LAZY` for collections (default for `@OneToMany`, `@ManyToMany`)
   - `EAGER` only when necessary to avoid N+1 queries

2. **Cascade operations carefully:**
   ```java
   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Child> children;  // Deletes child if removed from list
   ```

3. **Avoid circular references:**
   ```java
   // In one side use @JsonBackReference, other use @JsonManagedReference
   @JsonManagedReference
   @OneToMany(mappedBy = "department")
   private List<Employee> employees;
   
   @JsonBackReference
   @ManyToOne
   private Department department;
   ```


## 12. Hibernate Cascading Types


Cascading defines how operations performed on a parent entity propagate to its associated child entities. In Spring Data JPA, we specify this using the `cascade` attribute of relationship annotations (e.g. `@OneToMany(cascade = CascadeType.ALL)`).

### Cascading Types:
1. **`CascadeType.PERSIST`:** When the parent entity is saved (`persist()`), all associated child entities are automatically saved.
2. **`CascadeType.MERGE`:** When the parent state is merged (`merge()`), child entity states are also merged.
3. **`CascadeType.REMOVE`:** When the parent entity is deleted (`remove()`), all its associated child entities are deleted from the database.
4. **`CascadeType.REFRESH`:** When the parent is reloaded/refreshed (`refresh()`), the child entities are also reloaded from the database.
5. **`CascadeType.DETACH`:** When the parent is detached from the Hibernate Session/Persistence Context (`detach()`), the children are also detached.
6. **`CascadeType.ALL`:** Applies all the above cascading operations together.

### JPA Cascade vs Hibernate Cascade:
- `jakarta.persistence.CascadeType` is the JPA standard.
- `org.hibernate.annotations.CascadeType` is Hibernate-specific, offering extra options. Always prefer the JPA standard.
- **`orphanRemoval = true` vs `CascadeType.REMOVE`:**
  - `CascadeType.REMOVE` deletes children only if the parent is deleted.
  - `orphanRemoval = true` deletes children if the parent is deleted **OR** if a child is removed from the parent's collection (e.g., `parent.getChildren().remove(child)`).

---


## 13. How do you record audit info (e.g. created/modified dates, user) for audit purposes in JPA?

Spring Data JPA provides built-in support for entity auditing using annotations:

1. **Enable Auditing:** Annotate your main configuration class with `@EnableJpaAuditing`.
2. **Add Auditing Annotations:** Decorate your base entity class:
   - `@CreatedBy`: Records the user who created the entity.
   - `@CreatedDate`: Records the creation timestamp.
   - `@LastModifiedBy`: Records the user who last updated the entity.
   - `@LastModifiedDate`: Records the last update timestamp.
3. **Attach Entity Listener:** Annotate the entity class with `@EntityListeners(AuditingEntityListener.class)`.

### Code Example:
```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
```

### Implementing User Retrieval (`AuditorAware`):
To populate `@CreatedBy` and `@LastModifiedBy`, implement `AuditorAware<String>`:
```java
@Component
public class SecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                       .map(Authentication::getName);
    }
}
```

---

## 14. What is the difference between CrudRepository and JpaRepository?

- **`CrudRepository`:** Part of the core Spring Data Commons project. It provides basic CRUD (Create, Read, Update, Delete) methods (`save()`, `findById()`, `delete()`) returning `Iterable`.
- **`JpaRepository`:** Part of Spring Data JPA. It extends `ListCrudRepository` and `PagingAndSortingRepository`. It adds JPA-specific operations (e.g. `flush()` to sync state to DB, `deleteInBatch()`) and returns `List` instead of `Iterable`, reducing casts.

### Summary:
Use `CrudRepository` for basic CRUD. Use `JpaRepository` when you need sorting, pagination, batch deletes, flushing, or JPA-specific optimizations.
