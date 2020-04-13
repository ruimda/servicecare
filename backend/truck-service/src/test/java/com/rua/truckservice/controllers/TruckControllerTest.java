package com.rua.truckservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rua.truckservice.models.Location;
import com.rua.truckservice.models.Truck;
import com.rua.truckservice.services.TruckService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TruckController.class)
public class TruckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TruckService truckService;

    @Test
    void findTruckByLP_LicensePlateMissing_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/trucks")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findTruckByLP_LicensePlateNotFound_ReturnsNotFound() throws Exception {
        // prepare
        Mockito.when(truckService.findTruckByLicencePlate(Mockito.anyString()))
                .thenReturn(Optional.empty());

        // test+assert
        mockMvc.perform(get("/api/trucks")
                .contentType("application/json")
                .param("licensePlate","XPTO-123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findTruckByLP_LicensePlateFound_ReturnsTruck() throws Exception {
        // prepare
        String plate = "ANY-999";
        Long id = 123L;
        Truck truck = new Truck();
        truck.setId(id);
        truck.setLicensePlate(plate);
        truck.setBrand("MAN");
        truck.setModel("XPTO");

        Mockito.when(truckService.findTruckByLicencePlate(plate))
                .thenReturn(Optional.of(truck));

        // test+assert
        MvcResult mvcResult = mockMvc.perform(get("/api/trucks")
                .contentType("application/json")
                .param("licensePlate", "ANY-999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.licensePlate", is("ANY-999")))
                .andExpect(jsonPath("$.brand", is("MAN")))
                .andExpect(jsonPath("$.model", is("XPTO")))
                .andReturn();
    }

    @Test
    void findTruckLocations_EmptyId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/trucks/{id}/locations", "")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findTruckLocations_IdNotFound_ReturnsEmptyArray() throws Exception {
        Long id = 123L;
        List<Location> serviceLocations = new ArrayList<>();

        Mockito.when(truckService.getLatestTruckLocations(id))
                .thenReturn(serviceLocations);

        mockMvc.perform(get("/api/trucks/{id}/locations", "123")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void findTruckLocations_IdFound_ReturnsLocationsArray() throws Exception {
        Long id = 123L;
        List<Location> serviceLocations = new ArrayList<>();
        serviceLocations.add( new Location(1,1));
        serviceLocations.add( new Location(2,2));
        serviceLocations.add( new Location(3,3));

        Mockito.when(truckService.getLatestTruckLocations(id))
                .thenReturn(serviceLocations);

        mockMvc.perform(get("/api/trucks/{id}/locations", "123")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
