package com.smartparking.model;

import java.util.List;
import java.util.Optional;

public class Floor {
    private int floorNumber;
    private List<ParkingSpot> spots;

    public Floor(int num, List<ParkingSpot> spots) {
        this.floorNumber = num;
        this.spots = spots;
    }

    public Optional<ParkingSpot> getSpot(VehicleType type) {
        return spots.stream()
                .filter(s -> s.isAvailable() && s.getType() == type)
                .findFirst();
    }
}