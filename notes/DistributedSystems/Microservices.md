# Microservices Architecture Questions and Answers

## 1. What are Monolith Application and its Disadvantages?

- In Monolith Architecture the application is build as a single unit. 
- Normally a monolith application have one large code base and it lack modularity

- ### Disadvantages of Monolith Application:
	- The code base get larger in size with time and hence it's very difficult to manage.
	- It is very difficult to introduce new technology as it affects the whole application.
	- A single bug in any module can bring down the whole application
	- It is very difficult to scale a single module. One has to scale the whole application
	- Continuous deployment is extremely difficult. Large monolithic applications are actually an obstacle to frequent deployments. In order to update one component, we have to redeploy the entire application.

---

## 2. What are Microservices?
- While Monolith Application works a single component, a Microservice Architecture breaks it down to independent standalone small applications, each serving one particular requirement.
- Within this microservice architecture, the entire functionality is split in independent deployable module which communicate with each other through API's(RESTful web services )

- ### Advantages of Microservices
	- All the services are independent of each other. Therefore testing and deployment is easy as compare to Monolith application.
	- With microservice architecture, it's easy to build complex applications.
	- It will give flexibility to choose technologies and framework for each microservices independently 
	- Development Teams can work on individual services, enabling specialization.
	- Can scale individual services independently, based on demand.
	- Independent service deployment, allowing faster releases.
	- Isolated failures in one service do not affect the entire system.
	- Easier to set up failover mechanisms for specific services.

---

## 3. How to start with Micro services?

- If u have a monolith application, Identify all possible standalone functionalities.
- Once u have identify them, you need to create standalone projects, we are taking spring boot to create these microservices.
- You  need them to interact with each other through some ways , It can be Rest Api or Messaging.
- You need load balancer.
- eureka for service discovery (useful during load balancing and cloud deployments)
- API gateways and many more stuff. 

---

## 4. Components of microservices:

- It should have its own server.
- It should have its own database.
- It should be fault tolerant.
- It should have its Service Discovery/Registry.
- Containerization
- Orchestration
- Authentication and Authorization
- Logging and Monitoring
- Configuration Management

---

## 5. 12 Factors of Microservices: codebase, dependencies, dynamic properties, dev-prod parity, auto scaling, logs, admin processes.

- Codebase & version control: eg: Github/bitbucket
	- Example: Imagine you're building a microservices-based e-commerce platform. Each microservice, like product catalog, shopping cart, and user authentication, should have its own codebase. This separation allows teams to work independently on these services, updating and deploying them without affecting others.
	
- Dependencies: eg: pom.xml
	- Explicitly declare and isolate dependencies
	
- Config: eg: cloud config server, cut config. 
	- Example: When deploying a payment processing microservice, you would store API keys, payment gateway URLs, and other configuration settings in environment variables. This allows you to change payment gateway providers without modifying the code.
	
- Backing service: eg: downstream systems, database, etc..,
	- Example: This principle provides flexibility to change the backing service implementations without major changes to the application. Pluggability can be best achieved by using an abstraction like JPA over an RDBMS data source and using configuration properties (like a JDBC URL) to configure the connection.
	
- Build, release, run: 
	- Build: we compile the source code and build a Docker image.
	- Release: we tag the image and push it to a registry.
	- Run: we pull the image from the registry and run it as a container instance.
	- Example: A continuous integration/continuous deployment (CI/CD) pipeline should be set up for each microservice. Changes are built, released, and run independently for each service. This ensures that updates are made in a controlled and isolated manner.
	
- Processes: eg: Autoscaling in AWS.
	- Example: Stateless processes give the application an ability to scale out quickly to handle a sudden increase in traffic and scale in when the traffic to the system decreases.
	
- Port binding: eg: bind the port using nginx or any gateway.
	- Example: Port Binding refers to an application binding itself to a particular port and listening to all the requests from interested consumers on that port.
	
- Concurrency - Stateless Applications Help to Scale Out
	- Instead of making a single process even larger, we create multiple processes and then distribute the load of our application among those processes.
	- Example: Traditionally, whenever an application reached the limit of its capacity, the solution was to increase its capacity by adding RAM, CPU, and other resources - a process called vertical scaling. Horizontal scaling or "scaling out", on the other hand, is a more modern approach, meant to work well with the elastic scalability of cloud environments. Instead of making a single process even larger, we create multiple processes and then distribute the load of our application among those processes.

