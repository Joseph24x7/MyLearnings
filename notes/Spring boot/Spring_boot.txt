1. Bean scope - explain each. which is the default bean scope?

- Singleton (default): Spring container creates and manages only one instance of the bean throughout the application's lifecycle.
- Prototype: In Prototype scope, a new instance of the bean is created every time it's requested from the Spring container.
- Request: In Request scope, a new bean instance is created for every HTTP request in a web application.
- Session: In Session scope, a new bean instance is created for each HTTP session in a web application.
- Global Session: It's shared across all users and sessions within the application.

========================================================================================================================================================

2. Can we prototype bean created inside singleton bean?

	- yes possible, but only one instance of prototype bean will be created overall and we lose the behavior of prototype behavior

========================================================================================================================================================

3. bean life cycle:

 - container started
 - bean instantiated ( @component )
 - dependencies injected ( @Autowired )
 - init() method / @postcontruct 
 - actual process 
 - destroy() / @predestroy 

========================================================================================================================================================

4. different ways to inject beans in springboot / spring:

 - Constructor Injection ( @RequiredArgsConstructor )
 - Setter Injection 
 - Field Injection (@Autowired)
 - Method Injection ( @Bean )
 - XML injection

========================================================================================================================================================
5. Constructor Injection vs Field Injection(@Autowired vs @RequiredArgsConstructor):

Immutability: Constructor injection enforces immutability because once the object is constructed, its dependencies cannot be changed. Field injection allows for changes to injected dependencies after the object is created.
Testing: Constructor injection is easier to work with in unit testing because you can create instances with specific dependencies easily. Field injection can be less convenient for testing because you need to rely on reflection or other methods to set dependencies directly on fields.

========================================================================================================================================================

6. BeanFactory(lazy) vs ApplicationContext(eager):

 - Both are used to create beans
 - BF lazy loads the beans whenever it is required,
 - AC is eager loaded.


========================================================================================================================================================

7. Important features and Advantages of Spring boot:

 - Version management by using parent pom
 - Auto configurations
 - Embedded server
 - Easy to integration
 - One of the best Framework provider to implement microservices

========================================================================================================================================================

8. Database connection:

 - how to configure multiple database to be connected at the same time:
	- create multiple data source, session factory, transaction manager using @Bean and @Qualifier in configuration classes
	- In the entity scan we can specify and differentiate what all entities belongs to Data source 1 & Data source 2.

 - how to configure multiple database in which one will be a primary one:
	- by using @Primary annotation
	- If primary bean is not available, it will go for a fallback option for the secondary bean

 - how to use different database per environment:
	- @Profile for environment
	
 - how to use different database on shift basis (like 12am - 12pm one database, 12pm -12am different database):
	- @Conditional for property


========================================================================================================================================================

9. how can we set context-path for our complete project:

 - server.servlet.context-path=/your-context-path

========================================================================================================================================================

10. General dependencies used in your recent project

 - web
 - test
 - actuators
 - devtools
 - jpa
 - webflux
 - redis
 - lombok
 - mapstruct
 - testcontainers

========================================================================================================================================================

11. Annotations specific to Spring boot:

 - @SpringBootApplication
 - @SpringBootTest
 - @ConditionalOnProperty / @ConditionalOnClass
 - @EnableAutoConfiguration
 - @ComponentScan
 - @Profile

========================================================================================================================================================

12. Use of @Profile, @Conditional, @primary:

 - @Profile annotation is used to specify which beans should be loaded based on the active profiles. eg: @Profile("dev") , @Profile("default")
 - @Conditional annotation allows you to conditionally create or exclude beans based on custom conditions. eg: @Conditional(MyCustomCondition.class)
 - @Primary annotation is used to indicate the primary bean to be injected when there are multiple beans of the same type. If primary bean is not available, it will go for a fallback option for the secondary bean

========================================================================================================================================================

13. What is JAR, WAR, EAR:

 - JAR (Java Archive) files can be run as standalone applications, used as libraries, or used in web applications. eg: Lombok is a JAR.
 - WAR (Web Archive) files are used for packaging web applications.
 - EAR (Enterprise Archive) file used for  deploying complex enterprise applications that consist of multiple components such as multiple WARs & JARs.. Mainly used in packaging and deploying monolithic application into Jboss, WildFly Servers..

