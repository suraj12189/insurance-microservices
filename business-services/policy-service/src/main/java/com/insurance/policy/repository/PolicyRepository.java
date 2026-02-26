package com.insurance.policy.repository;

import com.insurance.policy.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findByCustomerId(String customerId);

    Policy findByPolicyNumber(String policyNumber);
}