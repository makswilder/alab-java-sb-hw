package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.Exceptions.SpaceNotFoundException;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.service.CoworkingSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CoworkingSpaceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CoworkingSpaceService coworkingSpaceService;

    @InjectMocks
    private CoworkingSpaceController coworkingSpaceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(coworkingSpaceController).build();
    }

    @Test
    void shouldReturnAllSpaces() throws Exception {
        List<CoworkingSpace> spaces = List.of(new CoworkingSpace(1, "Private Office", 100, true));
        when(coworkingSpaceService.getSpaces()).thenReturn(spaces);

        mockMvc.perform(get("/api/spaces")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Private Office"))
                .andExpect(jsonPath("$[0].price").value(100));

        verify(coworkingSpaceService, times(1)).getSpaces();
    }

    @Test
    void shouldAddSpaceSuccessfully() throws Exception {
        doNothing().when(coworkingSpaceService).saveSpace(any(CoworkingSpace.class));
        mockMvc.perform(post("/api/spaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"type\": \"Private Office\", \"price\": 100, \"available\": true}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Space added successfully!"));

        verify(coworkingSpaceService, times(1)).saveSpace(any(CoworkingSpace.class));
    }

    @Test
    void shouldDeleteSpaceSuccessfully() throws Exception {
        doNothing().when(coworkingSpaceService).deleteSpace(1);

        mockMvc.perform(delete("/api/spaces/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Space deleted successfully!"));

        verify(coworkingSpaceService, times(1)).deleteSpace(1);
    }


    @Test
    void shouldReturnNotFoundForNonExistingSpace() throws Exception {
        doThrow(new SpaceNotFoundException("Space not found")).when(coworkingSpaceService).deleteSpace(99);

        mockMvc.perform(delete("/api/spaces/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Space not found"));

        verify(coworkingSpaceService, times(1)).deleteSpace(99);
    }
}
