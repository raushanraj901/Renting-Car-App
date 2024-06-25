package com.masai;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarRentalSystem system = new CarRentalSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Car");
            System.out.println("2. Add Customer");
            System.out.println("3. Rent Car");
            System.out.println("4. List Cars");
            System.out.println("5. List Customers");
            System.out.println("6. List Rentals");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Car ID: ");
                    String carId = scanner.nextLine();
                    System.out.print("Enter Make: ");
                    String make = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    system.addCar(new Car(carId, make, model));
                    break;
                case 2:
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    system.addCustomer(new Customer(customerId, name));
                    break;
                case 3:
                    System.out.print("Enter Car ID: ");
                    carId = scanner.nextLine();
                    Optional<Car> carOptional = system.getCarById(carId);
                    if (!carOptional.isPresent()) {
                        System.out.println("Car not found or already rented. Please add a new car.");
                        break;
                    }

                    System.out.print("Enter Customer ID: ");
                    customerId = scanner.nextLine();
                    Optional<Customer> customerOptional = system.getCustomerById(customerId);
                    if (!customerOptional.isPresent()) {
                        System.out.println("Customer not found. Please add a new customer.");
                        break;
                    }

                    System.out.print("Enter Rental Date (yyyy-mm-dd): ");
                    LocalDate rentalDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter Return Date (yyyy-mm-dd): ");
                    LocalDate returnDate = LocalDate.parse(scanner.nextLine());

                    if (!system.areDatesValid(rentalDate, returnDate)) {
                        System.out.println("Rental date and return date should not be in the past.");
                        break;
                    }

                    Rental rental = new Rental(carId, customerId, rentalDate, returnDate);
                    system.addRental(rental);
                    carOptional.get().setRented(true);
                    system.addCar(carOptional.get());  // Update the car's rented status
                    break;
                case 4:
                    system.listCars();
                    break;
                case 5:
                    system.listCustomers();
                    break;
                case 6:
                    system.listRentals();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}

