package com.coworking.coworkingspace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.repository.CoworkingSpaceRepo;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CoworkingSpaceRepo coworkingSpaceRepo;
    private final ReservationService reservationService;

    @Transactional
    public void reserve(int spaceId, String reservationDetails) {
        CoworkingSpace space = coworkingSpaceRepo.findById(spaceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found"));

        CoworkingSpace reservedSpace = reservationService.reserveSpace(space, reservationDetails);
        coworkingSpaceRepo.save(reservedSpace);
    }

    @Transactional
    public void cancelReservation(int spaceId) {
        CoworkingSpace space = coworkingSpaceRepo.findById(spaceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found"));

        CoworkingSpace updatedSpace = reservationService.cancelReservation(space);
        coworkingSpaceRepo.save(updatedSpace);
    }
}
