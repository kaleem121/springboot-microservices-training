package com.techTalkEra.orderservice;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.techTalkEra.common.dto.OrderEvent;

@Service
public class OrderKafkaProducer {
	
	private final KafkaTemplate<String,OrderEvent> kafkaTemplate;
	
	private final String TOPIC = "order-event";
	
	 private final Logger log = LoggerFactory.getLogger(OrderKafkaProducer.class);
	
	public OrderKafkaProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate){
		this.kafkaTemplate=kafkaTemplate;
	}
	
	public void sendOrderEvent(OrderEvent orderEvent) {
		
		 CompletableFuture<SendResult<String,OrderEvent>> future = kafkaTemplate.send(TOPIC,String.valueOf(orderEvent.getOrderId()),orderEvent);


		 future.whenComplete((result, ex) -> {
		        if (ex == null) {
		            log.info("OrderEvent sent to topic={} partition={} offset={}",
		                    result.getRecordMetadata().topic(),
		                    result.getRecordMetadata().partition(),
		                    result.getRecordMetadata().offset());
		        } else {
		            log.error("Failed to send OrderEvent for orderId={} : {}", orderEvent.getOrderId(), ex.getMessage(), ex);
		        }
		    });
	}
	
	

}
