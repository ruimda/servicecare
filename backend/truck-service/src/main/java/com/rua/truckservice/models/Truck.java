package com.rua.truckservice.models;

import java.util.Objects;

public class Truck {

    private Long id;

    private String licensePlate;

    private String brand;

    private String model;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        Truck other = (Truck) otherObject;

        return Objects.equals(id, other.id)
                && Objects.equals(licensePlate, other.licensePlate)
                && Objects.equals(brand, other.brand)
                && Objects.equals(model, other.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, licensePlate, brand, model);
    }

    @Override
    public String toString() {
        return getClass().getName()
                + "[id=" + id
                + ",licensePlate=" + licensePlate
                + ",brand=" + brand
                + ",model=" + model
                + "]";
    }

}
