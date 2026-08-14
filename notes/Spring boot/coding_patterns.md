# Coding Patterns in Spring Boot Interviews

---


## 1. Kafka Idempotent Consumer (Process Each Payment Only Once)


**Problem:** A Kafka listener processes payment events. The same message may be redelivered. Process each payment ID exactly once, commit offset only after success.

```java
@Entity
@Table(name = "processed_events")
public class ProcessedEvent {
    @Id
    private String eventId; // unique per event
    private String paymentId;
    private LocalDateTime processedAt;
}

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
    boolean existsByEventId(String eventId);
}
```

```java
@Component
public class PaymentEventListener {

    @Autowired private ProcessedEventRepository processedRepo;
    @Autowired private PaymentService paymentService;

    @KafkaListener(
        topics = "payment-events",
        containerFactory = "manualAckKafkaListenerContainerFactory"  // manual offset commit
    )
    public void handlePaymentEvent(PaymentEvent event,
                                   Acknowledgment ack) {
        // 1. Idempotency check
        if (processedRepo.existsByEventId(event.getEventId())) {
            log.info("Duplicate event {}, skipping", event.getEventId());
            ack.acknowledge(); // still commit offset so it doesn't redeliver
            return;
        }

        try {
            // 2. Process the payment
            paymentService.processPayment(event.getPaymentId(), event.getAmount());

            // 3. Mark as processed (idempotency record)
            processedRepo.save(new ProcessedEvent(
                event.getEventId(), event.getPaymentId(), LocalDateTime.now()
            ));

            // 4. Commit offset only after success
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Payment processing failed for eventId={}", event.getEventId(), e);
            // Do NOT acknowledge - message will be redelivered
        }
    }
}
```

```java
// Manual Ack config - MANUAL_IMMEDIATE commits right when ack.acknowledge() is called
@Bean
public KafkaListenerContainerFactory<?> manualAckKafkaListenerContainerFactory(
        ConsumerFactory<String, PaymentEvent> cf) {
    ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(cf);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    return factory;
}
```

**Key Points:**
- `AckMode.MANUAL_IMMEDIATE` - offset committed only when `ack.acknowledge()` is called
- Idempotency key = `eventId` stored in DB
- On failure: don't acknowledge → Kafka redelivers the message

---


## 2. Thread-Safe Warehouse Inventory Reserve


**Problem:** Multiple threads try to reserve stock simultaneously. Must never oversell.

```java
@Service
public class WarehouseService {

    // ConcurrentHashMap to hold per-product locks
    private final ConcurrentHashMap<String, ReentrantLock> productLocks =
            new ConcurrentHashMap<>();

    @Autowired
    private InventoryRepository inventoryRepo;

    public boolean reserve(String productId, int qty) {
        // Get or create a lock specific to this product
        ReentrantLock lock = productLocks.computeIfAbsent(
            productId, id -> new ReentrantLock()
        );

        lock.lock();
        try {
            Inventory inventory = inventoryRepo.findByProductId(productId);

            if (inventory == null || inventory.getStock() < qty) {
                return false; // Not enough stock
            }

            inventory.setStock(inventory.getStock() - qty);
            inventoryRepo.save(inventory);
            return true;

        } finally {
            lock.unlock(); // ALWAYS release lock
        }
    }
}
```

**Alternative: DB-level optimistic locking (recommended for distributed systems)**
```java
@Entity
public class Inventory {
    private String productId;
    private int stock;

    @Version  // Optimistic lock - DB throws OptimisticLockException on concurrent update
    private Long version;
}

// In service:
@Transactional
public boolean reserve(String productId, int qty) {
    Inventory inv = inventoryRepo.findByProductId(productId);
    if (inv.getStock() < qty) return false;
    inv.setStock(inv.getStock() - qty);
    inventoryRepo.save(inv); // Will fail if another thread updated same row
    return true;
}
// Catch OptimisticLockException in caller and retry
```

**Which approach to use:**
- **ReentrantLock** → Single JVM, in-process concurrency
- **`@Version` (Optimistic Locking)** → Distributed app with multiple pods (recommended)
- **DB `SELECT FOR UPDATE`** → Pessimistic locking at DB level (max safety, worst throughput)

---


## 3. OrderService - Revenue, Grouping, Top Spenders


```java
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Total revenue across all orders
    public double calculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    // Group orders by customerId
    public Map<String, List<Order>> groupOrdersByCustomer() {
        return orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(Order::getCustomerId));
    }

    // Top N customers by total spend
    public List<String> getTopSpentCustomers(int n) {
        return orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerId,
                        Collectors.summingDouble(order ->
                                order.getItems().stream()
                                        .mapToDouble(i -> i.getPrice() * i.getQuantity())
                                        .sum()
                        )
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
```
