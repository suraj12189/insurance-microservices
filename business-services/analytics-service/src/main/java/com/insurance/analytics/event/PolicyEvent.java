package com.insurance.analytics.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyEvent {

    private Long policyId;

    private String customerId;

    private String policyType;

    private String status;

    private Double premiumAmount;

    private LocalDateTime createdAt;

    // Optional (recommended)
    private String eventType;
    // POLICY_CREATED
    // POLICY_UPDATED
    // POLICY_CANCELLED
}