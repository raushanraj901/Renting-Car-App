package com.masai;

import java.io.Serializable;

public class Car implements Serializable {
    private String id;
    private String make;
    private String model;
    private boolean isRented;

    public Car(String id, String make, String model) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.isRented = false;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", isRented=" + isRented +
                '}';
    }
}

