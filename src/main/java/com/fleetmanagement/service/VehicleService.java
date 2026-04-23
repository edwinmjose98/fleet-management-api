package com.fleetmanagement.service;

import com.fleetmanagement.model.*;
import com.fleetmanagement.repository.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final SubscriptionPackageRepository packageRepository;
    private final VehicleSubscriptionRepository subscriptionRepository;

    public VehicleService(VehicleRepository vehicleRepository,
                          SubscriptionPackageRepository packageRepository,
                          VehicleSubscriptionRepository subscriptionRepository) {
        this.vehicleRepository = vehicleRepository;
        this.packageRepository = packageRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Vehicle onboardVehicle(Vehicle vehicle) {
        vehicle.setStatus(VehicleStatus.ONLINE);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleByDeviceId(String deviceId) {
        return vehicleRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + deviceId));
    }

    public VehicleSubscription assignSubscription(String deviceId, String packageName) {
        Vehicle vehicle = getVehicleByDeviceId(deviceId);
        SubscriptionPackage pkg = packageRepository.findByName(packageName)
                .orElseThrow(() -> new RuntimeException("Package not found: " + packageName));

        VehicleSubscription subscription = subscriptionRepository
                .findByVehicle(vehicle)
                .orElse(new VehicleSubscription());

        subscription.setVehicle(vehicle);
        subscription.setSubscriptionPackage(pkg);
        return subscriptionRepository.save(subscription);
    }

    public Set<Feature> getVehicleFeatures(String deviceId) {
        Vehicle vehicle = getVehicleByDeviceId(deviceId);
        VehicleSubscription subscription = subscriptionRepository.findByVehicle(vehicle)
                .orElseThrow(() -> new RuntimeException("No subscription found for vehicle: " + deviceId));
        return subscription.getSubscriptionPackage().getFeatures();
    }

    public Vehicle updateFirmware(String deviceId, String newVersion) {
        Vehicle vehicle = getVehicleByDeviceId(deviceId);
        vehicle.setStatus(VehicleStatus.UPDATING);
        vehicle.setFirmwareVersion(newVersion);
        return vehicleRepository.save(vehicle);
    }
}