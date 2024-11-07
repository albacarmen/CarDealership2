package com.pluralsight.Contracts;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ContractFileManager {

    private static final String FILE_NAME = "contracts.csv";


    public void saveContracts(List<Contract> contracts) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(contracts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load
    public List<Contract> loadContracts() {
        List<Contract> contracts = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contracts = (List<Contract>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contracts != null ? contracts : new ArrayList<>();
    }

    // filter contracts, streamlined
    public List<Contract> filterContractsByPrice(double minPrice) {
        List<Contract> contracts = loadContracts();
        return contracts.stream()
                .filter(contract -> contract.getTotalPrice() >= minPrice)
                .collect(Collectors.toList());
    }

    // sort contracts by total price using streams
    public List<Contract> sortContractsByTotalPrice() {
        List<Contract> contracts = loadContracts();
        return contracts.stream()
                .sorted(Comparator.comparingDouble(Contract::getTotalPrice))
                .collect(Collectors.toList());
    }


    public double getTotalMonthlyPayments() {
        List<Contract> contracts = loadContracts();
        return contracts.stream()
                .mapToDouble(Contract::getMonthlyPayment)
                .sum();
    }


    public Contract getMostExpensiveContract() {
        List<Contract> contracts = loadContracts();
        return contracts.stream()
                .max(Comparator.comparingDouble(Contract::getTotalPrice))
                .orElse(null); // Return null if no contracts exist
    }

    private static final String FILE_PATH = "contracts.csv";

    // Method to save the contract to a file
    public void saveContract(Contract contract) {
        try (FileWriter writer = new FileWriter(new File(FILE_PATH), true)) {
            writer.write(contract.toString() + "\n");
        } catch (IOException e) {
            System.err.println("Error saving contract: " + e.getMessage());
        }
    }
}

