package com.masai;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.lang.reflect.Type;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private static final String CAR_FILE = "data/cars.json";
    private static final String CUSTOMER_FILE = "data/customers.json";
    private static final String RENTAL_FILE = "data/rentals.json";

    private AtomicInteger carIdCounter = new AtomicInteger(0);
    private AtomicInteger customerIdCounter = new AtomicInteger(0);

    private Gson gson;


    public CarRentalSystem() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        cars = readData(CAR_FILE, Car.class);
        customers = readData(CUSTOMER_FILE, Customer.class);
        rentals = readData(RENTAL_FILE, Rental.class);
        if (rentals == null) {
            rentals = new ArrayList<>(); // Initialize rentals if null
        }
        initializeCounters();
    }


    // Initialize counters based on existing data
    private void initializeCounters() {
        if (!cars.isEmpty()) {
            String lastCarId = cars.get(cars.size() - 1).getId();
            int numericPart = Integer.parseInt(lastCarId.substring(0, 2));
            carIdCounter.set(numericPart);
        }
        if (!customers.isEmpty()) {
            String lastCustomerId = customers.get(customers.size() - 1).getId();
            int numericPart = Integer.parseInt(lastCustomerId.substring(0, 2));
            customerIdCounter.set(numericPart);
        }
    }


    private String generateCarId() {
        String numericPart = String.format("%02d", carIdCounter.incrementAndGet());
        return numericPart + "CR";
    }

    private String generateCustomerId() {
        String numericPart = String.format("%02d", customerIdCounter.incrementAndGet());
        return numericPart + "CT";
    }


    // Add car
    public void addCar(String make, String model) {
        String carId = generateCarId();
        Car car = new Car(carId, make, model);
        cars.add(car);
        saveData(CAR_FILE, cars);
    }

    // Add customer
    public void addCustomer(String name) {
        String customerId = generateCustomerId();
        Customer customer = new Customer(customerId, name);
        customers.add(customer);
        saveData(CUSTOMER_FILE, customers);
    }

    // Add rental
    public void addRental(Rental rental) {
        Optional<Car> carOptional = cars.stream()
                                        .filter(car -> car.getId().equals(rental.getCarId()))
                                        .findFirst();

        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            if (car.isRented()) {
                throw new DuplicateRentalException("Car is already rented.");
            } else {
                car.setRented(true); // Mark the car as rented
                rentals.add(rental);
                saveData(RENTAL_FILE, rentals);
            }
        } else {
            throw new IllegalArgumentException("Car not found.");
        }
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
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName))) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read data from file
    private <T> List<T> readData(String fileName, Class<T> clazz) {
        List<T> data = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(fileName))) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true); // Set lenient mode to handle empty files
            data = gson.fromJson(jsonReader, listType);
            if (data == null) {
                data = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println(fileName + " not found or could not be read. A new file will be created.");
        } catch (JsonSyntaxException e) {
            System.out.println("Error parsing JSON in file: " + fileName);
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

    // Nested classes for better organization

    public static class DuplicateCarException extends RuntimeException {
        public DuplicateCarException(String message) {
            super(message);
        }
    }

    public static class DuplicateCustomerException extends RuntimeException {
        public DuplicateCustomerException(String message) {
            super(message);
        }
    }

 // In CarRentalSystem.java
    public static class DuplicateRentalException extends RuntimeException {
        public DuplicateRentalException(String message) {
            super(message);
        }
    }

}
