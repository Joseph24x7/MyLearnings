# Spring Data Cache – Interview Quick Reference

---

## 1. What are the advantages of Redis Cache?

| Advantage | Description |
|-----------|-------------|
| **In-Memory Storage** | Extremely fast read/write operations |
| **Distributed Cache** | Shared across multiple application instances |
| **Data Persistence** | Optional RDB/AOF persistence to disk |
| **Data Structures** | Supports Strings, Lists, Sets, Hashes, Sorted Sets |
| **TTL Support** | Automatic key expiration |
| **Pub/Sub** | Built-in messaging capabilities |
| **High Availability** | Redis Sentinel & Cluster for failover |
| **Atomic Operations** | Thread-safe operations |
| **Scalability** | Horizontal scaling with Redis Cluster |

### When to Use Redis?
- Session management
- API response caching
- Rate limiting
- Leaderboards/Counters
- Real-time analytics
- Message queues

---

## 2. How to implement Redis Cache in Spring Boot?

### Step 1: Add Dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

### Step 2: Configure Redis Properties
```properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=yourpassword
spring.redis.timeout=2000ms

# Optional: Connection pool
spring.redis.lettuce.pool.max-active=8
spring.redis.lettuce.pool.max-idle=8
```

### Step 3: Enable Caching
```java
@SpringBootApplication
@EnableCaching
public class Application { }
```

### Step 4: Configure Redis Cache Manager
```java
@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .disableCachingNullValues()
            .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(factory)
            .cacheDefaults(config)
            .build();
    }
}
```

### Step 5: Use Cache Annotations
```java
@Service
public class ProductService {

    @Cacheable(value = "products", key = "#id")
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @CachePut(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void clearAllProducts() { }
}
```

### Cache Annotations Summary

| Annotation | Purpose |
|------------|---------|
| `@Cacheable` | Cache method result; skip execution if cached |
| `@CachePut` | Always execute method & update cache |
| `@CacheEvict` | Remove entry from cache |
| `@Caching` | Combine multiple cache operations |

---

## 3. Spring Boot Caching vs Redis Caching?

| Aspect | Spring Boot Caching | Redis Caching |
|--------|---------------------|---------------|
| **Type** | Abstraction/Framework | Cache Provider |
| **Default Provider** | ConcurrentHashMap (in-memory) | External Redis server |
| **Scope** | Single JVM instance | Distributed (multiple instances) |
| **Persistence** | No (lost on restart) | Yes (optional RDB/AOF) |
| **Scalability** | Limited to single instance | Horizontally scalable |
| **Data Sharing** | Not shared across instances | Shared across all instances |
| **Use Case** | Simple, single-node apps | Microservices, clustered apps |

### Spring Boot Caching
```java
// Uses default ConcurrentMapCacheManager
@EnableCaching
@SpringBootApplication
public class App { }

@Cacheable("items")
public Item getItem(Long id) { }
```
- Simple setup, no external dependency
- Cache lost when app restarts
- Each instance has its own cache

### Redis Caching
```java
// Uses RedisCacheManager
@Bean
public CacheManager cacheManager(RedisConnectionFactory factory) {
    return RedisCacheManager.create(factory);
}
```
- Requires Redis server
- Cache persists across restarts
- Shared across all application instances

### When to Choose What?

| Scenario | Recommendation |
|----------|----------------|
| Single instance app | Spring Boot default cache |
| Multiple instances / Microservices | Redis |
| Cache must survive restart | Redis |
| Simple POC / Development | Spring Boot default |
| High availability required | Redis Cluster |

---

## 4. Hibernate Caching - How it works internally and Where does it store data?

### Hibernate Cache Levels

| Cache | Scope | Default | Storage |
|-------|-------|---------|---------|
| **L1 (First-Level)** | Session | ✅ Enabled | JVM Heap (Session object) |
| **L2 (Second-Level)** | SessionFactory | ❌ Disabled | External provider (Ehcache, Redis) |
| **Query Cache** | Queries | ❌ Disabled | Same as L2 provider |

### L1 Cache (First-Level) - How it Works
```java
Session session = sessionFactory.openSession();
Employee emp1 = session.get(Employee.class, 1);  // DB hit, stored in L1
Employee emp2 = session.get(Employee.class, 1);  // Cache hit from L1
session.close();  // L1 cache cleared
```
- Stored in **Session object** (JVM heap memory)
- Automatically enabled, cannot be disabled
- Cleared when session closes
- Not shared across sessions

