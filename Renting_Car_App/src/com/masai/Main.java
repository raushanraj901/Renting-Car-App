package com.masai;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CarRentalSystem system = new CarRentalSystem();

    public static void main(String[] args) {
        displayMenu();
        int choice = getUserChoice();

        while (choice != 7) {
            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    addCustomer();
                    break;
                case 3:
                    rentCar();
                    break;
                case 4:
                    listCars();
                    break;
                case 5:
                    listCustomers();
                    break;
                case 6:
                    listRentals();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                    break;
            }
            displayMenu();
            choice = getUserChoice();
        }

        System.out.println("Exiting...");
    }

    private static void displayMenu() {
        System.out.println("\n===== Car Rental System Menu =====");
        System.out.println("1. Add Car");
        System.out.println("2. Add Customer");
        System.out.println("3. Rent Car");
        System.out.println("4. List Cars");
        System.out.println("5. List Customers");
        System.out.println("6. List Rentals");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0; // Invalid choice
        }
    }

    private static void addCar() {
        System.out.print("Enter Make: ");
        String make = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();

        system.addCar(make, model);
        System.out.println("Car added successfully!");
    }

    private static void addCustomer() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        system.addCustomer(name);
        System.out.println("Customer added successfully!");
    }

    private static void rentCar() {
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();
        Optional<Car> carOptional = system.getCarById(carId);

        if (!carOptional.isPresent()) {
            System.out.println("Car not found or already rented. Please add a new car.");
            return;
        }

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Optional<Customer> customerOptional = system.getCustomerById(customerId);

        if (!customerOptional.isPresent()) {
            System.out.println("Customer not found. Please add a new customer.");
            return;
        }

        System.out.print("Enter Rental Date (yyyy-mm-dd): ");
        LocalDate rentalDate = getLocalDateFromInput();
        System.out.print("Enter Return Date (yyyy-mm-dd): ");
        LocalDate returnDate = getLocalDateFromInput();

        if (!system.areDatesValid(rentalDate, returnDate)) {
            System.out.println("Rental date and return date should not be in the past.");
            return;
        }

        Rental rental = new Rental(carId, customerId, rentalDate, returnDate);
        system.addRental(rental);
        System.out.println("Car rented successfully!");
    }

    private static LocalDate getLocalDateFromInput() {
        LocalDate date = null;
        while (date == null) {
            try {
                date = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid date format. Enter again (yyyy-mm-dd): ");
            }
        }
        return date;
    }

    private static void listCars() {
        System.out.println("\nList of Cars:");
        system.listCars();
    }

    private static void listCustomers() {
        System.out.println("\nList of Customers:");
        system.listCustomers();
    }

    private static void listRentals() {
        System.out.println("\nList of Rentals:");
        system.listRentals();
    }
    
}
