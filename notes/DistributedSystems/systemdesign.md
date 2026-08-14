## 1. How to Handle Payment Gateway Sync and Async Calls?
### Synchronous Call (Blocking)
- Use `RestTemplate` or `WebClient.block()`
- Wait for payment confirmation before proceeding
- Suitable for real-time confirmation requirements

### Asynchronous Call (Non-Blocking)
- Use `WebClient` with reactive streams or `CompletableFuture`
- Payment gateway sends webhook callback on completion
- Store transaction status as PENDING, update via webhook

---

## 2. SOA vs Microservices

| Feature | Service-Oriented Architecture (SOA) | Microservices |
|---------|------------------------------------|---------------|
| **Approach** | Enterprise-wide (share-as-much-as-possible) | Application-wide (share-as-little-as-possible) |
| **Communication** | Centralized Enterprise Service Bus (ESB) | Light message broker (Kafka) or REST/gRPC |
| **Database** | Shared database among services | Database-per-service (decentralized) |
| **Size** | Larger, coarse-grained services | Smaller, fine-grained/focused services |
| **Protocols** | SOAP, XML, WSDL (heavy) | REST, JSON, Protobuf (light) |

---

## 3. Disintegrating Monolith to Microservices: The Strangler Fig Pattern

- **Concept:** Do not rewrite the entire system from scratch. Instead, gradually replace monolithic functionalities with microservices.
- **Analogy:** Named after the Strangler Fig plant, which grows around a host tree, slowly encapsulating it until the host tree dies and only the new tree remains.
- **Implementation Steps:**
  1. Place an **API Gateway/Reverse Proxy** in front of the monolith.
  2. Build a new microservice for a specific feature (e.g. `NotificationService`).
  3. Route all traffic for notifications to the new microservice at the gateway level.
  4. Remove notification code from the monolith.
  5. Repeat for all modules until the monolith is completely gone.

---

## 4. Designing API to Process 100,000 Orders (Asynchronous, Scalable, Secure)

### The Challenge:
Processing 100,000 orders in a single HTTP request will cause timeouts, database locking, memory exhaustion, and server crashes.

### The Solution:
1. **Asynchronous API Design (HTTP 202 Accepted):**
   - The client uploads orders and immediately receives an `HTTP 202 Accepted` response with a `jobId` and a location header (e.g. `/api/jobs/12345`).
2. **Avoid Large Payloads (Pre-signed S3 URL):**
   - Instead of sending 100,000 records in JSON via POST, the frontend requests a **pre-signed S3 URL** from the gateway.
   - The frontend uploads the CSV/JSON file containing the orders directly to AWS S3.
   - The frontend then calls the backend POST endpoint passing only the S3 file path/key.
3. **Event-Driven Processing (Message Queue):**
   - The backend puts an event on a message queue (e.g. Kafka or RabbitMQ): `OrderJobEvent(jobId, s3Path)`.
4. **Worker Processing & Batching:**
   - Worker microservice instances consume the event from the queue.
   - The worker streams the file from S3, processes the orders in **batches** (e.g. 500 at a time), and saves them to the DB using batch inserts.
5. **Job Status Polling / WebSockets:**
   - The client polls `/api/jobs/12345` or listens to a WebSocket connection to track the job's progress (e.g. "Processing: 45% complete").

---

## 5. CAP Theorem Explained Simply

The CAP Theorem states that in any distributed data store, you can only guarantee **two out of three** of the following properties at the same time:

1. **C**onsistency: Every read receives the most recent write or an error.
2. **A**vailability: Every non-failing node returns a non-error response (but without guarantee that it contains the most recent write).
3. **P**artition Tolerance: The system continues to operate despite arbitrary network partition/failures between nodes.

### The Trade-off (Why you can't have all three):
In a network partition (P) where Node A cannot talk to Node B:
- If a write occurs on Node A, we must choose:
  - **CP System (Consistency):** Block/reject reads on Node B until partition heals. (Sacrifice Availability).
  - **AP System (Availability):** Allow reads on Node B, which returns old/stale data. (Sacrifice Consistency).
- *Note:* Partition tolerance (P) is mandatory in distributed systems because network cables can always fail. Thus, the real choice is always between **CP** and **AP**.

---

