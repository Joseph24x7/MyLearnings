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