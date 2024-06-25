package com.masai;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private static final String CAR_FILE = "data/cars.dat";
    private static final String CUSTOMER_FILE = "data/customers.dat";
    private static final String RENTAL_FILE = "data/rentals.dat";

    public CarRentalSystem() {
        cars = readData(CAR_FILE);
        customers = readData(CUSTOMER_FILE);
        rentals = readData(RENTAL_FILE);
    }

    // Add car
    public void addCar(Car car) {
        cars.add(car);
        saveData(CAR_FILE, cars);
    }

    // Add customer
    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveData(CUSTOMER_FILE, customers);
    }

    // Add rental
    public void addRental(Rental rental) {
        rentals.add(rental);
        saveData(RENTAL_FILE, rentals);
    }

    // List all cars
    public void listCars() {
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    // List all customers
    public void listCustomers() {
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    // List all rentals
    public void listRentals() {
        for (Rental rental : rentals) {
            System.out.println(rental);
        }
    }

    // Save data to file
    private <T> void saveData(String fileName, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read data from file
    @SuppressWarnings("unchecked")
    private <T> List<T> readData(String fileName) {
        List<T> data = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            data = (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found. A new file will be created.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Check if a car is available
    public Optional<Car> getCarById(String carId) {
        return cars.stream().filter(car -> car.getId().equals(carId) && !car.isRented()).findFirst();
    }

    // Check if a customer exists
    public Optional<Customer> getCustomerById(String customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst();
    }

    // Check if the dates are valid
    public boolean areDatesValid(LocalDate rentalDate, LocalDate returnDate) {
        LocalDate today = LocalDate.now();
        return !rentalDate.isBefore(today) && !returnDate.isBefore(today);
    }
}

