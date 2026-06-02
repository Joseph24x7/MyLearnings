# Docker Containerization Guide

## 1. How to dockerize an application?

### Step 1: Create Dockerfile
```dockerfile
FROM openjdk:17-oracle
EXPOSE 8081
ADD target/usermanagement.jar usermanagement.jar
ENTRYPOINT ["java","-jar","/usermanagement.jar"]
```

### Step 2: Configure Pipeline
```yaml
steps:
  - name: Build with Maven
    run: mvn clean install -Dmaven.version=3.9.3

  - name: Log in to Docker Hub
    run: docker login -u joseph24x7 -p ${{ secrets.DOCKERHUB_TOKEN }}

  - name: Build Docker image
    run: docker build -t joseph24x7/usermanagement .

  - name: Push image to Docker Hub
    run: docker push joseph24x7/usermanagement
```

## 2. Name few important Common Docker Commands?
- docker login -u username -p Password
- docker pull jenkins/jenkins:latest
- docker build -t joseph24x7/resumeatschecker:latest .
- docker run -p 8001:8001 joseph24x7/resumeatschecker:latest
- docker images <- To list the docker images
- docker ps <- To list the running containers
- docker stop containerId <- To stop a running container
- docker restart containerId <- To restart a running container

## 3. How to install docker Desktop?

- Enable Hyper-V virtualization in your System ( At present WSL-2 is recommended )
- Download the Linux kernel update package
- Run Ubuntu on Windows

## 4. How to create docker image using docker file in springboot application?

- Create a docker file in the root directory of your springboot application
- Use the below code snippet to create a docker file
```dockerfile
FROM openjdk:17-oracle
EXPOSE 8081
ADD target/usermanagement.jar usermanagement.jar
ENTRYPOINT ["java","-jar","/usermanagement.jar"]
```
- Build the docker image using the below command
```bash
docker build -t joseph24x7/usermanagement .
```
- Run the docker image using the below command
```bash
docker run -p 8081:8081 joseph24x7/usermanagement
```

---

## 5. Difference between Podman Desktop and Docker Desktop

| Feature | Docker | Podman |
|---------|--------|--------|
| **Architecture** | Daemon-based | Daemonless |
| **Rootless** | Optional (not default) | Default |
| **Security** | Good | Better (no daemon) |
| **CLI** | docker command | podman command |
| **Container Size** | Higher memory usage | Lower memory |
| **Adoption** | Industry standard | Growing |

**Key Difference:**
- Docker requires a daemon service; Podman runs containers directly as child processes
- Podman is more secure (no privileged daemon), Lighter (lower resource usage)
- Both are OCI-compliant and fully compatible

**When to use:**
- Docker: Team already using it, need enterprise support
- Podman: Security-critical, resource-constrained environments

---

## 6. How will you build, deploy, and monitor your application?

### Build Phase
```
1. Code commit → Trigger Jenkins/GitHub Actions pipeline
2. Maven build: mvn clean package
3. Run tests: Unit tests (mvn test), Integration tests
4. SAST scan: SonarQube for code quality
5. Build Docker image: docker build -t app:latest .
6. Push to registry: docker push registry.io/app:latest
```

### Deploy Phase
```
1. Pull image: docker pull registry.io/app:latest
2. Update Kubernetes manifest: image: app:latest
3. Apply: kubectl apply -f deployment.yaml
4. Rolling update: Gradual replacement of pods (no downtime)
5. Health checks: readiness probe, liveness probe
```

### Monitor Phase
```
1. Metrics: Prometheus (CPU, memory, requests)
2. Dashboards: Grafana visualization
3. Logs: ELK Stack (Elasticsearch, Logstash, Kibana)
4. Alerts: PagerDuty, AlertManager (High CPU, high error rate)
5. Tracing: Jaeger for distributed tracing
```

### Example CI/CD Pipeline
```yaml
stages:
  - Build: mvn clean package
  - Test: mvn test (80%+ coverage)
  - Scan: mvn sonar:sonar
  - Docker: docker build -t app:$BUILD_NUMBER
  - Deploy: kubectl apply -f k8s/
  - Monitor: Prometheus scrapes metrics every 15s
```

---

## 7. Tools you use in your project

### Build & Deployment
- **Maven/Gradle**: Build tool
- **Jenkins/GitHub Actions**: CI/CD pipeline
- **Docker**: Containerization
- **Kubernetes**: Orchestration
- **Helm**: K8s package manager

