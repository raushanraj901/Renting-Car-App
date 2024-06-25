package com.masai;

import java.io.Serializable;
import java.time.LocalDate;

public class Rental implements Serializable {
    private String carId;
    private String customerId;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    public Rental(String carId, String customerId, LocalDate rentalDate, LocalDate returnDate) {
        this.carId = carId;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public String getCarId() {
        return carId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "carId='" + carId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
