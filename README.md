# Cron Validator – Java + Spring Boot 
### (Quartz Cron Parser & Text Converter)

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.7-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9.5-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-24.0.5-2496ED?style=flat-square&logo=docker&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.12.0-FF6600?style=flat-square&logo=rabbitmq&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=flat-square&logo=swagger&logoColor=white)

A **stateless**, **high-performance**, and extensible worker service for validating **Quartz cron expressions** and converting them into human-readable text.
Designed with clean architecture, multi-protocol input support (HTTP + RabbitMQ), and future-ready extensibility for UNIX cron.

This service currently operates as an independent microservice but is architected as a worker component for future **Job Orchestrator** systems.

---

## 🚀 Features

### ✅ 1. Cron Validation (Quartz)
- Validates Quartz cron expressions  
- Returns a boolean (`true` / `false`)  
- Lightweight, fast, and stateless  

---

### ✅ 2. Cron → Human-Readable Text

**Example:**  
`0 0/5 * * * ?` → **"Every 5 minutes"**

---

### ✅ 3. Future-Ready UNIX Cron Support
- Built using a pluggable `CronService` interface  
- Additional implementations (e.g., `CronUnixService`) can be added without modifying controller code  

---

### ✅ 4. HTTP + RabbitMQ Support

Supports two input modes:

| Input Type            | Description                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| HTTP REST API         | Simple for clients, includes Swagger/OpenAPI docs                           |
| RabbitMQ Worker Mode  | Fire-and-forget worker consuming jobs and publishing results                |

---

### ✅ 5. JUnit + Mockito + MockMVC Test Suite
- Service layer unit tests  
- Static utility tests (using `MockedStatic`)  
- Controller tests using MockMVC + Mockito  

---

### ✅ 6. Docker Support (Multi-Stage Build)

To build and run:

```bash
docker build -t cron-validator .
docker run -p 8080:8080 cron-validator
```

---


## 🛠️ Tech Stack
**Languages & Frameworks**

- Java 21
- Spring Boot 3.5.7
- Spring Web
- Spring AMQP (RabbitMQ)
- Quartz Scheduler
- Springdoc OpenAPI

**Build & Dev Tools**

- Maven
- JUnit 5
- Mockito
- MockMVC
- Docker (multi-stage build)

---

## 📁 Project Structure

```bash
cron-validator/
├── Dockerfile
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/syedhisham41/cron_validator
│   │   │   ├── Constants/        # Enums & dictionaries
│   │   │   ├── Controller/       # REST Controllers
│   │   │   ├── DTO/              # Request/Response models
│   │   │   ├── Events/           # MQ config, publishers, consumers
│   │   │   ├── Exceptions/       # Custom exception hierarchy
│   │   │   ├── Service/          # CronQuartzService + CronService interface
│   │   │   ├── Utils/            # CronUtils helper class
│   │   │   └── CronValidatorApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test/java/com/syedhisham41/cron_validator
│       ├── Controller/           # MockMVC controller tests
│       ├── Service/              # CronQuartzService unit tests
│       ├── Utils/                # Utility tests
│       └── Events/               # RabbitMQ consumer tests
.
```
---

## ▶ Running the project

### 🐳 Docker Support
This project includes a multi-stage Dockerfile for lightweight deployment.

**Build image**
```bash
docker build -t cron-validator .
```

**Run container**
```bash
docker run -p 8080:8080 cron-validator
```

*(Note: Current Dockerfile does not bundle RabbitMQ. RMQ is expected to run externally — e.g., by Job Orchestrator or docker-compose.)*

### ▶ Run Locally

#### 1️⃣ Clone the repository
```bash
git clone https://github.com/syedhisham41/cron-validator.git
cd cron-validator
```

#### 2️⃣ Run with Maven
```bash
mvn clean install
mvn spring-boot:run
```

The service will start on:
```bash
http://localhost:8080/
```

#### 3️⃣ Run Tests
```bash
mvn test
```

#### 4️⃣ Build JAR
```bash
mvn clean package
java -jar target/cron-validator.jar
```

---



## 📡 REST API Endpoints
**Base URL**

 - `http://localhost:8080/api`

---

**1. Validate Cron Expression**

**POST /validate**

##### Request
```json
{
  "cronExpr": "0 0/5 * * * ?",
  "cronType": "QUARTZ"
}
```

##### Response
```json
true
```

**2. Convert Cron to Human Text**

**POST /crontext**

##### Request
```json
{
  "cronExpr": "0 0/5 * * * ?",
  "cronType": "QUARTZ"
}
```

##### Response
```json
"Every day at 12:00 PM"
```
**3. Cron Text Test (Debug Only)**

**POST /crontexttest**

##### Request
```json
{
  "cronExpr": "0 0/5 * * * ?",
  "cronType": "QUARTZ"
}
```

##### Response
```json
"(Quartz summary output, used for debugging)"
```

## 🧩 Design & Extensibility

**Interface-based service design**

```java
public interface CronService {
    boolean validate(String expr) throws ParseException;
    String cronToText(String expr);
    String parseCronToText(String expr);
}
```

**Current implementation**
 - `CronQuartzService` → `@Service("QUARTZ")`

**Future implementation**
 - `CronUnixService` → `@Service("UNIX")`

**Automatic routing inside Controller**

`parserServices.get(cronRequest.getCronType().toString())`

The controller does not need modifications when adding new cron types —
just register a new service implementing `CronService` with a unique service name.

---

## 📬 RabbitMQ Integration

Uses **3 queues** mapped via routing keys:
```bash
worker.cron.validator.requests.queue
worker.cron.validator.results.queue
worker.cron.validator.status.queue
```

**Queue Responsibilities**:
- **Requests Queue** → Receives cron validation requests  
- **Results Queue** → Publishes final validation results  
- **Status Queue** → Publishes intermediate processing states (dashboards, orchestrators) 


**Exchanges & Routing Keys**:
- Topic exchange
- Routing keys mapped per queue

**Message Flow**:

- Client (Job Orchestrator or any client) → requests.queue
- Worker picks the request
- Worker pushes status to status.queue
- Worker pushes final results to results.queue

---

## 🌍 Real-world Use Cases

- Job orchestration engines
- Workflow schedulers
- Task automation platforms
- Monitoring dashboards for cron executions
- Enterprise task validation pipelines
- Background worker services
- Cron conversion for UI dashboards

---

## 🧭 Future Enhancements

- UNIX cron support (CronUnixService)
- Unified dashboard UI (using Thymeleaf or React)
- Docker Compose with embedded RabbitMQ