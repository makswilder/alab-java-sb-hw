package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void shouldReturnSpacesViewWhenSpaceIsReserved() throws Exception {
        List<CoworkingSpace> spaces = List.of(new CoworkingSpace(1, "Private Office", 100, true));
        when(adminService.viewSpaces()).thenReturn(spaces);

        mockMvc.perform(get("/admin/spaces"))
                .andExpect(status().isOk())
                .andExpect(content().string("spaces"));

        verify(adminService, times(1)).viewSpaces();
    }

    @Test
    void shouldAddSpaceAndRedirect() throws Exception {
        CoworkingSpace space = new CoworkingSpace(1, "Shared Desk", 50, true);

        when(adminService.addSpace(any(CoworkingSpace.class))).thenReturn(space);

        mockMvc.perform(post("/admin/add")
                        .param("type", "Shared Desk")
                        .param("price", "50"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/spaces"));

        verify(adminService, times(1)).addSpace(any(CoworkingSpace.class));
    }

    @Test
    void shouldRemoveSpaceAndRedirect() throws Exception {
        doNothing().when(adminService).removeSpace(1);

        mockMvc.perform(get("/admin/remove/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/spaces"));

        verify(adminService, times(1)).removeSpace(1);
    }
}
