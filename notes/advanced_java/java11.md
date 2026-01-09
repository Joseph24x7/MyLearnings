# Java Core Concepts (Java 8 → 11)

---

## 1. Java 11 Features (from Java 8 → 11):
- var Keyword for Local Variable Type Inference
- HTTP Client (standardized from incubator)
- Epsilon (No-Op) Garbage Collector
- Immutable Collections (List.of(), Set.of(), Map.of())

---

## 2. var Keyword for Local Variable Type Inference
- Introduced in Java 10, allows the compiler to infer the type of local variables.
- Example:
  ```java
  var list = new ArrayList<String>(); // Compiler infers ArrayList<String>
  ```
- Note: var can only be used for local variables, not for fields, method parameters, or return types.
- Benefits: Reduces boilerplate code and improves readability.
- Limitations: Type must be clear from the context; cannot be null.

---

## 3. HTTP Client (Standardized)
- Introduced in Java 11, provides a modern API for making HTTP requests.
- Supports synchronous and asynchronous requests.
- Synchronous Example:
  ```java
  HttpClient client = HttpClient.newHttpClient();
  HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://example.com"))
      .build();
  HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
  System.out.println(response.body());
  ```
- Asynchronous Example:
  ```java
  HttpClient client = HttpClient.newHttpClient();
  HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://example.com"))
      .build();
  client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(System.out::println);
  ```
---

## 4. Difference between HttpClient vs HttpURLConnection
| Feature               | HttpURLConnection                     | HttpClient                           |
|-----------------------|--------------------------------------|-------------------------------------|
| **Introduced In**     | Java 1.1                             | Java 11                           |
| **API Style**         | Imperative                           | Fluent and modern                   |
| **Asynchronous Support** | Limited (via threads)                | Built-in support for asynchronous requests |
| **HTTP/2 Support**    | No                                   | Yes                                 |
| **WebSocket Support** | No                                   | Yes                                 |
| **Ease of Use**       | More boilerplate code                | More concise and easier to use     |

----

## 5. Why HTTP/2 and WebSocket Support Are Important?

### HTTP/1.1:
- Like a single narrow road where only **one car can go at a time**.
- Browser opens 6 connections max → still lots of waiting in line.
- Every request sends big repetitive headers (cookies, user-agent, etc.) → wastes time and data.
- Result: pages load slowly, especially if they have many images, CSS, or JS files.

### HTTP/2 Support
- Turns that narrow road into a **50-lane super highway**.
- One single connection, but **hundreds of files travel together at the same time** (called multiplexing).
- Headers are compressed → less chatting, more speed.


### WebSocket Support
- Instead of the browser constantly asking “Any new messages?” (polling = waste of battery and time),
- WebSocket opens a **permanent open phone line** between your app and the server.
- Server can push new data instantly → messages, notifications, live scores, stock prices, multiplayer game moves appear in real time with zero delay.

### Summary Table

| Feature         | HTTP/1.1 (Old)         | HTTP/2 (Modern)             | WebSocket (Real-time)         |
|-----------------|------------------------|-----------------------------|-------------------------------|
| Speed           | Slow, lots of waiting  | Super fast page loading     | Not for loading, for live data |
| Connections     | Many (6 max usually)   | Just 1, but does everything | 1 permanent open line         |
| Best for        | Very old apps          | All normal websites & APIs  | Chat, notifications, gaming   |
| Feels like      | Waiting in a queue     | Everything loads together   | Messages appear instantly     |

---

## 6. Does RestTemplate, Webclient, RestClient in Spring Boot Latest versions use JDK HTTP/2 underneath?

| Client         | Supported Clients                                   | Uses JDK HttpClient?         | Notes                                                        |
|----------------|------------------------------------------------------|-------------------------------|--------------------------------------------------------------|
| RestTemplate   | HttpURLConnection, Apache HttpClient, OkHttp, Netty | No                            | Cannot use JDK HttpClient; defaults to HttpURLConnection.    |
| WebClient      | Reactor Netty, Jetty, Apache HttpClient             | No                            | Does NOT support JDK HttpClient; uses Reactor Netty by default. |
| RestClient     | Reactor Netty (default), JDK HttpClient (optional), Apache HttpClient | Yes (if configured) | Can use JDK HttpClient but NOT by default; default is Reactor Netty. |

---

## 7. Epsilon (No-Op) Garbage Collector
- Introduced in Java 11, Epsilon GC is a no-op garbage collector that does not reclaim memory.
- Primarily used for performance testing and memory pressure testing.
- Example Usage:
  ```bash
  java -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -Xmx512m -jar yourapp.jar
  ```
- Note: Since Epsilon GC does not reclaim memory, it is not suitable for production environments

---
## 8. Immutable Collections (List.of(), Set.of(), Map.of())
- Introduced in Java 9 and available in Java 11, these factory methods create immutable collections.
- Example:
  ```java
  List<String> immutableList = List.of("A", "B", "C");
  Set<Integer> immutableSet = Set.of(1, 2, 3);
  Map<String, Integer> immutableMap = Map.of("One", 1, "Two", 2);
  ```
- Benefits: Improved performance, thread-safety, and prevention of accidental modifications.
- Limitations: Attempting to modify these collections will throw `UnsupportedOperationException`.
- Use Cases: Suitable for constant data that should not change after creation.

---
