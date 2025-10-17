# Messaging Systems in Microservices

## 1. What is the purpose of groupId/consumer group in Kafka?

### Consumer Groups
- Groups multiple consumers under same groupId
- Benefits:
  - Enables parallel processing
  - Provides load balancing
  - Assigns different partitions to each consumer

### Different GroupIds
- Creates separate consumer groups
- Characteristics:
  - Independent partition assignment
  - Separate offset management
  - Allows multiple groups to consume same topic

## 2. What are the differences between Queue and Kafka?

### Message Consumption
- **Queue**
  - Single consumer per message
  - Once consumed, message is removed
- **Kafka**
  - Multiple consumer groups can read same message
  - Messages persist based on retention policy

### Message Ordering
- **Kafka**
  - Maintains strict message order within partitions
  - Guaranteed sequential processing
- **Queue**
  - No guaranteed order during failures
  - May reprocess messages out of sequence

### Message Storage
- **Queue**
  - Removes messages after consumption
  - Temporary storage only
- **Kafka**
  - Retains messages
  - Allows replay using offsets
  - Configurable retention period

### Use Cases
- **Queue**
  - Simple point-to-point messaging
  - When message should be processed once
- **Kafka**
  - Event streaming
  - Data replication
  - When multiple consumers need same data

## 3. What are the advantages of using partitions in Kafka?

### Key Benefits
- Distributes load and data across multiple brokers and storage servers
- Each partition can be placed on different servers
- Enables handling more data and clients
- Supports concurrent message processing via multiple consumers
- Provides load balancing across brokers and consumers
- Supports replication for fault tolerance

## 4. What is Kafka replica and its usage?

### Overview
- A replica is a copy of a partition in a topic
- Core component of Kafka's fault-tolerant design

### Advantages and Features
- Enables fault tolerance through multiple partition copies
- Uses leader-follower model:
  - Leader handles reads and writes
  - Followers replicate data from leader
- Provides data durability and high availability
- Maintains In-Sync Replicas (ISR)
- Configurable replication factor per topic

## 5. What is an offset in Kafka?

### Definition and Purpose
- Position marker in a Kafka partition
- Unique identifier for message position
- Tracks consumer progress in partitions
- Critical for:
  - Fault tolerance
  - At-least-once message delivery
  - Efficient data processing