### L2 Cache (Second-Level) - How it Works
```java
// Session 1
Session s1 = sessionFactory.openSession();
Employee emp1 = s1.get(Employee.class, 1);  // DB hit, stored in L2
s1.close();

// Session 2
Session s2 = sessionFactory.openSession();
Employee emp2 = s2.get(Employee.class, 1);  // L2 cache hit (no DB)
s2.close();
```
- Stored in **external cache provider** (Ehcache, Infinispan, Redis)
- Shared across all sessions in SessionFactory
- Requires explicit configuration

### Where Data is Stored?

| Cache | Storage Location |
|-------|------------------|
| L1 Cache | JVM Heap → Session object → PersistenceContext |
| L2 Cache | External provider's storage (memory/disk) |
| Query Cache | External provider (stores query result IDs) |

### Internal Flow
```
get(Entity.class, id)
    ↓
Check L1 Cache (Session)
    ↓ miss
Check L2 Cache (SessionFactory)
    ↓ miss
Query Database
    ↓
Store in L1 → Store in L2
    ↓
Return Entity
```

---

## 5. When do we need to go for Caching Design Pattern?

### Use Caching When:

| Scenario | Example |
|----------|---------|
| **Frequent reads, rare writes** | Product catalog, config data |
| **Expensive computations** | Report generation, aggregations |
| **Slow external API calls** | Third-party service responses |
| **Database query optimization** | Complex joins, large datasets |
| **Session/User data** | User profiles, preferences |
| **Rate limiting** | API throttling |

### Avoid Caching When:

| Scenario | Reason |
|----------|--------|
| Data changes frequently | Cache invalidation overhead |
| Real-time accuracy needed | Stale data unacceptable |
| Large, unique datasets | Low cache hit ratio |
| Security-sensitive data | Risk of data exposure |

### Common Caching Patterns

#### 1. Cache-Aside (Lazy Loading)
```java
public Product getProduct(Long id) {
    Product product = cache.get(id);
    if (product == null) {
        product = database.findById(id);
        cache.put(id, product);
    }
    return product;
}
```
- Application manages cache
- Most common pattern

#### 2. Write-Through
```java
public void saveProduct(Product product) {
    database.save(product);
    cache.put(product.getId(), product);
}
```
- Write to DB and cache together
- Ensures cache consistency

#### 3. Write-Behind (Write-Back)
```java
public void saveProduct(Product product) {
    cache.put(product.getId(), product);
    // Async write to DB later
}
```
- Write to cache first, DB async
- Better write performance

#### 4. Read-Through
```java
// Cache provider handles DB fetch
@Cacheable("products")
public Product getProduct(Long id) {
    return database.findById(id);
}
```
- Cache fetches from DB on miss
- Simplified application code

### Caching Strategy Decision Tree

```
Is data read frequently?
├── No → Don't cache
└── Yes → Does data change frequently?
    ├── Yes → Short TTL or no cache
    └── No → Is data same for all users?
        ├── Yes → Global cache (Redis)
        └── No → User-specific cache
```

### Cache Invalidation Strategies

| Strategy | Description |
|----------|-------------|
| **TTL (Time-To-Live)** | Auto-expire after duration |
| **Event-based** | Invalidate on data change events |
| **Manual eviction** | Explicitly remove with `@CacheEvict` |
| **Version-based** | Include version in cache key |

```java
// TTL
@Cacheable(value = "products", cacheManager = "ttlCacheManager")

// Event-based eviction
@CacheEvict(value = "products", key = "#product.id")
public void updateProduct(Product product) { }

// Clear all
@CacheEvict(value = "products", allEntries = true)
public void refreshAllProducts() { }
```

---

## 6. How Redis achieves high performance? Where does it store data?

### Why Redis is Fast?

| Factor | Description |
|--------|-------------|
| **In-Memory Storage** | All data stored in RAM, no disk I/O for reads |
| **Single-Threaded** | No context switching, no locks overhead |
| **I/O Multiplexing** | Handles thousands of connections with epoll/kqueue |
| **Efficient Data Structures** | Optimized C implementations (SDS, ziplist, skiplist) |
| **No Disk Seek** | RAM access is ~100,000x faster than disk |
| **Simple Protocol** | RESP protocol is lightweight and fast to parse |

### Where Does Redis Store Data?

| Storage Type | Location | When Used |
|--------------|----------|-----------|
| **Primary Storage** | RAM (Memory) | All operations |
| **RDB Snapshot** | Disk (dump.rdb) | Point-in-time backup |
| **AOF Log** | Disk (appendonly.aof) | Every write operation |

