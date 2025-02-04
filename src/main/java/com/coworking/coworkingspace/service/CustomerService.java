package com.coworking.coworkingspace.service;

import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.model.Reservations;
import com.coworking.coworkingspace.repository.CoworkingSpaceRepo;
import com.coworking.coworkingspace.repository.ReservationsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CoworkingSpaceRepo coworkingSpaceRepo;
    private final ReservationsRepo reservationsRepo;

    public CustomerService(CoworkingSpaceRepo coworkingSpaceRepo, ReservationsRepo reservationsRepo) {
        this.coworkingSpaceRepo = coworkingSpaceRepo;
        this.reservationsRepo = reservationsRepo;
    }

    public List<CoworkingSpace> findAvailableSpaces() {
        return coworkingSpaceRepo.findByAvailableTrue();
    }

    public Reservations makeReservation(int spaceID, String customerName, String date, String startTime, String endTime) {
        return coworkingSpaceRepo.findById(spaceID)
                .filter(CoworkingSpace::isAvailable)
                .map(space -> {
                    space.setAvailable(false);
                    coworkingSpaceRepo.save(space);

                    Reservations reservation = new Reservations(0, customerName, date, startTime, endTime, space);
                    return reservationsRepo.save(reservation);
                })
                .orElseThrow(() -> new RuntimeException("Space with ID " + spaceID + " is not available!"));
    }

    public List<Reservations> viewMyReservations(String customerName) {
        return reservationsRepo.findByCustomerName(customerName);
    }

    public void cancelReservation(int reservationID) {
        reservationsRepo.findById(reservationID).ifPresentOrElse(reservation -> {
            coworkingSpaceRepo.findById(reservation.getSpace().getSpaceID()).ifPresent(space -> {
                space.setAvailable(true);
                coworkingSpaceRepo.save(space);
            });

            reservationsRepo.deleteById(reservationID);
        }, () -> {
            throw new RuntimeException("No reservation found with ID " + reservationID);
        });
    }
}
