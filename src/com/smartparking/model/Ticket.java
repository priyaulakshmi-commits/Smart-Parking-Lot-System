package com.smartparking.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {
    private String id;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private LocalDateTime entry;
    private LocalDateTime exit;

    public Ticket(String id, Vehicle v, ParkingSpot spot) {
        this.id = id;
        this.vehicle = v;
        this.spot = spot;
        this.entry = LocalDateTime.now();
    }

    public void close() {
        this.exit = LocalDateTime.now();
    }

    public long getHours() {
        return Math.max(1, Duration.between(entry, exit).toHours());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public String getId() {
        return id;
    }
}