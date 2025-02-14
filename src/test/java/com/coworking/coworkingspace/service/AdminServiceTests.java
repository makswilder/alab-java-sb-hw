package com.coworking.coworkingspace.service;

import com.coworking.coworkingspace.exception.SpaceNotFoundException;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.model.TypeOfSpace;
import com.coworking.coworkingspace.repository.CoworkingSpaceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTests {

    private AdminService adminService;
    private CoworkingSpaceRepo coworkingSpaceRepoMock;

    @BeforeEach
    void setUp() {
        coworkingSpaceRepoMock = mock(CoworkingSpaceRepo.class);
        adminService = new AdminService(coworkingSpaceRepoMock);
    }

    @Test
    void whenAddSpace_thenRepositorySaveIsCalled() {
        CoworkingSpace space = CoworkingSpace.builder()
                .name("TestSpace")
                .type(TypeOfSpace.OPEN_SPACE.name())
                .price(100.0)
                .available(true)
                .build();

        adminService.addSpace(space);

        ArgumentCaptor<CoworkingSpace> captor = ArgumentCaptor.forClass(CoworkingSpace.class);
        verify(coworkingSpaceRepoMock).save(captor.capture());
        CoworkingSpace capturedSpace = captor.getValue();

        assertEquals("TestSpace", capturedSpace.getName());
        assertEquals(TypeOfSpace.OPEN_SPACE.name(), capturedSpace.getType());
        assertEquals(100.0, capturedSpace.getPrice());
        assertTrue(capturedSpace.isAvailable());
    }

    @Test
    void whenCreateSpace_thenReturnValidCoworkingSpace() {
        CoworkingSpace space = adminService.createSpace("NewSpace", "PRIVATE", 150.0);

        assertNotNull(space);
        assertEquals("NewSpace", space.getName());
        assertEquals(TypeOfSpace.PRIVATE.name(), space.getType());
        assertEquals(150.0, space.getPrice());
        assertTrue(space.isAvailable());
    }

    @Test
    void whenRemoveSpace_thenRepositoryDeleteIsCalled() {
        int spaceId = 5;

        adminService.removeSpace(spaceId);

        verify(coworkingSpaceRepoMock).deleteById(spaceId);
    }

    @Test
    void whenRemoveNonexistentSpace_thenThrowException() {
        int invalidId = 999;
        doThrow(new EmptyResultDataAccessException(1)).when(coworkingSpaceRepoMock).deleteById(invalidId);

        assertThrows(EmptyResultDataAccessException.class, () -> adminService.removeSpace(invalidId));
    }

    @Test
    void whenUpdateSpaceWithValidId_thenRepositorySaveIsCalled() throws SpaceNotFoundException {
        int spaceId = 1;

        CoworkingSpace existingSpace = new CoworkingSpace(1, "OldSpace", "OPEN_SPACE", 100.0, true, null);
        CoworkingSpace updatedSpace = CoworkingSpace.builder()
                .name("UpdatedSpace")
                .type(TypeOfSpace.PRIVATE.name())
                .price(200.0)
                .available(false)
                .build();

        when(coworkingSpaceRepoMock.findById(spaceId)).thenReturn(Optional.of(existingSpace));

        adminService.updateSpace(spaceId, updatedSpace);

        verify(coworkingSpaceRepoMock).save(argThat(space ->
                space.getName().equals("UpdatedSpace") &&
                        space.getType().equals(TypeOfSpace.PRIVATE.name()) &&
                        space.getPrice() == 200.0 &&
                        space.isAvailable()
        ));
    }

    @Test
    void whenUpdateSpaceWithInvalidId_thenThrowSpaceNotFoundException() {
        int invalidId = 99;
        CoworkingSpace updatedSpace = CoworkingSpace.builder()
                .name("NonExistent")
                .type(TypeOfSpace.OPEN_SPACE.name())
                .price(300.0)
                .available(true)
                .build();

        when(coworkingSpaceRepoMock.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(SpaceNotFoundException.class, () -> adminService.updateSpace(invalidId, updatedSpace));
    }
}
