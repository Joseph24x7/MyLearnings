# Java Libraries and Tools

## 1. What is MapStruct?
- Java mapping library
- Features:
  - Compile-time code generation
  - Type-safe mapping
  - Reduced boilerplate code
- Use cases:
  - DTO to Entity conversion
  - Entity to DTO conversion
  - Object transformation

### Example
```java
@Mapper
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO dto);
}
```

## 2. What is Lombok?
- Java development library
- Reduces boilerplate code
- Compile-time code generation

### Common Annotations
1. **@Data**
   - Generates getters/setters
   - toString()
   - equals()/hashCode()

2. **@Builder**
   - Implements builder pattern
   - Fluent object creation

3. **@AllArgsConstructor**
   - Generates all-args constructor
   - Parameter validation

4. **@Slf4j**
   - Creates logger instance
   - Simplified logging

5. **@Value**
   - Immutable classes
   - All fields final
   - Private constructor

### Benefits
- Clean code
- Reduced errors
- Better maintainability
- Increased productivity

### Example
```java
@Data
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
}
```
