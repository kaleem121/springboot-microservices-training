# 🚀 Day 12 – Service Communication using Feign Client

## 🎯 **Goal**
Enable order-service to call product-service internally using **Feign Client**, without manually using RestTemplate or HTTP URLs.

## 🧩 Why Feign Client?

Simplifies inter-service REST calls.

Automatically integrates with Eureka for service discovery.

Makes communication type-safe (interface-based).

## ⚙️ Steps Implemented

### 1. Add Feign Dependency (in order-service)
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>

### 2. Enable Feign Client
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

### 3. Create Feign Client Interface
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProduct(@PathVariable Long id);
}

### 4. Use It in Controller
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/{orderId}")
    public String placeOrder(@PathVariable Long orderId) {
        ProductResponse product = productClient.getProduct(1L);
        return "Order " + orderId + " placed for product " + product.getName();
    }
}

### 5. Test

Start all services: discovery-server, api-gateway, product-service, order-service

Hit → http://localhost:8080/api/orders/1

## ✅ Result:
order-service calls product-service via **Feign Client** through Eureka + Gateway.