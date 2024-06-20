package com.example.order_service.web.controller;


import com.example.order_service.model.OrderEvent;
import com.example.order_service.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaOrderTopic}")
    private String kafkaOrderTopic;

//    @Value("${app.kafka.kafkaEventTopic}")
//    private String kafkaEventTopic;

//    private final KafkaTemplate<String, Order> kafkaOrderTemplate;

    private final KafkaTemplate<String, OrderEvent> kafkaOrderEventTemplate;

//    private final KafkaMessageService kafkaMessageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOrder(@RequestBody Order order) {

        OrderEvent event = new OrderEvent();
        event.setProduct(order.getProduct());
        event.setQuantity(order.getQuantity());

        kafkaOrderEventTemplate.send(kafkaOrderTopic, event);

        return ResponseEntity.ok("Order sent to kafka");
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<KafkaMessage> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(kafkaMessageService.getById(id).orElseThrow());
//    }



}
