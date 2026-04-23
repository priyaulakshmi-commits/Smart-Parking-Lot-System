package com.smartparking.main;

import com.smartparking.model.*;
import com.smartparking.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {

        // ===== Create Parking Spots =====
        List<ParkingSpot> spots = new ArrayList<>();

        for (int i = 1; i <= 5; i++)
            spots.add(new ParkingSpot(i, VehicleType.CAR));

        for (int i = 6; i <= 8; i++)
            spots.add(new ParkingSpot(i, VehicleType.MOTORCYCLE));

        for (int i = 9; i <= 10; i++)
            spots.add(new ParkingSpot(i, VehicleType.BUS));

        ParkingLot parkingLot = new ParkingLot(
                Arrays.asList(new Floor(1, spots))
        );

        // ===== MENU LOOP =====
        while (true) {

            System.out.println("\n===== SMART PARKING SYSTEM =====");
            System.out.println("1. Check-In");
            System.out.println("2. Check-Out");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            try {

                switch (choice) {

                    // ================= CHECK-IN =================
                    case 1:
                        System.out.print("Enter Vehicle Number: ");
                        String number = sc.nextLine();

                        System.out.println("Select Vehicle Type:");
                        System.out.println("1. Motorcycle  2. Car  3. Bus");
                        int typeChoice = sc.nextInt();
                        sc.nextLine();

                        VehicleType type = switch (typeChoice) {
                            case 1 -> VehicleType.MOTORCYCLE;
                            case 2 -> VehicleType.CAR;
                            case 3 -> VehicleType.BUS;
                            default -> throw new RuntimeException("Invalid vehicle type");
                        };

                        System.out.print("Enter Entry Time (yyyy-MM-dd HH:mm): ");
                        LocalDateTime entryTime =
                                LocalDateTime.parse(sc.nextLine(), formatter);

                        Ticket ticket = parkingLot.checkIn(
                                new Vehicle(number, type),
                                entryTime
                        );

                        System.out.println("🎫 Ticket Generated: " + ticket.getId());
                        break;

                    // ================= CHECK-OUT =================
                    case 2:
                        System.out.print("Enter Ticket ID: ");
                        String ticketId = sc.nextLine();

                        System.out.print("Enter Exit Time (yyyy-MM-dd HH:mm): ");
                        LocalDateTime exitTime =
                                LocalDateTime.parse(sc.nextLine(), formatter);

                        double fee = parkingLot.checkOut(ticketId, exitTime);

                        System.out.println("💰 Parking Fee: ₹" + fee);
                        break;

                    // ================= EXIT =================
                    case 3:
                        System.out.println("👋 Exiting system...");
                        return;

                    default:
                        System.out.println("❌ Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }
}