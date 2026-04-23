package com.smartparking.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {

    private String id;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private LocalDateTime entry;
    private LocalDateTime exit;

    // 🔥 Custom entry time
    public Ticket(String id, Vehicle vehicle, ParkingSpot spot, LocalDateTime entry) {
        this.id = id;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entry = entry;
    }

    // 🔥 Custom exit time
    public void close(LocalDateTime exitTime) {
        this.exit = exitTime;
    }

    public long getMinutes() {
        if (exit == null) return 0;
        return Duration.between(entry, exit).toMinutes();
    }

    public String getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public LocalDateTime getEntryTime() { return entry; }
    public LocalDateTime getExitTime() { return exit; }
}