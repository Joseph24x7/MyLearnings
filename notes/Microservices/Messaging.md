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
