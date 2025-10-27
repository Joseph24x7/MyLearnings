# NoSQL Database Concepts

## 1. What are the differences between SQL and NoSQL?

### Key Differences:
1. **Data Model**
   - NoSQL databases support various data models:
     - Document
     - Key-value
     - Wide-column
     - Graph
   - Allows for more flexible data storage

2. **Scalability**
   - SQL: Vertical scaling (CPU, RAM, or storage)
   - NoSQL: Horizontal scaling (multiple servers or nodes)

3. **Consistency Model**
   - SQL: ACID (Atomicity, Consistency, Isolation, Durability)
   - NoSQL: BASE (Basically Available, Soft state, Eventually consistent)

4. **Transaction Support**
   - SQL: Supports complete rollback functionality
   - NoSQL: Limited rollback support

## 2. What are BASE properties?

### BASE Components:
1. **Basically Available**
   - System guarantees availability
   - Responds to all requests (successful or failed)

2. **Soft State**
   - System state may change over time
   - Even without input due to eventual consistency

3. **Eventually Consistent**
   - System will become consistent over time
   - All accesses will eventually return the last updated value
