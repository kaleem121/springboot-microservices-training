# 🚀 Spring Boot Microservices Training

My 8-week microservices learning project (Java 21 + Spring Boot).  
This repo contains step-by-step progress from **Week 1 (foundations)** to advanced microservices concepts.

---

## ✅ Week 1 (Day 1–7) – Foundations
- Product service, REST CRUD (Product)
- JPA + MySQL (Docker)
- DTOs + Validation + Global Exception Handling
- Profiles (dev/prod) + Logging
- Actuator (health, info, metrics)
- Mini tests (MockMvc, H2)
- Tag: `week1-done`

## ✅ Week 2 (Day 8–14) – Service-to-Service Communication
- Day 8: API Gateway (Spring Cloud Gateway)
- Day 9: Order Service (created order service)
- Day 10: Service Discovery (Eureka) + API Gateway dynamic routing (`lb://`)
- Day 11: Centralized Config (Spring Cloud Config Server + Local Config Repo)
  - Config Server created to serve configurations centrally
  - Product & Order services now load configs from Config Server
  - Tested fetching configurations via http://localhost:8888/{service}/{profile}
- Day 12: OpenFeign Integration (Short)

  - Integrated OpenFeign in order-service for REST calls to product-service.
  - Verified communication through the gateway and Eureka Service Discovery.

- Day 13: Feign Exception Handling (Short)

  - Added Custom ErrorDecoder and GlobalExceptionHandler to handle Feign errors gracefully (404, 500, and generic).
  - Tested both direct and global handling approaches.
  - (See notes/day13.md for detailed explanation.)
  
- Day 14: Kafka Integration (Producer + Consumer)

  - Integrated Apache Kafka for asynchronous event-based communication.

  - Order Service → Producer: Publishes OrderEvent to topic order-events.

  - Product Service → Consumer: Listens to same topic and processes incoming events.

  - Configured spring.kafka properties in application.yml (no config class).

  - Verified event flow end-to-end via Kafka console and Spring Boot logs.
   
   	Command Used : 

  # Start Zookeeper
 .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
	

  # Start Kafka Broker
 .\bin\windows\kafka-server-start.bat .\config\server.properties

## ▶️ Run (Dev)

#### 1. Start MySQL (Docker)

docker start product-mysql

#### 2. Run Spring Boot
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

#### 3. Access APIs

Swagger UI → http://localhost:8081/swagger-ui/index.html

Actuator → http://localhost:8081/actuator

Eureka Server Dashboard → http://localhost:8761 

Config server -> http://localhost:8888/{service}/{profile}


## 📒 Learning Journal (Day-wise)

- [Day 1 – Spring Boot setup](notes/day1.md)
- [Day 2 – CRUD with JPA](notes/day2.md)
- [Day 3 – DB + Docker](notes/day3.md)
- [Day 4 – DTOs, Validation, Global Errors](notes/day4.md)
- [Day 5 – Profiles & Logging](notes/day5.md)
- [Day 6 – Actuator](notes/day6.md)
- [Day 7 – Review + Mini Tests](notes/day7.md)
- [Day 8 – API Gateway](./notes/day8.md) ✅
- [Day 9 – OrderService](./notes/day9.md) ✅
- [Day 10 – Eureka+GatewayRouting](./notes/day10.md) ✅
- [Day 11 – Centralized Config Server](./notes/day11.md) ✅
- [Day 12 – OpenFeign Integration](./notes/day12.md) ✅
- [Day 13 – Feign Exception Handling](./notes/day13.md) ✅
- [Day 14 – Kafka Integration(Producer+Consumer)](./notes/day14.md) ✅



## 📂 Project Structure
springboot-microservices-training/

 ├── README.md          # professional summary
 
 ├── notes/             # detailed daily notes
 
 │   ├── day1.md
 │   ├── day2.md
 │   ├── ...
 │   └── day7.md
 
 ├── src/               # application source code
 
 ├── pom.xml           # Maven build file 
 
 └── logs/             # ignored by Git (defined in .gitignore)

## 🛠️ Tech Stack

Java 17

Spring Boot

Spring Data JPA

Spring Cloud (Gateway, Config, Eureka, OpenFeign)

MySQL (Docker)

Actuator & Micrometer

Swagger / OpenAPI

JUnit 5, MockMvc, H2 DB


## 🎯 Tags (Milestones)

week1-done → CRUD + Profiles + Logging + Actuator + Tests

week2-done → (API Gateway + Order Service + Eureka Service Discovery + Dynamic Routing + Centralized Config + Feign Integration + Kafka Integration)