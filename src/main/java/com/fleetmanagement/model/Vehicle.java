package com.fleetmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String deviceId;

    private String vin;
    private String vehicleType;
    private String firmwareVersion;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    private LocalDateTime lastSeen;
    private LocalDateTime registeredAt = LocalDateTime.now();
}