package com.insurance.policy.dto;

import java.time.LocalDate;

public record CreatePolicyRequest(
        String customerId,
        String policyType,
        Double premiumAmount,
        LocalDate startDate,
        LocalDate endDate
) {}