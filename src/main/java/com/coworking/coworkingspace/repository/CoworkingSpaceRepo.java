package com.coworking.coworkingspace.repository;

import com.coworking.coworkingspace.model.CoworkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoworkingSpaceRepo extends JpaRepository<CoworkingSpace, Integer> {

    List<CoworkingSpace> findByAvailableTrue();
}
