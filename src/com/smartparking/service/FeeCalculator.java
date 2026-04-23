package com.smartparking.service;

import com.smartparking.model.Ticket;

public class FeeCalculator {

    public static double calculate(Ticket t) {
        long hours = t.getHours();

        switch (t.getVehicle().getType()) {
            case MOTORCYCLE: return hours * 10;
            case CAR: return hours * 20;
            case BUS: return hours * 50;
            default: return 0;
        }
    }
}