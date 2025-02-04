package com.coworking.coworkingspace.repository;

import com.coworking.coworkingspace.model.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsRepo extends JpaRepository<Reservations, Integer> {

    List<Reservations> findByCustomerName(String customerName);
}
