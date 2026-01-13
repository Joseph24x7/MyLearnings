# SQL Database Concepts

## 1. Difference between SQL and NoSQL

| Feature | SQL | NoSQL |
|---------|-----|-------|
| **Data Model** | Relational (tables with rows and columns) | Document, Key-value, Wide-column, Graph |
| **Schema** | Fixed schema, predefined structure | Dynamic/flexible schema |
| **Scalability** | Vertical scaling (CPU, RAM, storage) | Horizontal scaling (multiple servers/nodes) |
| **Consistency Model** | ACID (Atomicity, Consistency, Isolation, Durability) | BASE (Basically Available, Soft state, Eventually consistent) |
| **Transaction Support** | Full transaction support with complete rollback | Limited transaction and rollback support |
| **Query Language** | Structured Query Language (SQL) | Varies by database (MongoDB uses MQL, etc.) |
| **Best For** | Complex queries, transactions, data integrity | Large volumes, flexible data, high scalability |
| **Examples** | MySQL, PostgreSQL, Oracle, SQL Server | MongoDB, Cassandra, Redis, DynamoDB |

---

## 2. Types of Normalization Techniques

Normalization is the process of organizing data to reduce redundancy and improve data integrity.

### First Normal Form (1NF)
- Each column should contain atomic (indivisible) values.
- Each column should have a unique name.
- Order of rows and columns doesn't matter.
- No repeating groups of columns.

### Second Normal Form (2NF)
- Must be in 1NF.
- All non-key attributes must be fully dependent on the entire primary key.
- Eliminates partial dependencies.

### Third Normal Form (3NF)
- Must be in 2NF.
- No transitive dependencies (non-key columns should not depend on other non-key columns).
- Every non-key attribute must depend only on the primary key.

### Boyce-Codd Normal Form (BCNF)
- Must be in 3NF.
- For every functional dependency X → Y, X must be a super key.
- Stricter version of 3NF.

### Fourth Normal Form (4NF)
- Must be in BCNF.
- No multi-valued dependencies.
- A table should not have more than one independent multi-valued fact about an entity.

### Fifth Normal Form (5NF)
- Must be in 4NF.
- No join dependencies.
- Cannot be decomposed into smaller tables without loss of data.

---

## 3. Types of Indexes in SQL

### 1. Clustered Index
- Determines the physical order of data in a table.
- Only one clustered index per table.
- Primary key creates a clustered index by default.
```sql
CREATE CLUSTERED INDEX idx_emp_id ON Employees(EmployeeID);
```

### 2. Non-Clustered Index
- Creates a separate structure with pointers to data rows.
- Multiple non-clustered indexes can exist per table.
- Does not affect physical order of data.
```sql
CREATE NONCLUSTERED INDEX idx_emp_name ON Employees(Name);
```

### 3. Unique Index
- Ensures all values in the indexed column(s) are unique.
- Can be clustered or non-clustered.
```sql
CREATE UNIQUE INDEX idx_email ON Employees(Email);
```

### 4. Composite Index
- Index on multiple columns.
- Order of columns matters for query optimization.
```sql
CREATE INDEX idx_name_dept ON Employees(Name, Department);
```

### 5. Full-Text Index
- Used for full-text searches on large text columns.
- Supports linguistic searches.
```sql
CREATE FULLTEXT INDEX ON Articles(Content);
```

### 6. Covering Index
- Includes all columns needed by a query.
- Avoids table lookup (index-only scan).
```sql
CREATE INDEX idx_covering ON Orders(OrderDate) INCLUDE (CustomerID, Amount);
```

---

## 4. What is an Index?
- Helps database quickly locate matching rows
- Similar to hashtable structure
- Reduces need for full table scans

### Example
```sql
CREATE INDEX idx_employee_id ON Employees (EmployeeID);
```

---

## 5. What is a Trigger?
- Database objects that respond to specific events
- Automatically executed on data changes
- Part of DDL (Data Definition Language) commands

---

## 6. What are the differences between Views, Tables, and Synonyms?

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

---

## 7. ACID Properties in Detail

### Atomicity
- **Definition:** A transaction is treated as a single, indivisible unit of work.
- **Behavior:** Either all operations within a transaction succeed, or none of them do.
- **Implementation:** Uses transaction logs to track changes. If failure occurs, rollback undoes all changes.
- **Example:** In a bank transfer, both debit and credit must succeed. If credit fails, debit is rolled back.

### Consistency
- **Definition:** A transaction brings the database from one valid state to another valid state.
- **Behavior:** All data integrity constraints, triggers, and rules must be satisfied after transaction completion.
- **Implementation:** Database validates all constraints before committing a transaction.
- **Example:** If a rule states account balance cannot be negative, any transaction violating this is rejected.