- Disposability:
	- Example: A chat application's WebSocket service should be designed to start quickly and shut down gracefully. This way, it can be easily restarted or replaced without disrupting the chat service, which is essential for maintaining uptime.
	
- Dev/Prod Parity - Build Once - Ship Anywhere:
	- The purpose of dev/prod parity is to ensure that the application will work in all environments ideally with no changes.

- Logs:  eg. Sync Logs
	- Example: A log analysis microservice collects log data from various microservices in a streaming fashion and stores them for analysis. This centralized log management helps in troubleshooting issues and monitoring the health of the entire system.

- Admin Processes: eg: Admin UI
	- Example: When scaling a microservice cluster up or down, an administrative process manages the scaling activities. This process is separate from the main application code and is responsible for tasks like launching new instances or updating configurations without affecting user requests.

---

## 6. Disadvantages/Challenges with microservices:

- When application is smaller, then going with microservices architecture is not useful.

- Security Concerns: Microservices typically communicate over the network, and securing these communications can be challenging. Additionally, there is a need to manage security at the individual service level, which can be more complex than securing a monolithic application.

- Data Consistency: Maintaining consistency across multiple microservices can be difficult. Transactions that span multiple services might be challenging to implement, and ensuring data.

- Resource Overhead: Each microservice typically requires its own resources, such as databases, messaging systems, and other infrastructure components. This can lead to increased resource overhead compared to a monolithic application that shares these resources.

- Monitoring and Debugging Challenges: The operation and deployment of microservices require additional infrastructure and tools for monitoring, logging, and managing the services.. Identifying and resolving issues in a microservices environment can be more challenging than in a monolithic architecture. Debugging and monitoring tools need to be sophisticated enough to handle the distributed nature of microservices.

---

## 7. Need of Eureka in terms of Service Registry:

- Service Discovery: Eureka provides a mechanism for service discovery, allowing services to find and communicate with each other dynamically. In a microservices environment, services often come and go, and having a dynamic service discovery mechanism is essential.

- Dynamic Scaling: Microservices applications often require the ability to scale services up or down based on demand. Eureka enables dynamic scaling by allowing newly instantiated services to register themselves with the registry, and other services can dynamically discover and communicate with them.

- Load Balancing: Eureka can be integrated with load balancing mechanisms to distribute incoming requests among multiple instances of a service. This helps in optimizing resource utilization and improving the overall performance and availability of the application.

- Resilience: Eureka helps in building resilient systems by allowing services to be resilient to failures. If a service instance fails, Eureka can quickly detect the failure and route traffic to healthy instances, minimizing downtime and improving system reliability.

---

## 8. Limitations of Eureka:

- Only the services which are registered to Eureka can do communication to the other services registered into eureka.

---

## 9. Fault Tolerance in Microservices:

- Fault tolerance is the property that enables a system to continue operating properly in the event of the failure of some of its components.
- It falls under circuit breaker design pattern of microservices.
- To implement this: Hystrix, Resilience4J

---

## 10. use of Resilience4J in Fault Tolerance/Circuit breaker?

- ### States of Resilience4J and its purposes:

	- CLOSED: When all the requests are successfully passing through without much failures.
	- OPEN: When the failure rate of requests exceeds a configured threshold, the Circuit Breaker transitions to the OPEN state. In this state, all incoming requests are blocked, and the Circuit Breaker does not allow any requests to pass through.
	- HALF_OPEN: After a specified duration in the OPEN state, the Circuit Breaker transitions to the HALF_OPEN state. In this state, a limited number of requests are allowed to pass through to test whether the backend service has recovered.

