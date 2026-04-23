package com.smartparking.main;

import com.smartparking.model.*;
import com.smartparking.service.*;

import java.util.*;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        List<ParkingSpot> spots = new ArrayList<>();

        for (int i = 1; i <= 5; i++)
            spots.add(new ParkingSpot(i, VehicleType.CAR));

        for (int i = 6; i <= 8; i++)
            spots.add(new ParkingSpot(i, VehicleType.MOTORCYCLE));

        for (int i = 9; i <= 10; i++)
            spots.add(new ParkingSpot(i, VehicleType.BUS));

        Floor floor = new Floor(1, spots);
        ParkingLot lot = new ParkingLot(Arrays.asList(floor));

        Map<String, Ticket> tickets = new HashMap<>();

        while (true) {
            System.out.println("\n==== SMART PARKING SYSTEM ====");
            System.out.println("1. Check-In");
            System.out.println("2. Check-Out");
            System.out.println("3. View Availability");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    checkIn(lot, tickets);
                    break;

                case 2:
                    checkOut(lot, tickets);
                    break;

                case 3:
                    showAvailability(spots);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    /* ================= CHECK-IN ================= */
    private static void checkIn(ParkingLot lot, Map<String, Ticket> tickets) {

        System.out.print("Enter vehicle number: ");
        String number = sc.next();

        System.out.println("Select vehicle type:");
        System.out.println("1. MOTORCYCLE");
        System.out.println("2. CAR");
        System.out.println("3. BUS");

        int typeChoice = sc.nextInt();

        VehicleType type = switch (typeChoice) {
            case 1 -> VehicleType.MOTORCYCLE;
            case 2 -> VehicleType.CAR;
            case 3 -> VehicleType.BUS;
            default -> throw new RuntimeException("Invalid type");
        };

        Vehicle vehicle = new Vehicle(number, type);

        try {
            Ticket ticket = lot.checkIn(vehicle);
            tickets.put(ticket.getId(), ticket);

            System.out.println("✅ Vehicle Parked!");
            System.out.println("Ticket ID: " + ticket.getId());

        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    /* ================= CHECK-OUT ================= */
    private static void checkOut(ParkingLot lot, Map<String, Ticket> tickets) {

        System.out.print("Enter Ticket ID: ");
        String id = sc.next();

        try {
            double fee = lot.checkOut(id);
            tickets.remove(id);

            System.out.println("💰 Fee: " + fee);

        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    /* ================= AVAILABILITY ================= */
    private static void showAvailability(List<ParkingSpot> spots) {

        long free = spots.stream().filter(ParkingSpot::isAvailable).count();
        long occupied = spots.size() - free;

        System.out.println("🅿️ Total Spots: " + spots.size());
        System.out.println("✅ Available: " + free);
        System.out.println("❌ Occupied: " + occupied);
    }
}