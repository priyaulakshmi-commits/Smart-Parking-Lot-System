package com.smartparking.service;

import com.smartparking.model.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {

    private List<Floor> floors;
    private Map<String, Ticket> activeTickets = new ConcurrentHashMap<>();

    public ParkingLot(List<Floor> floors) {
        this.floors = floors;
    }

    // ================= CHECK-IN =================
    public synchronized Ticket checkIn(Vehicle vehicle, LocalDateTime entryTime) {

        for (Floor floor : floors) {
            Optional<ParkingSpot> spotOpt = floor.getSpot(vehicle.getType());

            if (spotOpt.isPresent()) {
                ParkingSpot spot = spotOpt.get();

                if (spot.park(vehicle)) {
                    String ticketId = UUID.randomUUID().toString();

                    Ticket ticket = new Ticket(ticketId, vehicle, spot, entryTime);
                    activeTickets.put(ticketId, ticket);

                    System.out.println("✅ Parked at Spot: " + spot.getId());
                    return ticket;
                }
            }
        }

        throw new RuntimeException("❌ No parking spot available");
    }

    // ================= CHECK-OUT =================
    public synchronized double checkOut(String ticketId, LocalDateTime exitTime) {

        Ticket ticket = activeTickets.get(ticketId);

        if (ticket == null) {
            throw new RuntimeException("❌ Invalid Ticket ID");
        }

        // ⚠️ Validation: exit time should be after entry
        if (exitTime.isBefore(ticket.getEntryTime())) {
            throw new RuntimeException("❌ Exit time cannot be before entry time");
        }

        ticket.close(exitTime);
        ticket.getSpot().leave();

        double fee = FeeCalculator.calculate(ticket);

        activeTickets.remove(ticketId);

        System.out.println("🚪 Vehicle exited from Spot: " + ticket.getSpot().getId());

        return fee;
    }
}