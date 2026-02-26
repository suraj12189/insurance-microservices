package com.insurance.analytics.consumer;

import com.insurance.analytics.event.PolicyEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PolicyEventConsumer {

    @KafkaListener(topics = "policy-events", groupId = "analytics-group")
    public void consume(PolicyEvent event) {
        System.out.println("Analytics received event: " + event.getEventType() + " Policy: " + event.getPolicyNumber());
    }
}