## 6. System Design: WhatsApp-Like Chat System

Designing a highly scalable, real-time messaging system like WhatsApp requires supporting low-latency message delivery, online/offline status tracking, and massive scale.

### 1. Functional Requirements:
- One-on-one chat (text, images, media).
- Message status indicators: Sent (single tick), Delivered (double tick), Read (blue ticks).
- Last seen / Online status.
- Group chat (up to 500 members).
- Media storage (photos, videos).

### 2. High-Level Architecture & Components:
```
[ Sender Client ] 
       │ (WebSocket Connection)
       ▼
[ WebSocket Gateway Service ] ─── (Manages active connections & routes messages)
       │
       ├────────► [ Chat/Message Service ] ───► [ Message Store: Cassandra / HBase ]
       │                                            │ (Stores messages for offline delivery)
       │                                            ▼
       ├────────► [ Presence Service ] ────────► [ Cache: Redis ] (Stores online status)
       │
       └────────► [ Push Notification Service ] ──► [ APNS / FCM ] (For offline users)
```

### 3. Key Technology Choices:
- **Protocol: WebSockets (or XMPP):**
  - Standard HTTP is pull-based. We need bidirectional, persistent connection for real-time delivery. WebSockets maintain a TCP connection allowing instant push from server to client.
- **Database for Messages: Wide-column Store (Cassandra or HBase):**
  - **Reason:** Very high write throughput, horizontal scalability, and efficient query pattern for chronological data (`WHERE user_id = X ORDER BY timestamp DESC`).
- **Database for Metadata/Users: Relational DB (PostgreSQL):**
  - For user profiles, contacts, and group configuration where relational consistency is required.
- **Caching & Presence: Redis:**
  - For tracking user online/offline status ("Presence Service") and mapping active connections (`user_id -> websocket_server_ip`).
- **Media Storage: Object Storage (Amazon S3) + CDN:**
  - Files are uploaded to S3. Only the metadata and S3 link are sent via the chat system.

### 4. Message Delivery Workflow (Sender -> Receiver):
1. **Sender is Online:** Sender sends a message via WebSocket. Gateway validates authentication.
2. **Database Write:** Chat Service writes the message to the database (marked as `SENT` -> single tick sent back to Sender).
3. **Receiver Online Check:** 
   - Gateway checks Redis to see if the Receiver is online and which WebSocket Gateway server they are connected to.
   - If **Online:** The gateway server forwards the message via their active WebSocket connection. Once the Receiver's client acknowledges receipt, the server updates status to `DELIVERED` (double ticks) and notifies the Sender.
   - If **Offline:** Chat service saves the message. The gateway triggers the **Push Notification Service** (using Firebase FCM or Apple APNS) to wake up the Receiver's phone. When they come online, their client pulls unread messages.

---

## 7. Design LRU (Least Recently Used) Cache


An LRU Cache evicts the **least recently used** item when the cache is full.

### Key Operations (both must be O(1)):
- `get(key)` — return value if exists, else -1
- `put(key, value)` — insert; if full, evict LRU item first

### Data Structure: HashMap + Doubly Linked List
- **HashMap**: O(1) key lookup → points to node in linked list
- **Doubly Linked List**: tracks recency order (MRU at head, LRU at tail)

```
Dummy Head ←→ [Most Recently Used] ←→ ... ←→ [Least Recently Used] ←→ Dummy Tail
              ↑ move here on access                                    ↑ evict from here
```

### Implementation:
```java
class LRUCache {

    // Node for doubly linked list
    private static class Node {
        int key, val;
        Node prev, next;
        Node(int k, int v) { key = k; val = v; }
    }

    private final int capacity;
    private final Map<Integer, Node> map;   // key → node
    private final Node head, tail;           // dummy sentinels

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);  // Most Recently Used side
        tail = new Node(0, 0);  // Least Recently Used side
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        moveToFront(node);       // accessed → becomes MRU
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            moveToFront(node);
        } else {
            if (map.size() == capacity) {
                // Evict LRU (node just before tail)
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            insertAtFront(newNode);
            map.put(key, newNode);
        }
    }

    // Remove node from its current position
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Insert node right after dummy head (MRU position)
    private void insertAtFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void moveToFront(Node node) {
        remove(node);
        insertAtFront(node);
    }
}
```

