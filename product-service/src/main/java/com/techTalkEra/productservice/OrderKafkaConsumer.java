package com.techTalkEra.productservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.techTalkEra.common.dto.OrderEvent;

@Service
public class OrderKafkaConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(OrderKafkaConsumer.class);
	
	@KafkaListener(topics ="order-event",groupId = "product-service-group")
	public void consume(OrderEvent event) {
		log.info("Received Order Event in product service ->{}",event);
		
		//TODO update product quantity ,send notification , etc.
		
		log.info("updating product inventary for productId = {} by quantity ={}",event.getProductId(),event.getQuantity());
	}

}