### Isolation
- **Definition:** Concurrent transactions execute independently without interfering with each other.
- **Behavior:** Intermediate states of a transaction are invisible to other transactions.
- **Implementation:** Uses locking mechanisms and isolation levels (READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE).
- **Example:** If two users are updating the same record simultaneously, each sees a consistent view of data.

### Durability
- **Definition:** Once a transaction is committed, its changes are permanent.
- **Behavior:** Committed data survives system crashes, power failures, or other failures.
- **Implementation:** Uses write-ahead logging (WAL) and persistent storage.
- **Example:** After a successful money transfer confirmation, the transfer remains recorded even if system crashes.

---

## 8. Isolation Techniques in Transaction Management

### 1. READ UNCOMMITTED (Lowest Isolation)
- **Behavior:** Transactions can read uncommitted changes from other transactions.
- **Problem:** Dirty reads are possible.
- **Use Case:** Rarely used; only when approximate data is acceptable.

### 2. READ COMMITTED (Default in most databases like PostgreSQL, SQL Server)
- **Behavior:** Transactions can only read committed changes.
- **Problem:** Non-repeatable reads possible (same query may return different results within a transaction).
- **Use Case:** General-purpose applications where dirty reads are unacceptable.

### 3. REPEATABLE READ (Default in MySQL InnoDB)
- **Behavior:** Once a row is read, it cannot be changed by other transactions until current transaction completes.
- **Problem:** Phantom reads possible (new rows can be inserted by other transactions).
- **Use Case:** Financial applications requiring consistent reads within a transaction.

### 4. SERIALIZABLE (Highest Isolation)
- **Behavior:** Transactions are executed as if they were sequential (one after another).
- **Problem:** Lowest concurrency and performance.
- **Use Case:** Critical operations where complete isolation is mandatory.

### Common Problems and Isolation Levels

| Problem | Description | Prevented By |
|---------|-------------|--------------|
| **Dirty Read** | Reading uncommitted data | READ COMMITTED and above |
| **Non-Repeatable Read** | Same query returns different data | REPEATABLE READ and above |
| **Phantom Read** | New rows appear in repeated query | SERIALIZABLE only |

### Default Isolation Levels
- **MySQL (InnoDB):** REPEATABLE READ
- **PostgreSQL:** READ COMMITTED
- **SQL Server:** READ COMMITTED
- **Oracle:** READ COMMITTED

---

## 9. Types of Views

### 1. Simple View
- Based on a single table.
- Can perform DML operations (INSERT, UPDATE, DELETE).
- Does not contain functions or GROUP BY.
```sql
CREATE VIEW emp_view AS SELECT id, name, department FROM Employees;
```

### 2. Complex View
- Based on multiple tables (uses JOINs).
- May contain functions, expressions, or GROUP BY.
- DML operations may be restricted.
```sql
CREATE VIEW emp_dept_view AS 
SELECT e.name, d.dept_name 
FROM Employees e JOIN Departments d ON e.dept_id = d.id;
```

### 3. Materialized View
- Stores the result set physically (unlike regular views).
- Needs to be refreshed to get updated data.
- Improves query performance for complex queries.
```sql
CREATE MATERIALIZED VIEW sales_summary AS 
SELECT product_id, SUM(amount) as total 
FROM Sales GROUP BY product_id;
```

### 4. Indexed View (SQL Server)
- A view with a unique clustered index.
- Physically stores data like a materialized view.
- Automatically maintained by the database engine.

### 5. Partitioned View
- Combines horizontally partitioned data from multiple tables.
- Appears as a single table to the user.
- Used for distributing data across servers.

---

## 10. UNION vs UNION ALL

### UNION
- Combines result sets of two or more SELECT statements.
- **Removes duplicate rows** from the final result.
- Performs additional sorting/comparison to eliminate duplicates.
- **Slower** due to duplicate elimination overhead.

```sql
SELECT name FROM Employees
UNION
SELECT name FROM Contractors;
-- Returns unique names from both tables
```

### UNION ALL
- Combines result sets of two or more SELECT statements.
- **Keeps all rows** including duplicates.
- No additional processing for duplicate removal.
- **Faster** than UNION.

```sql
SELECT name FROM Employees
UNION ALL
SELECT name FROM Contractors;
-- Returns all names including duplicates
```

### Key Differences

| Feature | UNION | UNION ALL |
|---------|-------|-----------|
| **Duplicates** | Removes duplicates | Keeps all duplicates |
| **Performance** | Slower (sorting required) | Faster (no sorting) |
| **Use Case** | When unique results needed | When all results needed or no duplicates exist |

### Best Practice
- Use `UNION ALL` when you know there are no duplicates or when duplicates are acceptable.
- Use `UNION` only when duplicate elimination is required.
