# ⚙️ Day 14 – Kafka Integration (Event-Driven Communication)

## 🎯 **Goal**

Integrate Apache Kafka for asynchronous communication between microservices.
Here, Order Service acts as a Producer, and Product Service acts as a Consumer.

## 🧩 Concept Overview

 Producer (Order Service) → Publishes events (e.g., order placed).

 Consumer (Product Service) → Listens to these events and processes them (e.g., update stock).

 Kafka acts as a distributed message broker enabling loose coupling and asynchronous communication.

## ⚙️ Steps Implemented

### 1️. Add Kafka Dependency (Maven)

**In both Order Service and Product Service**:

<!-- Spring Kafka -->
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>

### 2️. Start Kafka & Zookeeper

Commands:

# Start Zookeeper
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

# Start Kafka broker
.\bin\windows\kafka-server-start.bat .\config\server.properties


Ensure both are running properly before starting services.

### 3️. Configure Kafka in application.yml
Order Service (Producer)
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      

Product Service (Consumer)
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: product-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*"

### 4️. Create Event Class in Order Service

📁 com.techtalksera.orderservice.event.OrderEvent

package com.techtalksera.orderservice.event;

public class OrderEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;

    public OrderEvent() {}

    public OrderEvent(Long orderId, Long productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

### 5️. Create Kafka Producer (Order Service)

📁 com.techtalksera.orderservice.kafka.OrderProducer

package com.techtalksera.orderservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.techtalksera.orderservice.event.OrderEvent;

@Service
public class OrderKafkaProducer {

    private static final String TOPIC = "order-event";
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderKafkaProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("✅ Order event sent to Kafka: " + event);
    }
}

### 6️. Publish Event in Controller (Order Service)

📁 com.techtalksera.orderservice.controller.OrderController

@PostMapping("/place")
public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {
    // Save order to DB first
    Order order = orderService.createOrder(request);

    // Send event to Kafka
    OrderEvent event = new OrderEvent(order.getId(), order.getProductId(), order.getQuantity());
    orderKafkaProducer.sendOrderEvent(event);

    return ResponseEntity.ok("Order placed successfully!");
}

### 7️. Create Kafka Consumer (Product Service)

📁 com.techtalksera.productservice.kafka.OrderConsumer

package com.techtalksera.productservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.techtalksera.productservice.event.OrderEvent;

@Service
public class OrderKafkaConsumer {

    @KafkaListener(topics = "order-event", groupId = "product-group")
    public void consume(OrderEvent event) {
        System.out.println("📩 Received Order Event: " + event);

        // Example: Reduce stock logic
        System.out.println("Updating product stock for productId: " + event.getProductId());
    }
}

### 8️. Event Class in Product Service

📁 com.techtalksera.productservice.event.OrderEvent

package com.techtalksera.productservice.event;

public class OrderEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;

    public OrderEvent() {}

    public OrderEvent(Long orderId, Long productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters & Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}

## ✅ Output Example

**Order Service Console**:

✅ Order event sent to Kafka: OrderEvent{orderId=1, productId=101, quantity=2}


**Product Service Console**:

📩 Received Order Event: OrderEvent{orderId=1, productId=101, quantity=2}
Updating product stock for productId: 101

## 🧠 Key Learnings

Kafka enables loose coupling between services.

Producers and Consumers communicate asynchronously.

***KafkaTemplate is used for publishing events.***

***@KafkaListener is used for consuming messages.***

YAML configuration simplifies producer/consumer setup.

Helps prepare for event-driven microservices (useful for SAGA pattern, Resilience4j, etc.).