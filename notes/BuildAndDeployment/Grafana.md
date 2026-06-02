# Monitoring Tools

## 1. What is Grafana?
- Grafana is a dashboard and visualization tool.
- It doesn’t store data itself, but connects to data sources (like Loki, Prometheus, Elasticsearch, etc.) to show charts, graphs, and alerts.
- Grafana can send alerts (via email, Slack, Teams, etc.) when metrics cross thresholds or issues are detected. In our case, we are using teams channel for sending alerts.

## 2. What is Elasticsearch?
- Elasticsearch is a search and analytics database.
- It is mainly used to store and search logs, events, and text data at scale.
- Tools like Kibana or Grafana connect to it to visualize and analyze logs.

## 3. What is Prometheus?
- Prometheus is a monitoring system that collects and stores time-series metrics.
- It is widely used to track CPU, memory, network usage, and application performance in Kubernetes/OpenShift.
- Grafana connects to Prometheus to create metrics dashboards and alerts.

---

## 4. Reducing Log File Sizes in Production (Log Management)

If log files are growing too fast and filling up disk space in production:

1. **Log Rotation (Logback/Log4j2 configuration):**
   - Configure a **Size-and-Time-Based Rolling Policy**.
   - Archives logs daily, caps file sizes (e.g., max 100MB per log file), and keeps a maximum history (e.g., delete files older than 30 days or cap total log folder size to 10GB).
2. **Adjust Logging Levels:**
   - Run production on `INFO` or `WARN` level. Avoid `DEBUG` or `TRACE` logging in production as they produce a massive amount of disk I/O and text.
3. **Asynchronous Logging:**
   - Use asynchronous loggers to write logs on a separate background thread, so application execution is not blocked by slow disk writes.
4. **Log Aggregation (Centralized Logging):**
   - Instead of storing logs locally on server disks, use agents (like Filebeat or Fluentd) to instantly stream logs to a centralized log database (like Elasticsearch). Set a retention policy (TTL) in Elasticsearch to automatically delete old logs.

---

## 5. How to Monitor and Secure Microservices Applications (Summary)

To ensure a microservices system is healthy and secure:

### Monitoring:
1. **Metrics Collection:** Use **Spring Boot Actuator** to expose application metrics, scraped by **Prometheus** and visualized in **Grafana** (CPU, memory, database connection pool, active threads, HTTP error rate).
2. **Distributed Tracing:** Use **Sleuth/Micrometer Tracing** and **Zipkin/Jaeger** to inject a unique `Trace ID` and `Span ID` into HTTP headers. This allows you to track a request as it flows across multiple microservices.
3. **Health Probes:** Define `/actuator/health/readiness` and `liveness` endpoints for Kubernetes to restart unhealthy containers automatically.

### Securing:
1. **API Gateway Gateway-Level Security:** Terminate SSL/TLS at the gateway, perform OAuth2 authentication, check JWT signatures, and reject unauthorized requests before they reach downstream services.
2. **Service-to-Service Security (mTLS):** Use Mutual TLS (via Service Meshes like Istio) to encrypt and authenticate all internal communication between microservices.
3. **Secrets Management:** Keep database credentials and API keys out of code repositories by loading them from AWS Secrets Manager or HashiCorp Vault.