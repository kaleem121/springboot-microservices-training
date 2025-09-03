**DAY1**

# Hello Service

A minimal Spring Boot application.

## How to run
 Run the app from STS: Right click project → Run As → Spring Boot App  
-Or from terminal:  
  ```bash
  ./mvnw spring-boot:run

  
Endpoint:

GET /hello

Port:

Runs on port 8081

----------------------------------------------------------------------------

**DAY2**

## Product API (in-memory)
- POST /products
- GET /products
- GET /products/{id}
- PUT /products/{id}
- DELETE /products/{id}

### Example Create body
{

  "name": "Notebook",
  
  "price": 149.50,
  
  "stock": 20
  
}
----------------------------------------------------------------------------
###Add a “Day 3” section with:

Docker command used

DB URL/credentials (dev)

Note on ddl-auto: update

---------------------------------------------------------------------------------------


##📅 Day 4 – DTOs, Validation & Global Errors

**Added DTOs**

ProductCreateRequest → input for POST (name, price, stock + validation)

ProductUpdateRequest → input for PUT (full replace)

ProductResponse → output to client

**Validation rules (on DTOs)**

name: @NotBlank

price: @NotNull, @DecimalMin("0.0"), @Digits(10,2)

stock: @NotNull, @Min(0)

**Global error handler (@RestControllerAdvice)**

All errors return unified JSON: {timestamp, status, error, message, path, details}

Handles: NotFound (404), Validation errors (400), Bad JSON, Wrong type

**Swagger UI (springdoc)**

Dependency: springdoc-openapi-starter-webmvc-ui

URL: http://localhost:8081/swagger-ui/index.html

