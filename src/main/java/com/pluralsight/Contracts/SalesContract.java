package com.pluralsight.Contracts;

import com.pluralsight.Dealership.Vehicle;

public class SalesContract extends Contract {
    private static final double SALES_TAX_RATE = 0.05;
    private static final double RECORDING_FEE = 100;
    private double processingFee;
    private boolean isFinance;

    private double totalPrice;
    private double monthlyPayment;


    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean isFinance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.isFinance = isFinance;
        this.processingFee = vehicleSold.getPrice() >= 10000 ? 495 : 295;  // fee based on price
    }


    @Override
    public double getTotalPrice() {
        double salesTaxAmount = getVehicleSold().getPrice() * SALES_TAX_RATE;
        totalPrice = getVehicleSold().getPrice() + salesTaxAmount + RECORDING_FEE + processingFee;
        return totalPrice;
    }


    @Override
    public double getMonthlyPayment() {
        if (!isFinance) return 0.0;

        int numberOfPayments;
        double interestRate;

        if (getVehicleSold().getPrice() >= 10000) {
            numberOfPayments = 48;
            interestRate = 4.25 / 1200;
        } else {
            numberOfPayments = 24;
            interestRate = 5.25 / 1200;
        }

        monthlyPayment = getTotalPrice() * (interestRate * Math.pow(1 + interestRate, numberOfPayments))
                / (Math.pow(1 + interestRate, numberOfPayments) - 1);

        monthlyPayment = Math.round(monthlyPayment * 100) / 100.0;
        return monthlyPayment;
    }


    @Override
    public String toString() {
        return "SALE|" + getDate() + "|" + getCustomerName() + "|" + getCustomerEmail() + "|"
                + getVehicleSold().getVin() + "|" + getVehicleSold().getYear() + "|"
                + getVehicleSold().getMake() + "|" + getVehicleSold().getModel() + "|"
                + getVehicleSold().getVehicleType() + "|" + getVehicleSold().getColor() + "|"
                + getVehicleSold().getOdometer() + "|" + getVehicleSold().getPrice() + "|"
                + (SALES_TAX_RATE * getVehicleSold().getPrice()) + "|" + RECORDING_FEE + "|"
                + processingFee + "|" + totalPrice + "|" + isFinance + "|" + monthlyPayment;
    }

    // Getters and Setters for fields if necessary
    public double getProcessingFee() { return processingFee; }
    public void setProcessingFee(double processingFee) { this.processingFee = processingFee; }
    public boolean isFinance() { return isFinance; }
    public void setFinance(boolean finance) { isFinance = finance; }
}

