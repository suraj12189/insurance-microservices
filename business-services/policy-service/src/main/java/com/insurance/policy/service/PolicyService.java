package com.insurance.policy.service;

import com.insurance.policy.dto.CreatePolicyRequest;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.enums.PolicyStatus;
import com.insurance.policy.event.PolicyEvent;
import com.insurance.policy.event.PolicyEventProducer;
import com.insurance.policy.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository repository;
    private final PolicyEventProducer policyEventProducer;

    // ================= UC-3 CREATE POLICY =================
    public Policy createPolicy(CreatePolicyRequest request) {

        Policy policy = Policy.builder()
                .policyNumber(UUID.randomUUID().toString())
                .customerId(request.customerId())
                .policyType(request.policyType())
                .premiumAmount(request.premiumAmount())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .status(PolicyStatus.CREATED)
                .build();

        Policy saved = repository.save(policy);

        //Publish Event
        policyEventProducer.publishEvent(PolicyEvent.builder()
                .eventType("POLICY_CREATED")
                .policyNumber(saved.getPolicyNumber())
                .customerId(saved.getCustomerId())
                .status(saved.getStatus())
                .build());
        return saved;
    }

    // ================= UC-4 VIEW POLICY =================
    public Policy getPolicy(String policyNumber) {
        return repository.findByPolicyNumber(policyNumber);
    }

    public List<Policy> getCustomerPolicies(String customerId) {
        return repository.findByCustomerId(customerId);
    }

    // ================= UC-8 UPDATE STATUS =================
    public Policy updateStatus(String policyNumber, PolicyStatus status) {

        Policy policy = repository.findByPolicyNumber(policyNumber);

        policy.setStatus(status);

        Policy updated = repository.save(policy);
        policyEventProducer.publishEvent(PolicyEvent.builder()
                .eventType("POLICY_STATUS_CHANGED")
                .policyNumber(updated.getPolicyNumber())
                .customerId(updated.getCustomerId())
                .status(updated.getStatus())
                .build());
        return updated;
    }
}