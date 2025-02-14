package com.coworking.coworkingspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coworking.coworkingspace.model.CoworkingSpace;
import java.util.List;

public interface CoworkingSpaceRepo extends JpaRepository<CoworkingSpace, Integer> {

    List<CoworkingSpace> findCoworkingSpaceByAvailable(boolean available);

    List<CoworkingSpace> findByReservationDetailsIsNotNull();
}
