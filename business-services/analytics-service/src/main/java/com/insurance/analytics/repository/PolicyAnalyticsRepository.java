package com.insurance.analytics.repository;

import com.insurance.analytics.entity.PolicyAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyAnalyticsRepository
        extends JpaRepository<PolicyAnalytics, Long> {
}