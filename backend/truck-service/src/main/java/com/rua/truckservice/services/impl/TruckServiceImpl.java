package com.rua.truckservice.services.impl;

import com.rua.truckservice.conf.AppProperties;
import com.rua.truckservice.models.Location;
import com.rua.truckservice.models.Truck;
import com.rua.truckservice.repositories.TruckRepository;
import com.rua.truckservice.services.TruckService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TruckServiceImpl implements TruckService {

    private TruckRepository truckRepo;
    private AppProperties props;

    public TruckServiceImpl(TruckRepository truckRepository, AppProperties props) {
        this.truckRepo = truckRepository;
        this.props = props;
    }

    @Override
    public Optional<Truck> findTruckByLicencePlate(String licensePlate) {
        try {
            Truck truck = truckRepo.findByLicensePlate(licensePlate);
            return Optional.of(truck);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Location> getLatestTruckLocations(Long truckId) {
        return truckRepo.getLatestLocations(truckId, props.getLocationHistorySize());
    }
}
