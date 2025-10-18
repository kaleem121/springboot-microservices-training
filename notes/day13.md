# ⚙️ Day 13 – Feign Error Handling (ErrorDecoder)

## 🎯 **Goal**

Handle errors gracefully when **Feign calls fail** (e.g., product-service down or product not found).

## 🧩 Why Handle Errors?

By default, **Feign throws generic exceptions**.

We need meaningful messages for better debugging and client response handling.

## ⚙️ Steps Implemented

### 1. Create Custom Exceptions
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

public class ProductServiceDownException extends RuntimeException {
    public ProductServiceDownException(String message) {
        super(message);
    }
    
public class FeignGenericException extends RuntimeException {
    public ProductServiceDownException(String message) {
        super(message);
    }
}

### 2. Implement Custom ErrorDecoder
@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                return new ProductNotFoundException("Product not found!");
            case 500:
                return new ProductServiceDownException("Product service is down!");
            default:
                return new Exception("General Feign error occurred");
        }
    }
}

### 3. Register Bean in FeignConfig
@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}

### 4. Global Exception Handler
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ProductServiceDownException.class)
    public ResponseEntity<String> handleProductServiceDown(ProductServiceDownException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }

     @ExceptionHandler(FeignGenericException.class)
	    public ResponseEntity<String> handleGeneralException(FeignGenericException e){
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
}

## 🧠 Key Learnings

###🔹 1. How Feign Works Internally

Feign creates a proxy object for the interface.

When a method is invoked → Feign performs an HTTP call under the hood.

If it fails → Feign invokes your ErrorDecoder.decode().

The returned exception is wrapped inside UndeclaredThrowableException.

###🔹 2. Exception Handling Approaches

**✅ Approach 1: Global Exception Handler (@ControllerAdvice)**
Handles Feign exceptions globally by checking for wrapped exceptions.

**✅ Approach 2: Directly in Controller**

try {
    productClient.getProduct(26L);
} catch (ProductServiceDownException e) {
    // handle service down
}


**✅ Approach 3: Handle FeignException**
You can also directly catch:

import feign.FeignException;

@ExceptionHandler(FeignException.class)
public ResponseEntity<String> handleFeignException(FeignException e) {
    return ResponseEntity.status(e.status()).body(e.getMessage());
}

**🧾 Example Outputs**

Scenario Status Message

Product not found	    404	Product not found!

Product service down	503	Product service is down!

Unexpected exception	500	Unexpected error (Generic error)

## ✅ Output

If product-service is down →
503 Product service is down!

If product not found →
404 Product not found!