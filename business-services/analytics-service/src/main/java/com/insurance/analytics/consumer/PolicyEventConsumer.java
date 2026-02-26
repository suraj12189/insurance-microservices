package com.insurance.analytics.consumer;

import com.insurance.analytics.entity.PolicyAnalytics;
import com.insurance.analytics.repository.PolicyAnalyticsRepository;
import com.insurance.policy.event.PolicyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PolicyEventConsumer {

    private final PolicyAnalyticsRepository repository;

    @KafkaListener(
            topics = "policy-events",
            groupId = "analytics-group"
    )
    public void consume(PolicyEvent event) {

        PolicyAnalytics analytics =
                PolicyAnalytics.builder()
                        .policyNumber(event.getPolicyNumber())
                        .customerId(event.getCustomerId())
                        .eventType(event.getEventType())
                        .status(event.getStatus())
                        .build();

        repository.save(analytics);
    }
}