========================================================================================================================================================

14. How to write our own Auto Configuring classes and load them:

 - by using @Configuration and optional @Conditional(MyCondition.class). ex: WebConfig.java 
 - @EnableAutoConfiguration(exclude = WebConfig.class)
 
========================================================================================================================================================

15. How can we deploy a war based spring boot application in a traditional way? 

The ServletInitializer class you found in your Spring Boot application is used when you want to deploy your Spring Boot application as a traditional web application (WAR file) in a Servlet container, such as Apache Tomcat or Jboss. By extending SpringBootServletInitializer, you are providing a configuration that allows your Spring Boot application to run as a servlet-based web application.

========================================================================================================================================================

16. CommandLineRunner:

	- Functional interface and has run() method in it.
	- Whatever we define inside this run method will be executed after the Spring Boot application has started.

========================================================================================================================================================

17. What is circualar dependency and how to overcome it?

	- Suppose we have two classes, ClassA and ClassB, and they depend on each other
	- To break the circular dependency, you can introduce an interface or an abstract class and use constructor injection or setter injection to decouple the classes.
	- Now, both ClassA and ClassB depend on the DependencyInterface, which breaks the circular dependency.
	- this is known as dependency inversion principle.

Example:

@Configuration
public class AppConfig {
    @Bean
    public DependencyInterface classA(DependencyInterface classB) {
        return new ClassA(classB);
    }

    @Bean
    public DependencyInterface classB(DependencyInterface classA) {
        return new ClassB(classA);
    }
}

========================================================================================================================================================

18. @Qualifier vs @Resource

	- @Qualifier and @Resource to inject bean by its name.
	- @Qualifier is a Spring-specific annotation used to specify which bean to inject when multiple beans of the same type exist in the Spring application context.
	- @Resource is a standard Java EE annotation that can be used for dependency injection. It is not specific to Spring and can be used in other Java EE environments as well.


========================================================================================================================================================
19. bean by type and bean by name:

	- @Qualifier, @Resource, @Bean(name = "beanByName") to inject bean by its name.
	- @Autowired & @RequiredArgsConstructor, @Bean to inject bean by its type

========================================================================================================================================================

20.  how to create our own custom parser to parse json string to java object?

	- by using reflection api.

========================================================================================================================================================

21. Components of OAuth2/JWT token?

	- For JWT: Seperated by two dots.
		- Headers: {"alg": "HS256", "typ": "JWT"}
		- Payload (Base64-Encoded JSON):{"userDetails": "John Doe", "expiryTimestamp": 1516239022}
		- Signature (Encoded Header + Encoded Payload + Secret Key)

	- OAuth2:
		- Token type
		- Token Expiration
		- Scopes
		- client/User Information
	
========================================================================================================================================================

22. If there are more than one filter chain, how can we ensure the order?

	- by using @Order
	
========================================================================================================================================================

23. Latest features of Spring boot 3.x.x?

	- JDK 17+ and Jakarta EE 9+ Baseline
	- rest template will be deprecated and it will be RestClient
	- Observability
	- Spring Framework 6
	
========================================================================================================================================================

24. How we can create our own hibernate validation annotations

	1.  @Documented
		@Constraint(validatedBy = MyCustomValidator.class)
		@Target({ ElementType.FIELD, ElementType.METHOD })
		@Retention(RetentionPolicy.RUNTIME)
		public @interface MyCustomValidation {
			String message() default "Invalid value";

			Class<?>[] groups() default {};

			Class<? extends Payload>[] payload() default {};
		}


	2.  public class MyCustomValidator implements ConstraintValidator<MyCustomValidation, String> {

			@Override
			public void initialize(MyCustomValidation constraintAnnotation) {
				// Initialization, if needed
			}

			@Override
			public boolean isValid(String value, ConstraintValidatorContext context) {
				// Implement your custom validation logic here
				if (value == null) {
					return true; // Allow null values, or customize as needed
				}
				return value.startsWith("Custom"); // Example validation
			}
		}
		
	3. Set<ConstraintViolation<MyEntity>> violations = validator.validate(myEntity);
	
========================================================================================================================================================