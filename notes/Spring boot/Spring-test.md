# Spring Testing Guide – Interview Quick Reference

---

## 1. How to test private methods in JUnit?

| Approach | How |
|----------|-----|
| **Reflection** | `Method method = MyClass.class.getDeclaredMethod("privateMethod"); method.setAccessible(true); method.invoke(obj);` |
| **Test via public method** | Call public method that internally uses private method (preferred) |

---

## 2. How to mock static methods?

```java
// Mockito 3.4+ (try-with-resources required)
try (MockedStatic<Utils> mocked = Mockito.mockStatic(Utils.class)) {
    mocked.when(Utils::staticMethod).thenReturn("mocked");
    assertEquals("mocked", Utils.staticMethod());
}
```

| Tool | Support |
|------|---------|
| **Mockito 3.4+** | `mockStatic()` with try-with-resources |
| **PowerMock** | `@PrepareForTest`, `PowerMockito.mockStatic()` |

---

## 3. What is @Mock vs @Spy in Mockito?

| Aspect | @Mock | @Spy                                     |
|--------|-------|------------------------------------------|
| **Object** | Fake object | Real object (partial mock)               |
| **Default behavior** | Returns null/default | Calls real methods, unless mocked        |
| **Use case** | Full mock, stub all methods | Mock some, keep real behavior for others |

```java
@Mock UserRepository mockRepo;      // All methods return null/default
@Spy  UserService spyService;       // Real methods unless stubbed

when(mockRepo.findById(1L)).thenReturn(user);   // Must stub
doReturn(user).when(spyService).getUser(1L);    // Override specific method
```

---

## 4. What is @MockBean vs @Mock in Spring Boot Tests?

| Aspect | @Mock | @MockBean |
|--------|-------|-----------|
| **Context** | Plain Mockito | Spring ApplicationContext |
| **Replaces bean** | No | Yes (replaces in context) |
| **Use with** | `@ExtendWith(MockitoExtension.class)` | `@SpringBootTest`, `@WebMvcTest` |

```java
// @Mock - Unit test (no Spring)
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock UserRepository repo;
    @InjectMocks UserService service;
}

// @MockBean - Integration test (Spring context)
@SpringBootTest
class IntegrationTest {
    @MockBean UserRepository repo;  // Replaces real bean
    @Autowired UserService service;
}
```

---

## 5. How to test JPA Repositories or DB calls in JUnit?

| Approach | Annotation | Database |
|----------|------------|----------|
| **@DataJpaTest** | Slice test | H2 in-memory (default) |
| **@SpringBootTest** | Full context | Real or embedded DB |
| **Testcontainers** | Real DB in Docker | PostgreSQL, MySQL, etc. |

```java
@DataJpaTest
class UserRepositoryTest {
    @Autowired UserRepository repo;
    @Autowired TestEntityManager em;

    @Test
    void findByEmail_shouldReturnUser() {
        em.persist(new User("test@mail.com"));
        User found = repo.findByEmail("test@mail.com");
        assertThat(found.getEmail()).isEqualTo("test@mail.com");
    }
}
```

---

## 6. Explain at high level about Testcontainers?

**Testcontainers** = Java library to run Docker containers during tests.

| Feature | Description |
|---------|-------------|
| **Real DB testing** | PostgreSQL, MySQL, MongoDB in Docker |
| **Disposable** | Container starts before test, destroyed after |
| **CI/CD friendly** | Works in Jenkins, GitHub Actions |

```java
@Testcontainers
@SpringBootTest
class UserRepoTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }
}
```

---

## 7. What is the difference between @WebMvcTest and @SpringBootTest?

| Aspect | @WebMvcTest | @SpringBootTest |
|--------|-------------|-----------------|
| **Scope** | Web layer only | Full application context |
| **Loads** | Controllers, Filters, WebMvcConfigurer | All beans |
| **Speed** | Fast (slice test) | Slow (full context) |
| **MockMvc** | Auto-configured | Need `@AutoConfigureMockMvc` |
| **Use case** | Test controllers in isolation | Integration/E2E tests |

```java
// @WebMvcTest - Controller slice test
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean UserService service;  // Must mock dependencies
}

// @SpringBootTest - Full integration test
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {
    @Autowired MockMvc mockMvc;
    // Uses real beans
}
```

---
