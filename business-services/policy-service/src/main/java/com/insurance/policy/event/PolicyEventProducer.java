package com.insurance.policy.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PolicyEventProducer {

    private final KafkaTemplate<String, PolicyEvent> kafkaTemplate;

    private static final String TOPIC = "policy-events";

    public void publishEvent(PolicyEvent event) {
        kafkaTemplate.send(TOPIC, event.getPolicyNumber(), event);
    }
}