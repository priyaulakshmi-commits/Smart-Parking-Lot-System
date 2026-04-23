package com.smartparking.model;

public class ParkingSpot {
    private int id;
    private VehicleType type;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSpot(int id, VehicleType type) {
        this.id = id;
        this.type = type;
        this.occupied = false;
    }

    public synchronized boolean park(Vehicle v) {
        if (!occupied && v.getType() == type) {
            this.vehicle = v;
            this.occupied = true;
            return true;
        }
        return false;
    }

    public synchronized void leave() {
        this.vehicle = null;
        this.occupied = false;
    }

    public boolean isAvailable() {
        return !occupied;
    }

    public VehicleType getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}