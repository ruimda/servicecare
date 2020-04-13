package com.rua.truckservice.controllers;


import com.rua.truckservice.models.Location;
import com.rua.truckservice.models.Truck;
import com.rua.truckservice.services.TruckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {
    final static Logger log = LoggerFactory.getLogger(TruckController.class);

    private TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping("")
    public ResponseEntity<Truck> findTruckByLP(
            @RequestParam(required = true) String licensePlate
    ){
        Optional<Truck> truck = truckService.findTruckByLicencePlate(licensePlate);
        if (truck.isPresent()) {
            return new ResponseEntity<>(truck.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Truck) null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/locations")
    public List<Location> findTruckLocations(@PathVariable Long id) {
        return truckService.getLatestTruckLocations(id);
    }

}
