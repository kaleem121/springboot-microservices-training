# Day 8 – API Gateway (Basics)

- Created `api-gateway` (Boot 3.3.3, Java 17).
- Dependencies: `spring-cloud-starter-gateway (4.1.3)`, actuator.
- Configured route:
  - `/api/products/**` → `http://localhost:8081` (StripPrefix=1)
- Verified via Postman/Swagger.
- Gateway now sits at **8080**, forwarding requests to Product Service (8081).