package com.example.order_service.listener;

import com.example.order_service.model.OrderEvent;
import com.example.order_service.model.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventListener {

    @Value("${app.kafka.kafkaEventTopic}")
    private String kafkaEventTopic;

    private final KafkaTemplate<String, OrderStatusEvent> kafkaOrderStatusEventTemplate;

    @KafkaListener(topics = "${app.kafka.kafkaOrderTopic}",
            groupId = "${app.kafka.kafkaOrderGroupId}",
            containerFactory = "kafkaOrderEventConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload OrderEvent event,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received order: {}", event);
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);

        OrderStatusEvent orderStatusEvent = new OrderStatusEvent();
        orderStatusEvent.setDate(Instant.now());
        orderStatusEvent.setStatus("CREATED ");
        kafkaOrderStatusEventTemplate.send(kafkaEventTopic, orderStatusEvent);

    }
}
