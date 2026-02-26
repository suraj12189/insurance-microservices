package com.insurance.policy.dto;

import com.insurance.policy.enums.PolicyStatus;

public record PolicyResponse(
        String policyNumber,
        String customerId,
        String policyType,
        Double premiumAmount,
        PolicyStatus status
) {}