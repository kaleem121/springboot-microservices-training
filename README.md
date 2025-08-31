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


