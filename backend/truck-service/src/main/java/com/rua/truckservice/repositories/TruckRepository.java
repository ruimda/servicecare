package com.rua.truckservice.repositories;

import com.rua.truckservice.models.Location;
import com.rua.truckservice.models.Truck;

import java.util.List;

public interface TruckRepository {

    Truck findByLicensePlate(String licensePlate);

    List<Location> getLatestLocations(Long truckId, int numberOfLocations);
}
