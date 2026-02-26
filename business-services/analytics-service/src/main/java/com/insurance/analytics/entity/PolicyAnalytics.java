package com.insurance.analytics.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyAnalytics {

    @Id
    private Long policyId;

    private String customerId;

    private String policyType;

    private String status;

    private Double premiumAmount;

    private LocalDateTime createdAt;
}