- ### Important Properties:

	- slidingWindowSize: This is the number of calls that the Circuit Breaker tracks for the sliding window. It affects how quickly the Circuit Breaker can transition between states.

	- minimumNumberOfCalls: The minimum number of calls that must be evaluated before the Circuit Breaker can transition to an open or closed state. It helps in preventing the Circuit Breaker from opening too quickly.

	- permittedNumberOfCallsInHalfOpenState: The number of permitted calls in the half-open state. When the Circuit Breaker transitions from open to half-open, it allows a limited number of calls to determine if the backend is still failing.

	- failureRateThreshold: The failure rate at which the Circuit Breaker opens. If the failure rate exceeds this threshold, the Circuit Breaker transitions to the open state.

---

## 11. Difference between handling the failures using exception vs handling failures using Resilience4J/Hystrix circuit breakers?

- When the ciruit breaker goes to OPEN state, it skips making a downstream calls
- But with exceptions it will hit the downstream everytime.

- Once the downstream is up, it will goe to HALF_OPEN state, where it will get the responses back from downstream.

---

## 12. API Gateway uses:

- Interface inbetween Front end & Microservice.
- Routing the requests
- Authentication/Authorization
- /Logging/ Monitoring/etc..
- Versioning
- Rate Limiters
- Load Balancing

---

## 13. How to implement API Gateway:

- Netflix API Gateway - Zuul (blocking API Gateway)
- Spring cloud API Gateway (non-blocking API Gateway)

---

## 14. When I have all below 4 in place, what will be the order to deploy?

-> EurekaServer ( below 3 services will be registerd to EurekaServer )
-> PaymentService
-> OrderService(uses PaymentService)
-> cloud-gateway ( routes to PaymentService & OrderService)

---

## 15. Terminalogies used in API-Gateway?

-> route: routes to the particular microservices contains the id, uri & predicate.
-> predicate: context path of a microservices.

---

## 16. How to handle fault tolerance in a microservice using kafka?

- Use Replication: Kafka supports data replication for fault tolerance. Configure your Kafka topics to have multiple replicas to ensure data durability. If a Kafka broker or instance fails, another replica can take over, ensuring data availability.
- Consumer Group: Use Kafka consumer groups for your microservices. If one consumer instance fails, Kafka will automatically rebalance the workload among the remaining consumers in the group. This ensures that no messages are lost.
- Circuit Breakers and Retries: Implement circuit breakers and retry mechanisms in your microservices. If a service or component experiences temporary issues, retries can help recover from transient failures. Circuit breakers can prevent your system from making repeated requests to a failing service.
- Backup and Restore: Set up backup and restore processes for Kafka data. Regularly back up your Kafka topics and configurations so that you can recover data in case of catastrophic failures.

---

## 17. API Gateway vs Load Balancing vs Service Registry:

- ### API Gateway:
	- Centralized API Management
	- Routing and Load Balancing
	- Security, rate limiting, and traffic encryption (HTTPS/SSL).
	- Analytics and Monitoring

- ### Load Balancing:
	- Scalability: Load balancers can automatically scale the number of server instances based on traffic load, allowing for efficient resource utilization.
	- Optimized Performance: They can route traffic to the closest or least loaded server, optimizing response times and reducing latency.
	- Health Checks: Load balancers can continuously monitor the health of server instances and route traffic only to healthy instances.

- ### Service Registry:
	- keep track of available services and their instances, making it easy for services to discover and communicate with each other dynamically.
	- Load Balancing.
	- Resilience: In case of instance failures, service registries can update their information and redirect traffic to healthy instances, improving system resilience.
	- Consistency: Service registries ensure that services always have access to up-to-date information about the available instances, which can be critical in dynamic, containerized environments.

---

## 18. Few important design patterns of microservices:

- ### Fault Tolerance: 
    - Implement resilience patterns such as retries, timeouts, and fallbacks to handle network and service failures.

- ### Circuit Breaker:
	- Example: Implement the Circuit Breaker pattern (e.g., with tools like Hystrix) to handle failures gracefully and prevent cascading failures.

- ### Saga Pattern:
	- The Saga design pattern is a way to manage data consistency across microservices in distributed transaction scenarios.
	- Saga pattern can be achieved using Choreography vs. Orchestration:
		- Choreography - where each service communicates with others through events
		- Orchestration - where a central service manages the flow of activities in a workflow.

---

## 20. Explain SAGA Design Pattern in Detail

