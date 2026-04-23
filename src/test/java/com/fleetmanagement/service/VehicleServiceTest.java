package com.fleetmanagement.service;

import com.fleetmanagement.exception.ResourceNotFoundException;
import com.fleetmanagement.model.*;
import com.fleetmanagement.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private SubscriptionPackageRepository packageRepository;

    @Mock
    private VehicleSubscriptionRepository subscriptionRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setDeviceId("VH-2024-001");
        vehicle.setVin("1HGBH41JXMN109186");
        vehicle.setVehicleType("Sedan");
        vehicle.setFirmwareVersion("v1.0.0");
    }

    @Test
    void onboardVehicle_ShouldSetStatusOnline() {
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        Vehicle result = vehicleService.onboardVehicle(vehicle);
        assertEquals(VehicleStatus.ONLINE, result.getStatus());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void getVehicleByDeviceId_ShouldReturnVehicle() {
        when(vehicleRepository.findByDeviceId("VH-2024-001")).thenReturn(Optional.of(vehicle));
        Vehicle result = vehicleService.getVehicleByDeviceId("VH-2024-001");
        assertEquals("VH-2024-001", result.getDeviceId());
    }

    @Test
    void getVehicleByDeviceId_ShouldThrowException_WhenNotFound() {
        when(vehicleRepository.findByDeviceId("VH-9999")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->
                vehicleService.getVehicleByDeviceId("VH-9999"));
    }

    @Test
    void updateFirmware_ShouldUpdateVersionAndStatus() {
        when(vehicleRepository.findByDeviceId("VH-2024-001")).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        Vehicle result = vehicleService.updateFirmware("VH-2024-001", "v1.2.0");
        assertEquals(VehicleStatus.UPDATING, result.getStatus());
        assertEquals("v1.2.0", result.getFirmwareVersion());
    }
}