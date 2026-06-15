# Microservices Architecture

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


## 4. 12 Factors of Microservices: codebase, dependencies, dynamic properties, dev-prod parity, auto scaling, logs, admin processes.


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


## 5. Disadvantages/Challenges with microservices:


- When application is smaller, then going with microservices architecture is not useful.

- Security Concerns: Microservices typically communicate over the network, and securing these communications can be challenging. Additionally, there is a need to manage security at the individual service level, which can be more complex than securing a monolithic application.

- Data Consistency: Maintaining consistency across multiple microservices can be difficult. Transactions that span multiple services might be challenging to implement, and ensuring data.

- Resource Overhead: Each microservice typically requires its own resources, such as databases, messaging systems, and other infrastructure components. This can lead to increased resource overhead compared to a monolithic application that shares these resources.

- Monitoring and Debugging Challenges: The operation and deployment of microservices require additional infrastructure and tools for monitoring, logging, and managing the services.. Identifying and resolving issues in a microservices environment can be more challenging than in a monolithic architecture. Debugging and monitoring tools need to be sophisticated enough to handle the distributed nature of microservices.

---


## 6. When I have all below 4 in place, what will be the order to deploy?


-> EurekaServer ( below 3 services will be registerd to EurekaServer )
-> PaymentService
-> OrderService(uses PaymentService)
-> cloud-gateway ( routes to PaymentService & OrderService)

---


## 7. Few important design patterns of microservices:


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



## 8. How will you decide between Monolithic vs Microservices architecture?


### Decision Matrix

| Factor | Monolithic | Microservices |
|--------|-----------|--------------|
| **Team Size** | Small (< 10 devs) | Large (10+ teams) |
| **Project Complexity** | Low to medium | Complex, domain-driven |
| **Scalability Need** | Single service enough | Individual service scaling |
| **Deployment Frequency** | Weekly/monthly | Daily/hourly |
| **Data Consistency** | Important (ACID) | Eventual consistency OK |
| **Team Communication** | Tightly coupled | Autonomous teams |

### Decision Process
```
1. Start with MONOLITH if:
   - Small team
   - Simple application
   - Single domain
   - ACID transactions critical
   - Example: Blog, Todo app

2. Go MICROSERVICES when:
   - Multiple teams working independently
   - Different scaling needs per service
   - Independent deployment needed
   - Domains are bounded (DDD)
   - Example: Netflix, Amazon, Uber

3. Hybrid approach:
   - Start monolithic, evolve to microservices
   - Modular monolith: Clean code, easy to extract later
```

### Real-World Example
```
E-commerce Platform:
- Start: Monolith (faster to market)
- Grow: Extract Payment, Inventory, Shipping services
- Scale: Independent teams, different tech stacks
- Monitor: Circuit breakers, async messaging
```

---


## 9. Are you okay with working in monolith application?

Yes, absolutely. Both Monolithic and Microservices architectures have their place:
- **Monolith Advantages:** Excellent for simpler systems, early-stage applications, or small development teams. It features a single codebase, easier debugging, simple testing, and straightforward transactional consistency (ACID transactions are localized).
- **Practical Standpoint:** While microservices offer scaling and team autonomy, they introduce distributed systems complexity (network latency, service discovery, distributed transactions like Saga). 
- **Approach:** Working on a monolith is great because we can focus on building features quickly. If scalability issues arise, we can apply the **Strangler Fig Pattern** to extract specific bounded contexts into microservices later.
