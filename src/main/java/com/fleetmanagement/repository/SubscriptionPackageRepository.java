package com.fleetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleetmanagement.model.SubscriptionPackage;

import java.util.Optional;

@Repository
public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackage, Long> {
    Optional<SubscriptionPackage> findByName(String name);
}