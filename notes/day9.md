# Day 9 – Order Service

- New service: `order-service` (8082), Boot 3.3.3, Java 17
- CRUD endpoints: /orders (POST, GET, PUT, DELETE)
- Validation + GlobalExceptionHandler
- DB: H2 (in-memory) for quick development
- Gateway route: /api/orders/** → http://localhost:8082 (StripPrefix=1)
- Tested via Postman; E2E via Gateway works
