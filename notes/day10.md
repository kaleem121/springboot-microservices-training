# Day 10 – Eureka + API Gateway Dynamic Routing

- **New Component:** `discovery-server` (Eureka), runs on **8761**
- **Goal:** Use **Service Discovery** to register all services and enable **dynamic routing** in API Gateway with `lb://`

---

### 1️ Discovery Server Setup
- Added a new Spring Boot project: **discovery-server**
- Dependency: `spring-cloud-starter-netflix-eureka-server`
- `@EnableEurekaServer` added in main class
- `application.yml`:
```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
Start server → Dashboard available at http://localhost:8761

### 2 ️Register Services with Eureka
In product-service and order-service:

yaml
Copy code
spring:
  application:
    name: product-service   # (or order-service)

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
✅ After running, both services show up as UP in Eureka Dashboard.

### 3️ Configure API Gateway (Dynamic Routing)
Added Eureka client + config in api-gateway:

yaml
Copy code
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/products/**
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/orders/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
      
Run gateway on 8080.

ℹ️ Note: No extra setup is needed for lb:// in Spring Cloud Gateway. The dependency spring-cloud-starter-gateway already brings Spring Cloud LoadBalancer, which works with Eureka out of the box.

### 4️ Testing
http://localhost:8080/products/all → routes to product-service

http://localhost:8080/orders/all → routes to order-service

Both pass through API Gateway and resolve via Eureka.

### 🐞 Issue Faced
Initially, routing worked only when using fixed URIs like:

http://localhost:8081/products/**

http://localhost:8082/orders/**

But when using service names (lb://order-service, lb://product-service), the Gateway failed with:

503 SERVICE_UNAVAILABLE

Eureka was registering services with my system hostname → e.g., KALEEM.sys...

Since there was no DNS entry for this hostname, Gateway could not resolve it.

### 🔧 Fix
Explicitly set the service instances to prefer localhost + IP:

yaml
Copy code
eureka:
  instance:
    prefer-ip-address: true
    ip-address: 127.0.0.1
    hostname: localhost
✅ After applying this in each service (order-service, product-service, api-gateway), Eureka registered them with localhost instead of system hostname.
✅ Gateway successfully routed requests using lb:// URIs.

### Added Eureka client + config in api-gateway:spring:
application:
name: api-gateway
cloud:
gateway:
routes:
- id: product-service
uri: lb://product-service
predicates:
- Path=/products/**
- id: order-service
uri: lb://order-service
predicates:
- Path=/orders/**


eureka:
client:
service-url:
defaultZone: http://localhost:8761/eureka/
instance:
prefer-ip-address: true
ip-address: 127.0.0.1
hostname: localhost

### ✅ Summary
Added Eureka Discovery Server

Registered product-service & order-service

API Gateway now routes dynamically using lb:// instead of fixed URLs

Fixed hostname resolution issue by forcing Eureka to use localhost

Verified end-to-end routing successfully