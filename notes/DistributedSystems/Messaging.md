# Messaging Systems in Microservices

## 1. What is the difference between Kafka and RabbitMQ?

- **Kafka**
    - Always persists messages to **disk**
    - Retained based on **time or size**
    - Each consumer maintains its **own offset**, Multiple consumers can read the **same message**
    - Message Replay Supported resetting offsets
    - High throughput (millions of messages/sec), Optimized for sequential disk writes
    - Partition support for parallelism and scalability
    - ### Use cases: In Ecommerce for Analytics Service, Log Aggregation.


- **RabbitMQ**
    - Messages stored in **memory by default**, can be persisted to disk
    - Messages removed after **consumer acknowledgment**
    - Same message cannot be re-read by other consumers
    - Message Replay not supported
    - Low latency, Best for task queues and short-lived jobs
    - No partitioning, relies on queues for load balancing
    - ### Use cases: In Ecommerce for sending Order Confirmation Emails.

---

## 2. Can same message be consumed twice in kafka?

- Yes, Kafka allows the same message to be consumed multiple times by different consumers or consumer groups.
- Each consumer group maintains its own offset, allowing multiple groups to read the same message independently.
- This feature is useful for scenarios where different applications or services need to process the same data in
  different ways.

---

## 3. Can same message be consumed twice in same consumer group?

- No, within the same consumer group, each message is consumed only once.
- Kafka ensures that each partition is assigned to only one consumer within a group, preventing duplicate consumption.
- If a consumer fails, Kafka reassigns the partitions to other consumers in the group, but the message will still be
  processed only once by the group.

---

## 4. What is the difference between Topic and Partition in Kafka?

- **Topic**
    - A logical channel to which producers send messages and from which consumers read messages.
    - Represents a stream of data, identified by a name.
- **Partition**
    - A physical division of a topic, allowing for parallelism and scalability.
    - Each partition is an ordered, immutable sequence of messages that is continually appended to.

---

## 5. can same partition be assigned to multiple consumers in same consumer group?

- No, in Kafka, a partition can be assigned to only one consumer within the same consumer group.
- This ensures that messages from a partition are processed in order and prevents duplicate consumption within the
  group.
- If there are more consumers than partitions, some consumers will remain idle.

---

## 6. What is the purpose of groupId/consumer group in Kafka?

- The `groupId` identifies a consumer group in Kafka.
- It allows multiple consumers to work together to consume messages from a topic.
- Each consumer in the group is assigned a subset of partitions, enabling parallel processing.
- The `groupId` also helps manage offsets, ensuring that each consumer group maintains its own position in the topic.

---

## 7. Can we add or create new partitions to existing topic in Kafka?

- Yes, Kafka allows you to add new partitions to an existing topic.
- This can be done using the Kafka command-line tools or programmatically via the Kafka Admin API.
- Adding partitions can help improve parallelism and throughput, but it may affect the ordering of messages.

---

## 8. Can we add unique message Ids in Kafka messages?

- Yes, you can add unique message IDs in Kafka messages by including them as part of the message payload or as message
  headers.
- This can help with tracking, deduplication, and ensuring idempotency in message processing.

---

## 9. Sample code to create Kafka Topic in command line?

- bootstrap-server: Address of the Kafka broker (e.g., localhost:9092)

```bash
kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
``` 

- zookeeper: Address of the Zookeeper instance (e.g., localhost:2181)

```bash
kafka-topics.sh --create --topic my-topic --zookeeper localhost:2181 --partitions 3 --replication-factor 1
```

---

## 10. Is zookeeper and bootstrap-server same?

- No, Zookeeper and bootstrap-server are not the same.
- **Zookeeper** is a centralized service used by Kafka for managing configuration, leader election, and cluster
  metadata.
- **Bootstrap-server** refers to the address of the Kafka broker that clients use to connect to the Kafka cluster.

---

## 11. How to publish and consume messages in Kafka using Java and springboot?

- **Publishing Messages:**

```java

@Autowired
private KafkaTemplate<String, String> kafkaTemplate;

public void sendMessage(String topic, String message) {
    kafkaTemplate.send(topic, message);
}
```

- **Consuming Messages:**

```java

@KafkaListener(topics = "my-topic", groupId = "my-group")
public void listen(String message) {
    System.out.println("Received Message: " + message);
}
```

- ** configuration in application.properties:**

```
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group
spring.kafka.consumer.auto-offset-reset=earliest
```

---

## 12. If multiple instance of consumers with same group id are running, how kafka manages the message consumption?

