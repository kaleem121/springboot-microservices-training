# 🚀 Spring Boot Microservices Training

My 8-week microservices learning project (Java 21 + Spring Boot).

## ✅ Week 1 (Day 1–7) – Foundations
- Hello service, REST CRUD (Product)
- JPA + MySQL (Docker)
- DTOs + Validation + Global Exception Handling
- Profiles (dev/prod) + Logging
- Actuator (health, info, metrics)
- Mini tests (MockMvc, H2)
- Tag: `week1-done`

## ▶️ Run (Dev)
```bash
docker start product-mysql
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

Swagger: http://localhost:8081/swagger-ui/index.html

Actuator: http://localhost:8081/actuator