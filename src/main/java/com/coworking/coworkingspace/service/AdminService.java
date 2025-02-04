package com.coworking.coworkingspace.service;

import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.repository.CoworkingSpaceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final CoworkingSpaceRepo coworkingSpaceRepo;

    public AdminService(CoworkingSpaceRepo coworkingSpaceRepo) {
        this.coworkingSpaceRepo = coworkingSpaceRepo;
    }

    public CoworkingSpace addSpace(CoworkingSpace newSpace) {
        if (coworkingSpaceRepo.existsById(newSpace.getSpaceID())) {
            throw new IllegalStateException("Space with ID " + newSpace.getSpaceID() + " already exists!");
        }
        return coworkingSpaceRepo.save(newSpace);
    }

    public void removeSpace(int spaceID) {
        if (!coworkingSpaceRepo.existsById(spaceID)) {
            throw new IllegalStateException("Space with ID " + spaceID + " does not exist!");
        }
        coworkingSpaceRepo.deleteById(spaceID);
    }

    public List<CoworkingSpace> viewSpaces() {
        return coworkingSpaceRepo.findAll();
    }
}
