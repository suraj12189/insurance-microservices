package com.insurance.analytics.event;

import com.insurance.analytics.enums.PolicyStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyEvent {

    private String eventType;
    private String policyNumber;
    private String customerId;
    private PolicyStatus status;
}