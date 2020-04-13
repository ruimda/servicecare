package com.rua.truckservice.repositories.impl;

import com.rua.truckservice.models.Location;
import com.rua.truckservice.models.Truck;
import com.rua.truckservice.repositories.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcTruckRepository implements TruckRepository {

    final static Logger log = LoggerFactory.getLogger(JdbcTruckRepository.class);
    private JdbcTemplate jdbc;

    public JdbcTruckRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Truck findByLicensePlate(String licensePlate) {
        final String SQL = "SELECT id, brand, model, licensePlate FROM Trucks WHERE licensePlate=?";
        Truck truck = jdbc.queryForObject(SQL, this::mapRowToTruck, licensePlate);
        return truck;
    }

    @Override
    public List<Location> getLatestLocations(Long truckId, int numberOfLocations) {
        final String SQL = "SELECT lat, lng FROM locations WHERE truckId=? ORDER BY dt desc LIMIT ?";
        List<Location> list = jdbc.query(SQL, this::mapRowToLocation, truckId, numberOfLocations);
        return list;
    }

    private Truck mapRowToTruck(ResultSet rs, int rowNum) throws SQLException {
        Truck truck = new Truck();
        truck.setId(rs.getLong("id"));
        truck.setBrand(rs.getString("brand"));
        truck.setModel(rs.getString("model"));
        truck.setLicensePlate(rs.getString("licensePlate"));
        return truck;
    }

    private Location mapRowToLocation(ResultSet rs, int rowNum) throws SQLException {
        return new Location(
                rs.getDouble("lat"),
                rs.getDouble("lng")
        );
    }
}

