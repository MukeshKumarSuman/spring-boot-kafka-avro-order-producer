package com.nps.producer;

import avro.schema.generated.Order;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProducer {

    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);
    KafkaTemplate<String, Order> orderKafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Order> orderKafkaTemplate) {
        this.orderKafkaTemplate = orderKafkaTemplate;
    }

    public void sendMessage(Order order) {
        logger.info("Publishing order={}", order);
        orderKafkaTemplate.send("orders-test", order.getId().toString(), order);
        logger.info("Publishing done");
    }
}
