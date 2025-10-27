# SQL Database Concepts

## 1. What is an Index?
- Helps database quickly locate matching rows
- Similar to hashtable structure
- Reduces need for full table scans

### Example
```sql
CREATE INDEX idx_employee_id ON Employees (EmployeeID);
```

## 2. What is a Trigger?
- Database objects that respond to specific events
- Automatically executed on data changes
- Part of DDL (Data Definition Language) commands

## 3. What are the differences between Views, Tables, and Synonyms?

### Views
- Virtual tables (read-only)
- Don't store data directly
- Based on SELECT queries

### Tables
- Primary data storage
- Support all operations:
  - INSERT
  - UPDATE
  - DELETE
  - SELECT

### Synonyms
- Alternative names for database objects
- Provides abstraction
- Enables flexible database access

## 4. ACID Property

- **Atomicity**: Spring ensures atomicity by wrapping a series of database operations within a single transaction, So it is either entirely successful or entirely rolled back in case of a failure.
- **Consistency**: Spring ensures that any transaction will bring the database from one valid state to another, maintaining all predefined rules, such as constraints and cascades.
- **Isolation**: Spring provides different isolation levels (like READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE) to control how transaction integrity is visible to other transactions.
- **Durability**: Once a transaction is committed in Spring, the changes are permanent and will survive any subsequent system failures, ensuring data integrity.

