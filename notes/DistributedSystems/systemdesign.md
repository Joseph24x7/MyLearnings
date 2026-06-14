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
