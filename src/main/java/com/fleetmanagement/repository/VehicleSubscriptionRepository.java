package com.fleetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.model.VehicleSubscription;

import java.util.Optional;

@Repository
public interface VehicleSubscriptionRepository extends JpaRepository<VehicleSubscription, Long> {
    Optional<VehicleSubscription> findByVehicle(Vehicle vehicle);
}