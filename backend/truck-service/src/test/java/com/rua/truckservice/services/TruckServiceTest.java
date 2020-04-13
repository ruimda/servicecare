package com.rua.truckservice.services;

import com.rua.truckservice.BaseTest;
import com.rua.truckservice.models.Location;
import com.rua.truckservice.models.Truck;
import com.rua.truckservice.repositories.TruckRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TruckServiceTest extends BaseTest {

    @Autowired
    TruckService truckService;

    @MockBean
    private TruckRepository truckRepo;

    @Test
    public void findTruckByLicencePlate_LicencePlateNotFound_ReturnsEmptyOptional() {
        // prepare
        String plate = "ANY-999";
        Mockito.when(truckRepo.findByLicensePlate(plate))
                .thenThrow(new EmptyResultDataAccessException(1));

        // test
        Optional<Truck> truckOptional = truckService.findTruckByLicencePlate(plate);

        // assert
        assertThat(truckOptional)
                .isNotNull()
                .isInstanceOf(Optional.class);

        assertThat(truckOptional.isPresent())
                .isFalse();
    }

    @Test
    public void findTruckByLicencePlate_LicencePlateFound_ReturnsTruckOptional() {
        // prepare
        String plate = "ANY-999";
        Long id = 123L;
        Truck truck = new Truck();
        truck.setId(id);
        truck.setLicensePlate(plate);
        truck.setBrand("MAN");
        truck.setModel("XPTO");
        Mockito.when(truckRepo.findByLicensePlate(plate))
                .thenReturn(truck);

        // test
        Optional<Truck> truckOptional = truckService.findTruckByLicencePlate(plate);

        // assert
        assertThat(truckOptional)
                .isNotNull()
                .isInstanceOf(Optional.class);

        assertThat(truckOptional.isPresent())
                .isTrue();

        assertThat(truck.getId()).isEqualTo(123L);
        assertThat(truck.getLicensePlate()).isEqualTo("ANY-999");
        assertThat(truck.getBrand()).isEqualTo("MAN");
        assertThat(truck.getModel()).isEqualTo("XPTO");
    }

    @Test
    public void getLatestTruckLocations_LocationsNotFound_ReturnEmptyArray() {
        // prepare
        Long id = 123L;
        List<Location> emptyList = new ArrayList<>();

        Mockito.when(truckRepo.getLatestLocations(id, 5))
                .thenReturn(emptyList);

        // test
        List<Location> locations = truckService.getLatestTruckLocations(id);

        // assert
        assertThat(locations)
                .isNotNull()
                .isInstanceOf(List.class)
                .isEmpty();
    }

    @Test
    public void getLatestTruckLocations_LocationsFound_ReturnLocationsArray() {
        // prepare
        Long id = 123L;
        List<Location> repoLocations = new ArrayList<>();
        repoLocations.add(new Location(1, 1));
        repoLocations.add(new Location(2, 2));
        repoLocations.add(new Location(3, 3));

        Mockito.when(truckRepo.getLatestLocations(Mockito.eq(id), Mockito.anyInt()))
                .thenReturn(repoLocations);

        // test
        List<Location> locations = truckService.getLatestTruckLocations(id);

        // assert
        assertThat(locations)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(3);
    }
}
