package com.insurance.policy.controller;

import com.insurance.policy.dto.CreatePolicyRequest;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.enums.PolicyStatus;
import com.insurance.policy.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService service;

    // UC-3 Create Policy
    @PostMapping
    public Policy createPolicy(@RequestBody CreatePolicyRequest request) {
        return service.createPolicy(request);
    }

    // UC-4 Get Policy
    @GetMapping("/{policyNumber}")
    public Policy getPolicy(@PathVariable String policyNumber) {
        return service.getPolicy(policyNumber);
    }

    // UC-4 Customer Policies
    @GetMapping("/customer/{customerId}")
    public List<Policy> getCustomerPolicies(@PathVariable String customerId) {
        return service.getCustomerPolicies(customerId);
    }

    // UC-8 Update Status
    @PutMapping("/{policyNumber}/status")
    public Policy updateStatus(
            @PathVariable String policyNumber,
            @RequestParam PolicyStatus status) {

        return service.updateStatus(policyNumber, status);
    }
}