### Monitoring & Logging
- **Prometheus**: Metrics collection
- **Grafana**: Visualization dashboards
- **ELK Stack**: Log aggregation
- **Jaeger/Zipkin**: Distributed tracing
- **PagerDuty**: Alert management

### Code Quality & Security
- **SonarQube**: SAST (static analysis)
- **OWASP DependencyCheck**: Vulnerability scanning
- **GitGuardian**: Secret scanning

### Databases & Caching
- **PostgreSQL**: Primary database
- **Redis**: Caching layer
- **Kafka**: Message queue

### Frameworks & Libraries
- **Spring Boot**: Application framework
- **Spring Security**: Authentication/Authorization
- **JUnit/Mockito**: Testing
- **Lombok**: Code generation

---

## 8. Kubernetes Core Objects (Pods, ConfigMaps, Helm)

### 1. Pods
- **Definition:** The smallest deployable unit in Kubernetes. 
- **Content:** Represents a single instance of a running process. A Pod contains one or more containers (usually one) that share storage, network IP, and configuration settings.
- **Does Kubernetes have containers in it?** Yes, containers (like Docker or containerd) run inside Pods.

### 2. ConfigMaps
- **Definition:** An API object used to store non-confidential configuration data in key-value pairs.
- **Purpose:** Decouples environment-specific configuration from container images, allowing you to run the same image in Dev, QA, and Prod.

### 3. Helm Charts
- **Definition:** A package manager for Kubernetes.
- **Purpose:** Packages all Kubernetes resource YAML manifests (Deployments, Services, ConfigMaps, Ingress) into a single, versioned package called a "Chart", allowing easy deployment, upgrade, and rollback with templated values.

---

## 9. Cloud Platforms & Infrastructure: OpenShift, S2I, AWS, NGINX

### 1. OpenShift vs Kubernetes
- **Kubernetes:** Open-source orchestration engine.
- **OpenShift:** Red Hat's enterprise Kubernetes platform. It includes built-in security, logging, monitoring, CI/CD pipelines, and developer-friendly dashboards out of the box.

### 2. S2I (Source-to-Image)
- **Concept:** An OpenShift feature that automates container image creation directly from raw source code.
- **How it works:** You point OpenShift to a Git repo. S2I downloads the code, automatically detects the language (e.g., Java Maven), compiles it, inserts the compiled binary into a builder container image, and outputs a ready-to-run container image—all without you writing a `Dockerfile`.

### 3. AWS Auto Scaling
- **Concept:** Dynamically adjusts computing capacity based on real-time traffic demand.
- **Scaling Groups:** Scales EC2 instances or ECS/EKS tasks up or down automatically using metrics like CPU utilization or request count from AWS CloudWatch.

### 4. NGINX Purpose & Load Balancing
NGINX is a lightweight, high-performance web server that serves three primary roles:
1. **Reverse Proxy:** Intercepts client requests and routes them to internal application servers.
2. **Load Balancer:** Distributes incoming HTTP/TCP traffic across multiple backend servers using algorithms like Round Robin or Least Connections.
3. **SSL Termination:** Decrypts incoming HTTPS requests at the NGINX level so backend servers only process plaintext HTTP, reducing backend CPU load.

---

## 10. Standard CI/CD Pipeline Steps

A classic DevOps pipeline runs behind every code commit:

```
[ Git Commit ] 
      │
      ▼
[ 1. Trigger ] ──────── Jenkins / GitHub Actions webhook detects change
      │
      ▼
[ 2. Build ] ────────── Compile code and build package (e.g., mvn clean package)
      │
      ▼
[ 3. Unit Test ] ────── Run unit tests (JUnit, Mockito) and verify code coverage
      │
      ▼
[ 4. SAST Scan ] ────── Scan code quality & security bugs (SonarQube / Veracode)
      │
      ▼
[ 5. Package ] ──────── Build Docker image (docker build) and scan dependencies
      │
      ▼
[ 6. Publish ] ──────── Push image to Registry (e.g., Docker Hub, AWS ECR)
      │
      ▼
[ 7. Deploy ] ───────── Update Kubernetes manifests & execute rolling deployment
      │
      ▼
[ 8. Verify ] ───────── Perform health checks (Liveness/Readiness probes)
```


