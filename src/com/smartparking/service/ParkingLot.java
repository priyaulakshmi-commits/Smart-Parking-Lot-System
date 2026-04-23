package com.smartparking.service;

import com.smartparking.model.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private List<Floor> floors;
    private Map<String, Ticket> activeTickets = new ConcurrentHashMap<>();

    public ParkingLot(List<Floor> floors) {
        this.floors = floors;
    }

    public synchronized Ticket checkIn(Vehicle v) {
        for (Floor f : floors) {
            Optional<ParkingSpot> spotOpt = f.getSpot(v.getType());

            if (spotOpt.isPresent()) {
                ParkingSpot spot = spotOpt.get();

                if (spot.park(v)) {
                    String id = UUID.randomUUID().toString();
                    Ticket ticket = new Ticket(id, v, spot);
                    activeTickets.put(id, ticket);
                    return ticket;
                }
            }
        }
        throw new RuntimeException("No spot available");
    }

    public synchronized double checkOut(String ticketId) {
        Ticket t = activeTickets.get(ticketId);

        if (t == null) {
            throw new RuntimeException("Invalid Ticket");
        }

        t.close();
        t.getSpot().leave();

        double fee = FeeCalculator.calculate(t);
        activeTickets.remove(ticketId);

        return fee;
    }
}