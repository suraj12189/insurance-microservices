package com.insurance.analytics.service;

import com.insurance.analytics.entity.PolicyAnalytics;
import com.insurance.analytics.repository.PolicyAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final PolicyAnalyticsRepository repository;

    public List<PolicyAnalytics> getAllPolicies() {
        return repository.findAll();
    }

    public long totalPolicies() {
        return repository.count();
    }
}