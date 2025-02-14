package com.coworking.coworkingspace.service;

import org.springframework.stereotype.Component;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.model.TypeOfSpace;

@Component
public class ReservationService {

    public CoworkingSpace reserveSpace(CoworkingSpace space, String reservationDetails) {
        if (!space.isAvailable()) {
            throw new IllegalStateException("Space is already reserved!");
        }

        space.setAvailable(false);

        String reservationType = (space.getType().equalsIgnoreCase(TypeOfSpace.PRIVATE.name()))
                ? "Prepaid reservation"
                : "Standard reservation";

        space.setReservationDetails(reservationDetails + " | " + reservationType);

        return space;
    }

    public CoworkingSpace cancelReservation(CoworkingSpace space) {
        if (space.isAvailable()) {
            throw new IllegalStateException("Space is not currently reserved!");
        }
        space.setReservationDetails(null);
        space.setAvailable(true);
        return space;
    }
}
