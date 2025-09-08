# Day 4 – DTOs, Validation, Global Errors

**Added DTOs**

ProductCreateRequest → input for POST (name, price, stock + validation)

ProductUpdateRequest → input for PUT (full replace)

ProductResponse → output to client

and ProductMapper for mapping these DTOs to entity(Product)

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