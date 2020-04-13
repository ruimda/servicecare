package com.rua.truckservice.services;

import com.rua.truckservice.models.Location;
import com.rua.truckservice.models.Truck;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;


public interface TruckService {

    Optional<Truck> findTruckByLicencePlate(String licensePlate);

    List<Location> getLatestTruckLocations(Long truckId) throws EmptyResultDataAccessException;

}
