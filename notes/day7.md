# Day 7 – Review + Mini Tests

- Manual CRUD verified (201/200/204/400/404)
- Actuator: /health, /info, /metrics (http.server.requests)
- Tests:
  - Controller: MockMvc POST /products → 201 + Location + JSON fields
  - Repository: @DataJpaTest with H2 → save & read
