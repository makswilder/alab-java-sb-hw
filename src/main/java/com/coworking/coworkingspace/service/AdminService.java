package com.coworking.coworkingspace.service;

import com.coworking.coworkingspace.exception.SpaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.model.TypeOfSpace;
import com.coworking.coworkingspace.repository.CoworkingSpaceRepo;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CoworkingSpaceRepo coworkingSpaceRepo;

    public void addSpace(CoworkingSpace newSpace) {
        coworkingSpaceRepo.save(newSpace);
    }

    public CoworkingSpace createSpace(String name, String type, double price) {
        return CoworkingSpace.builder()
                .name(name)
                .type(TypeOfSpace.valueOf(type).name())
                .price(price)
                .available(true)
                .build();
    }

    @Transactional
    public void removeSpace(int id) {
        coworkingSpaceRepo.deleteById(id);
    }

    @Transactional
    public void updateSpace(int id, CoworkingSpace updatedSpace) throws SpaceNotFoundException {
        CoworkingSpace space = coworkingSpaceRepo.findById(id)
                .orElseThrow(() -> new SpaceNotFoundException("Space not found"));
        space.setName(updatedSpace.getName());
        space.setType(updatedSpace.getType());
        space.setPrice(updatedSpace.getPrice());
        coworkingSpaceRepo.save(space);
    }
}
