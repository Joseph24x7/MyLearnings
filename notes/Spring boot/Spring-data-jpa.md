# Spring Data JPA

## 1. Spring Transactional Management
### Implementation in Spring Boot
- Add spring-boot-starter-data-jpa dependency
- Add `@Transactional` where the transaction is required

## 2. @Transactional Rollback Behavior
- By default: Rollbacks only for "unchecked exceptions"
- For both checked and unchecked: `@Transactional(rollbackFor = Exception.class)`
- Custom rollback: `@Transactional(rollbackFor = { CustomException.class }, noRollbackFor = { AnotherException.class })`
- For checked exceptions only: `@Transactional(rollbackFor = CustomCheckedException.class)`

## 3. Transaction Propagation Types
- **Propagation.REQUIRED** (default)
  - Checks for active transaction
  - Creates new if none exists
  - Reuses existing if present
- **Propagation.REQUIRES_NEW**
  - Creates new transaction regardless of existing ones
- **Propagation.SUPPORTS**
  - Non-transactional if no active transaction
  - Reuses existing if present
- **Propagation.MANDATORY**
  - Throws exception if no active transaction
  - Reuses existing if present
- **Propagation.NEVER**
  - Throws exception if active transaction exists

## 4. Hibernate Features

### 4.1 Advantages
- Object-Oriented (ORM) Framework
- Lazy loading support
- Caching capabilities
- Automatic DDL creation

### 4.2 Relationship Types
1. **One-to-One**
   
   **Table:**
   - Person (personid, name, passportid)
   - Passport (passportid, location, personid)
   
   **Entity:**
   - Person (personid, name, passport)
   - Passport (passportid, location, person)

2. **One-to-Many / Many-to-One**
   
   **Table:**
   - Author (authorId, name)
   - Book (bookId, title, authorId)
   
   **Entity:**
   - Author (authorId, name, list<books>)
   - Book (bookId, title, author)

3. **Many-to-Many**
   
   **Table:**
   - Students (studentsId, name)
   - Courses (coursesId, name)
   - Student_Course (studentsId, coursesId)
   
   **Entity:**
   - Students (studentsId, name, list<courses>)
   - Courses (coursesId, name, list<students>)

### 4.3 Annotations
- `@JoinColumn`: Used for one-to-one and one-to-many
- `@JoinTable`: Used for many-to-many

### 4.4 Caching
#### 4.4.1 First-Level Cache (Default)
- Session-Level Cache
- Destroyed when session is closed/cleared
- Not thread-safe
- Not designed for concurrent processes

#### 4.4.2 Second-Level Cache
- Session Factory-Level Cache
- Thread-safe
- Designed for concurrent processes
- Enable with: `spring.jpa.hibernate.cache.use_second_level_cache=true`

### 4.5 Important Hibernate Properties
- `hibernate.show_sql`: true/false - Display SQL statements
- `hibernate.hbm2ddl.auto`: create/update/validate - Schema generation behavior
- `hibernate.c3p0.min_size` & `hibernate.c3p0.max_size`: Connection pool size limits
