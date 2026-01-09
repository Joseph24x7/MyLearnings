# System Design – Interview-Ready Notes

---

## 1. What is System Design?

System Design is the process of defining the **architecture, components, interfaces, and data flow** of a system to meet both **functional** and **non-functional requirements**.

It focuses on:

* Building **scalable, reliable, and maintainable** systems
* Translating business requirements into technical solutions
* Making trade-offs between performance, cost, complexity, and time-to-market

System Design typically covers database design, API contracts, scalability strategies, fault tolerance, security, and deployment considerations.

---

## 2. Core Concepts in System Design

* **Scalability**: Ability to handle growth (users, data, traffic) by adding resources
* **Availability**: Percentage of time the system is operational
* **Reliability**: Ability to perform correctly over time
* **Latency**: Time taken to respond to a request
* **Throughput**: Number of requests processed per unit time
* **Consistency**: All users see the same data at the same time
* **Partition Tolerance**: System continues to operate despite network failures

> These concepts are often discussed using the **CAP Theorem**.

---

## 3. Common System Design Patterns

* **Client–Server Architecture**
* **Monolithic Architecture**
* **Microservices Architecture**
* **Load Balancing**
* **Caching (Read-through / Write-through)**
* **Database Sharding & Replication**
* **Message Queues / Event-Driven Architecture**
* **Circuit Breaker & Retry Patterns**

---

## 4. Generic Steps to Design Any System (HLD Flow)

1. **Clarify Requirements**

    * Functional requirements
    * Non-functional requirements (QPS, latency, availability)

2. **Estimate Scale**

    * Traffic (users, requests/sec)
    * Storage (data size, growth)

3. **High-Level Architecture**

    * Identify major components
    * Define interactions between components

4. **Data Design**

    * Choose database type (SQL / NoSQL)
    * Schema, partitioning, replication

5. **Scalability Strategy**

    * Horizontal vs vertical scaling
    * Stateless services

6. **Reliability & Fault Tolerance**

    * Replication, retries, failover

7. **Caching Strategy**

    * What to cache and where

8. **Security**

    * Authentication, authorization, encryption

9. **Monitoring & Observability**

    * Logs, metrics, alerts

---

## 5. High-Level Design (HLD) vs Low-Level Design (LLD)

### High-Level Design (HLD)

* System architecture
* Major components and interactions
* Data flow diagrams
* Technology choices

### Low-Level Design (LLD)

* Class diagrams
* APIs and interfaces
* Algorithms and data structures
* Database schemas

---

## 6. Designing a High-Volume Payment Gateway (HLD Steps)

### Step 1: Requirements

* Process millions of transactions/day
* Low latency (<200ms)
* High availability (99.99%)
* Strong consistency for payments
* Regulatory compliance (PCI-DSS)

### Step 2: Core Components

* API Gateway
* Authentication & Authorization Service
* Payment Processing Service
* Fraud Detection Service
* Transaction Ledger Service
* Notification Service

### Step 3: Architecture

* Stateless microservices behind a load balancer
* Event-driven processing using Kafka
* Idempotent APIs to avoid double payments

### Step 4: Data Storage

* Relational DB for transactions (PostgreSQL)
* NoSQL / Cache for session & metadata (Redis)
* Append-only ledger for audit

### Step 5: Scalability & Reliability

* Horizontal scaling
* Database replication
* Circuit breakers and retries
* Graceful degradation

### Step 6: Security

* Tokenization of card data
* Encryption at rest and in transit
* Role-based access control

---

## 7. MongoDB vs Couchbase – Why Couchbase?

| Aspect   | Couchbase                  | MongoDB                      |
| -------- | -------------------------- | ---------------------------- |
| Latency  | Very low (memory-first)    | Higher for some workloads    |
| Scaling  | Built-in auto-sharding     | Manual tuning often required |
| Caching  | Integrated cache           | External cache needed        |
| Query    | N1QL (SQL-like)            | Mongo Query Language         |
| Use Case | High throughput, real-time | General-purpose              |

Couchbase is preferred when **low latency, high throughput, and caching** are critical.

---

## 8. How YouTube Shows Video Preview Without Loading Full Video

* During upload, YouTube **extracts frames** at regular intervals
* Frames are combined into **sprite images** (thumbnail sheets)
* Each sprite is mapped to timestamps
* On hover, client fetches only the required thumbnail

This design minimizes bandwidth usage and improves user experience.

---

## 9. When to Choose Monolith Over Microservices

Choose **Monolithic Architecture** when:

* Team size is small
* Application is simple or early-stage
* Faster development and deployment is needed
* Operational overhead must be minimal

Microservices are better when scaling teams and features independently.

---

## 10. Designing a URL Shortener (bit.ly) – HLD Steps

### Step 1: Requirements

* Shorten long URLs
* Redirect users
* Handle high read traffic
* Optional analytics

### Step 2: Traffic Estimation

* Writes: Low
* Reads: Very high (read-heavy system)

### Step 3: High-Level Components

* API Gateway
* URL Shortening Service
* Redirection Service
* Database
* Cache (Redis)

### Step 4: Data Design

```
short_url -> long_url
```

* Use Base62 encoding for short keys
* Store mappings in NoSQL DB

### Step 5: Caching

* Cache hot URLs in Redis
* Reduce DB reads

### Step 6: Scalability

* Stateless services
* Horizontal scaling

### Step 7: Reliability

* Replication
* Cache fallback to DB

---

> These notes are structured for **system design interviews and real-world architecture discussions**.
