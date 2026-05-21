package com.fleetmanagement.controller;

import com.fleetmanagement.model.*;
import com.fleetmanagement.service.VehicleService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/onboard")
    public ResponseEntity<Vehicle> onboardVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.onboardVehicle(vehicle));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String deviceId) {
        return ResponseEntity.ok(vehicleService.getVehicleByDeviceId(deviceId));
    }

    @PostMapping("/{deviceId}/subscription")
    public ResponseEntity<VehicleSubscription> assignSubscription(
            @PathVariable String deviceId,
            @RequestParam String packageName) {
        return ResponseEntity.ok(vehicleService.assignSubscription(deviceId, packageName));
    }

    @GetMapping("/{deviceId}/features")
    public ResponseEntity<Set<Feature>> getFeatures(@PathVariable String deviceId) {
        return ResponseEntity.ok(vehicleService.getVehicleFeatures(deviceId));
    }

    @PutMapping("/{deviceId}/firmware")
    public ResponseEntity<Vehicle> updateFirmware(
            @PathVariable String deviceId,
            @RequestParam String version) {
        return ResponseEntity.ok(vehicleService.updateFirmware(deviceId, version));
    }
}