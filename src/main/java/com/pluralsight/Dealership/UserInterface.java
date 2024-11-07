package com.pluralsight.Dealership;

import com.pluralsight.Contracts.SalesContract;
import com.pluralsight.Contracts.LeaseContract;
import com.pluralsight.Contracts.ContractFileManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public void display() {
        init();
        boolean quit = false;
        while (!quit) {
            System.out.println("---------- Menu ----------");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            // Added the option for new contract creation
            System.out.println("10. Enter a New Contract");
            System.out.println("99. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processANewContract();
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void processANewContract() {
        System.out.println("Enter SALE for sale, LEASE for lease");
        String saleOrLease = scanner.nextLine();

        if (saleOrLease.equalsIgnoreCase("sale")) {
            processANewSale();
        } else if (saleOrLease.equalsIgnoreCase("lease")) {
            processANewLease();
        } else {
            System.out.println("Incorrect input!");
        }
    }

    public void processANewSale() {
        // Prompt for Sale contract details
        System.out.println("Enter Date: ");
        String date = scanner.nextLine();

        System.out.println("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Customer Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter Vehicle Vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = dealership.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.println("Enter If It is Financed (yes/no): ");
        String isFinanceOption = scanner.nextLine();
        boolean isFinanced = isFinanceOption.equalsIgnoreCase("yes");

        SalesContract salesContract = new SalesContract(date, name, email, vehicle, isFinanced);

        ContractFileManager contractFileManager = new ContractFileManager();
        contractFileManager.saveContract(salesContract);

        dealership.removeVehicle(vehicle);
        System.out.println("Sales contract created and vehicle removed from inventory.");
    }

    public void processANewLease() {
        // Prompt for Lease contract details
        System.out.println("Enter Date: ");
        String date = scanner.nextLine();

        System.out.println("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Customer Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter Vehicle Vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = dealership.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if ((LocalDate.now().getYear() - vehicle.getYear()) > 3) {
            System.err.println("Cannot lease a vehicle older than 3 years!");
            return;
        }

        LeaseContract leaseContract = new LeaseContract(date, name, email, vehicle);

        ContractFileManager contractFileManager = new ContractFileManager();
        contractFileManager.saveContract(leaseContract);

        dealership.removeVehicle(vehicle);
        System.out.println("Lease contract created and vehicle removed from inventory.");
    }

    // Existing methods for handling vehicle queries
    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        List<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
    }

    // More methods for other queries (GetByMakeModelRequest, etc.)

    private void init() {
        DealershipFileManager manager = new DealershipFileManager();
        dealership = manager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }


    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        System.out.print("Enter vehicle vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you wish to remove: ");
        int vin = scanner.nextInt();
        boolean vehicleRemoved = false;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                dealership.removeVehicle(vehicle);
                vehicleRemoved = true;
                System.out.println("Vehicle removed successfully!");
                break;
            }
        }
        if (!vehicleRemoved) {
            System.out.println("Vehicle not found.");
        }
    }


}