- Kafka uses a partition assignment strategy to distribute partitions among consumers in the same group.
- Each partition is assigned to only one consumer within the group, ensuring that messages from a partition are
  processed by only one consumer.
- If a consumer instance fails, Kafka reassigns its partitions to other active consumers in the group.

---

## 13. What is an offset in Kafka?

- An offset in Kafka is a unique identifier assigned to each message within a partition.
- It represents the position of the message in the partition and is used by consumers to track their progress.
- Consumers can commit offsets to indicate which messages have been processed, allowing them to resume from the last
  committed offset in case of failure.

---

## 14. What are the advantages of using partitions in Kafka?

- **Scalability:** Partitions allow Kafka to scale horizontally by distributing data across multiple brokers.
- **Parallelism:** Multiple consumers can read from different partitions simultaneously, improving throughput.
- **Fault Tolerance:** If a broker fails, partitions can be reassigned to other brokers, ensuring data availability.
- **Load Balancing:** Partitions help distribute the load evenly across consumers in a consumer group.

---

## 15. Use of Header Segments in Kafka?

- Header segments in Kafka allow you to include additional metadata with each message.
- They can be used for purposes such as tracing, filtering, or routing messages.
- Headers are key-value pairs that can be accessed by producers and consumers without affecting the message payload.
- Coding Example:

```java
ProducerRecord<String, String> record = new ProducerRecord<>("my-topic", "key", "value");
record.

headers().

add("correlationId","12345".getBytes());
        kafkaTemplate.

send(record);
```

---

## 16. How to ensure Authentication and Authorization in Kafka whiling publishing and consuming messages?

- **Authentication:**
    - Kafka verifies identity using SASL(Simple Authentication and Security Layer) mechanisms like SCRAM.
    - Producer / Consumer must provide username & password
    - Broker config (server.properties):
      ```properties
          listeners=SASL_PLAINTEXT://localhost:9092
          security.protocol=SASL_PLAINTEXT
          sasl.enabled.mechanisms=SCRAM-SHA-256
      ```
    - Client config (producer/consumer):
  ```properties
        security.protocol=SASL_PLAINTEXT
        sasl.mechanism=SCRAM-SHA-256
        sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required
        username="order-service"
        password="order-secret";
    ```
- **Authorization:**
    - Use ACLs to control access to topics and consumer groups.
    - Grant producer permission:
  ```bash
    kafka-acls.sh --add \ --allow-principal User:order-service \ --operation Write \ --topic order-events
  ```
    - Grant consumer permission:
  ```bash
    kafka-acls.sh --add \ --allow-principal User:analytics-service \ --operation Read \ --topic order-events \ --group analytics-group
  ```

---

## 17. Explain about Kafka Retention Policy?

- Kafka retention policy determines how long messages are stored in a topic before being deleted.
- Retention can be configured based on time (e.g., 7 days) or size (e.g., 1 GB).
- Once the retention limit is reached, older messages are deleted to free up space.
- Retention settings can be configured at the topic level using properties like `retention.ms` and `retention.bytes`.

---

## 18. Explain the purpose of DLT or DLQ in messaging systems?

- DLT (Dead Letter Topic) or DLQ (Dead Letter Queue) is used to handle messages that cannot be processed successfully.
- When a message fails to be processed after a certain number of attempts, it is sent to the DLT/DLQ for further
  analysis or reprocessing.
- This helps prevent message loss and allows for better error handling in messaging systems.

---

## 19. Message Structure of Kafka

### Kafka Message Components

| Component | Purpose |
|-----------|---------|
| **Key** | Optional identifier for partitioning |
| **Value** | Actual message payload (JSON, Avro, Protobuf) |
| **Headers** | Custom metadata (added in Kafka 0.11) |
| **Timestamp** | Message creation time (ms since epoch) |
| **Partition** | Which partition message belongs to |
| **Offset** | Sequential number in partition |
| **Compression** | Optional (gzip, snappy, lz4, zstd) |

### Example Code
```java
ProducerRecord<String, String> record = new ProducerRecord<>(
    "order-topic",
    "order-123",  // Key
    "{\"orderId\":\"123\",\"amount\":100.00}"  // Value
);
record.headers().add("userId", "user-456".getBytes());

producer.send(record, (metadata, exception) -> {
    System.out.println("Topic: " + metadata.topic());
    System.out.println("Partition: " + metadata.partition());
    System.out.println("Offset: " + metadata.offset());
});
```

### Common Formats
- **JSON** - Flexible, human-readable
- **Avro** - Schema registry, version control
- **Protobuf** - Compact, language-neutral

---