### Data Persistence Options
```
# No persistence (fastest, data lost on restart)
save ""

# RDB - Snapshot every 60 sec if 1000+ keys changed
save 60 1000

# AOF - Log every write
appendonly yes
appendfsync everysec
```

| Mode | Durability | Performance |
|------|------------|-------------|
| No persistence | ❌ None | ⚡ Fastest |
| RDB only | ⚠️ Minutes of data loss | 🚀 Fast |
| AOF everysec | ✅ ~1 sec data loss | 🔵 Good |
| AOF always | ✅ No data loss | 🐢 Slowest |

### Memory Architecture
```
Redis Server (Single Process)
    ↓
Main Thread (handles all commands)
    ↓
Memory (RAM)
├── Key-Value Store (main dict)
├── Expires Dict (TTL tracking)
└── Client Buffers
    ↓
Background Threads (I/O, lazy-free, AOF)
```

---

## 7. Spring Boot Caching - How it works internally and Where does it store data?

### How Spring Cache Works?

```
@Cacheable("products")
public Product getProduct(Long id) { }
```

**Internal Flow:**
```
Method Call
    ↓
CacheInterceptor (AOP Proxy)
    ↓
Generate Cache Key (SpEL or KeyGenerator)
    ↓
Check CacheManager → Cache → get(key)
    ↓ miss
Execute Actual Method
    ↓
Store Result in Cache → put(key, result)
    ↓
Return Result
```

### Key Components

| Component | Role |
|-----------|------|
| **CacheManager** | Creates and manages Cache instances |
| **Cache** | Actual storage (key-value store) |
| **CacheInterceptor** | AOP proxy that intercepts @Cacheable methods |
| **KeyGenerator** | Generates cache key from method params |
| **CacheResolver** | Resolves which cache to use |

### Where Data is Stored?

| CacheManager | Storage Location |
|--------------|------------------|
| **ConcurrentMapCacheManager** (default) | JVM Heap (ConcurrentHashMap) |
| **SimpleCacheManager** | JVM Heap (user-defined Cache) |
| **CaffeineCacheManager** | JVM Heap (Caffeine cache) |
| **RedisCacheManager** | External Redis server |
| **EhCacheCacheManager** | JVM Heap or Disk (Ehcache) |

### Default Behavior (No Redis)
```java
@EnableCaching  // Uses ConcurrentMapCacheManager
@SpringBootApplication
public class App { }
```
- Data stored in `ConcurrentHashMap` inside JVM heap
- Lost when application restarts
- Not shared across instances

### With Redis
```java
@Bean
public CacheManager cacheManager(RedisConnectionFactory factory) {
    return RedisCacheManager.create(factory);
}
```
- Data stored in external Redis server
- Survives application restart
- Shared across all instances

### Cache Key Generation
```java
// Default: all method parameters
@Cacheable("products")
public Product get(Long id) { }  // key = id

// Custom key using SpEL
@Cacheable(value = "products", key = "#id")
public Product get(Long id, boolean includeDetails) { }

// Multiple params
@Cacheable(value = "users", key = "#lastName + '_' + #firstName")
public User find(String firstName, String lastName) { }
```

---

## 8. How to Improve Cache Performance for SQL Database from Spring Boot?

### Multi-Level Caching Strategy

| Level | Tool | Use Case |
|-------|------|----------|
| **L1** | Hibernate L1 Cache | Within session (automatic) |
| **L2** | Hibernate L2 + Ehcache | Across sessions, single node |
| **Application** | Spring @Cacheable | Service layer caching |
| **Distributed** | Redis/Hazelcast | Multi-instance, microservices |
| **Database** | Query optimization, indexes | Reduce DB load |

### 1. Enable Hibernate Second-Level Cache
```properties
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
```

```java
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product { }
```

### 2. Use Spring @Cacheable on Service Layer
```java
@Cacheable(value = "products", key = "#id", unless = "#result == null")
public Product getProductById(Long id) {
    return productRepository.findById(id).orElse(null);
}
```

### 3. Implement Query Caching
```java
@Query("SELECT p FROM Product p WHERE p.category = :category")
@QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
List<Product> findByCategory(String category);
```

### 4. Add Database-Level Optimization
- **Indexes** on frequently queried columns
- **Materialized views** for complex queries
- **Read replicas** for read-heavy workloads
- **Connection pooling** (HikariCP)

### 5. Use Redis for Distributed Caching
```java
@Bean
public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(30))
        .disableCachingNullValues();
    return RedisCacheManager.builder(factory).cacheDefaults(config).build();
}
```

| **Cache Misses** | Poor cache key design | Use hierarchical, descriptive keys |

---