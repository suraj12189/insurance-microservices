package com.insurance.policy.event;

import com.insurance.policy.enums.PolicyStatus;
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