### Usage:
```java
LRUCache cache = new LRUCache(3);
cache.put(1, 1);  // cache: [1]
cache.put(2, 2);  // cache: [2, 1]
cache.put(3, 3);  // cache: [3, 2, 1]
cache.get(1);     // returns 1, cache: [1, 3, 2] (1 becomes MRU)
cache.put(4, 4);  // evicts 2 (LRU), cache: [4, 1, 3]
cache.get(2);     // returns -1 (evicted)
```

**Time Complexity:** O(1) for both get and put
**Space Complexity:** O(capacity)

> **Java shortcut:** `LinkedHashMap` with `accessOrder=true` can implement LRU in ~10 lines, but interviewers usually want the manual implementation above.

---


## 8. Design: High-Volume Country Code Validation Microservice



**Requirements:** Validate and return country codes. Multiple consumers. High throughput, low latency, scalable, fault tolerant.

### Key Insight: Country codes are **static reference data** (rarely/never change). This makes caching the central design decision.

### Architecture:
```
Consumers (multiple services/clients)
    │  REST / gRPC
    ▼
[API Gateway]  ← rate limiting, auth, routing
    │
    ▼
[CountryCode Service]  (multiple instances, stateless)
    │         │
    ▼         ▼
[In-Memory   [Redis Cache]   ← shared across instances
  Cache]          │
(Caffeine)        ▼ (only on cache miss)
              [DB / Config File]
```

### Spring Boot Implementation:

```java
@Service
public class CountryCodeService {

    // In-memory Caffeine cache (per instance, nanosecond access)
    private final LoadingCache<String, CountryCode> localCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(300)  // ~250 countries
            .build(this::loadFromRedis);

    @Autowired private RedisTemplate<String, CountryCode> redisTemplate;
    @Autowired private CountryCodeRepository repository;

    public CountryCode validate(String code) {
        return localCache.get(code.toUpperCase()); // O(1), nanoseconds
    }

    // Cache-aside pattern: Caffeine → Redis → DB
    private CountryCode loadFromRedis(String code) {
        CountryCode cached = redisTemplate.opsForValue().get("cc:" + code);
        if (cached != null) return cached;

        CountryCode fromDb = repository.findByCode(code)
                .orElseThrow(() -> new CountryCodeNotFoundException(code));

        redisTemplate.opsForValue().set("cc:" + code, fromDb, 24, TimeUnit.HOURS);
        return fromDb;
    }
}
```

```java
@RestController
@RequestMapping("/api/country-codes")
public class CountryCodeController {

    @GetMapping("/{code}")
    public ResponseEntity<CountryCode> validate(@PathVariable String code) {
        return ResponseEntity.ok(countryCodeService.validate(code));
    }

    @GetMapping
    public ResponseEntity<List<CountryCode>> getAll() {
        return ResponseEntity.ok(countryCodeService.getAll());
    }
}
```

### Design Decisions:

| Concern | Solution |
|---|---|
| **High throughput** | In-memory Caffeine cache (nanosecond reads, no network) |
| **Low latency** | 2-level cache: Caffeine (local) → Redis (shared) → DB (last resort) |
| **Multiple consumers** | Stateless service instances behind load balancer; Redis as shared cache |
| **Scalability** | Stateless pods → horizontal scaling with Kubernetes HPA |
| **Fault tolerance** | Circuit breaker (Resilience4J) on DB/Redis calls; fallback to local cache |
| **Cache invalidation** | When country codes update: publish event to Kafka → all instances evict local cache |
| **Data freshness** | Country codes are ISO standard, change almost never. 24h TTL is safe. |

### Cache Invalidation on Update:
```java
// Admin updates a country code
@KafkaListener(topics = "country-code-updates")
public void onUpdate(CountryCodeUpdatedEvent event) {
    localCache.invalidate(event.getCode());          // evict from Caffeine
    redisTemplate.delete("cc:" + event.getCode());   // evict from Redis
    // Next call will reload from DB
}
```

**Why not just use a database directly?**
- DB call = ~5-50ms. At 10,000 RPS that's 50,000ms of thread wait time per second.
- Caffeine cache = ~100ns. Same 10,000 RPS = negligible overhead.