### What is SAGA?
SAGA is a pattern to maintain **data consistency across multiple microservices** without using distributed transactions (2PC). It breaks a transaction into a sequence of local transactions.

### Why SAGA?
- Distributed transactions (2PC) don't scale well in microservices
- Each microservice has its own database
- Need eventual consistency across services

### Two Implementation Approaches

#### 1. Choreography (Event-Based)
Each service publishes events and listens to events from other services.

```
Order Service → [OrderCreated] → Payment Service
Payment Service → [PaymentCompleted] → Inventory Service  
Inventory Service → [InventoryReserved] → Shipping Service
```

**Pros:** Simple, loosely coupled, no single point of failure
**Cons:** Hard to track, cyclic dependencies possible

#### 2. Orchestration (Central Coordinator)
A central orchestrator service controls the flow.

```
Orchestrator → Order Service → "Create Order"
Orchestrator → Payment Service → "Process Payment"
Orchestrator → Inventory Service → "Reserve Stock"
Orchestrator → Shipping Service → "Ship Order"
```

**Pros:** Easy to track, centralized logic, clear flow
**Cons:** Single point of failure, orchestrator complexity

### Compensating Transactions (Rollback)
If any step fails, SAGA executes **compensating transactions** to undo previous steps.

```
SUCCESS: Order → Payment → Inventory → Shipping ✓

FAILURE at Inventory:
  Inventory fails → Compensate Payment (Refund) → Compensate Order (Cancel)
```

### Example: E-Commerce Order Flow

| Step | Service | Action | Compensating Action |
|------|---------|--------|---------------------|
| 1 | Order | Create Order | Cancel Order |
| 2 | Payment | Charge Customer | Refund Customer |
| 3 | Inventory | Reserve Stock | Release Stock |
| 4 | Shipping | Ship Order | Cancel Shipment |

### Implementation with Spring + Kafka
```java
// Orchestrator
@Service
public class OrderSagaOrchestrator {
    
    public void createOrder(OrderRequest request) {
        // Step 1: Create Order
        kafkaTemplate.send("order-topic", new CreateOrderEvent(request));
    }
    
    @KafkaListener(topics = "order-created")
    public void onOrderCreated(OrderCreatedEvent event) {
        // Step 2: Process Payment
        kafkaTemplate.send("payment-topic", new ProcessPaymentEvent(event));
    }
    
    @KafkaListener(topics = "payment-failed")
    public void onPaymentFailed(PaymentFailedEvent event) {
        // Compensate: Cancel Order
        kafkaTemplate.send("order-topic", new CancelOrderEvent(event));
    }
}
```

### Key Points
- **Eventual Consistency** - Not immediate, but guaranteed eventually
- **Idempotency** - Each step must be idempotent (safe to retry)
- **Timeout Handling** - Handle cases where services don't respond
- **State Tracking** - Track saga state for recovery

---

- ### Containerization and Orchestration: 
    - Use containerization (e.g., Docker) and container orchestration (e.g., Kubernetes) for managing and deploying microservices.

- ### Blue-Green Deployments: 
    - Use blue-green deployments to minimize downtime during updates by switching traffic from the old version (blue) to the new version (green).

- ### Command Query Responsibility Segregation (CQRS): 
	- Commands are instructions that indicate a desired change in the state of an entity. these commands execute operations such as Insert, Update, and Delete.
	- Queries are used to retrieve information from a database. Queries objects just return data and make no modifications to it.
	- We can achiveve Improved performance out of Segregating Commands and Queries.
	- Can be achieved by database replicas, master-slave mechanism.

## 19. Horizontal Scaling vs Vertical Scaling:

- ### Horizontal Scaling (Scaling Out):
	- Horizontal scaling means adding more instances of the same microservice to your application.
	- It's like adding more identical workers to handle a specific job. Each worker can process requests independently.
	- It is cost-effective and often used to handle increased demand by distributing the workload.

- ### Vertical Scaling (Scaling Up):
	- Vertical scaling means increasing the capacity of a single microservice instance by adding more resources (like CPU, RAM, or storage) to it.
	- It's like giving a single worker more powerful tools to handle a job. This can make that worker more capable.
	- It can be more expensive and may have limitations based on the hardware.

---
