package com.smartparking.service;

import com.smartparking.model.Ticket;

import java.time.Duration;

public class FeeCalculator {

    public static double calculate(Ticket t) {

        Duration duration = Duration.between(t.getEntryTime(), t.getExitTime());

        long minutes = duration.toMinutes();

        // 🚫 Grace period
        if (minutes <= 30) {
            return 0;
        }

        // Convert to hours (rounded up)
        long hours = (long) Math.ceil(minutes / 60.0);

        double rate;

        switch (t.getVehicle().getType()) {
            case MOTORCYCLE: rate = 10; break;
            case CAR: rate = 20; break;
            case BUS: rate = 50; break;
            default: rate = 0;
        }

        double fee = hours * rate;

        // 💡 Long parking discount
        if (hours > 5) {
            fee = fee * 0.9; // 10% discount
        }

        return fee;
    }
}