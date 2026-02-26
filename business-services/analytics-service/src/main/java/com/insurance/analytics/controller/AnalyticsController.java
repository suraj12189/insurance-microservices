package com.insurance.analytics.controller;

import com.insurance.analytics.entity.PolicyAnalytics;
import com.insurance.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService service;

    @GetMapping("/policies")
    public List<PolicyAnalytics> allPolicies() {
        return service.getAllPolicies();
    }

    @GetMapping("/policies/count")
    public long totalPolicies() {
        return service.totalPolicies();
    }
}