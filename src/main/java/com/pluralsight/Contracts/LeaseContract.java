package com.pluralsight.Contracts;
package com.pluralsight.Contracts;

import com.pluralsight.Dealership.Vehicle;

public class LeaseContract extends Contract {
    private double expectedEndingValue;
    private double leaseFee;
    private double totalPrice;
    private double monthlyPayment;


    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
        this.expectedEndingValue = getVehicleSold().getPrice() / 2;  
        this.leaseFee = getVehicleSold().getPrice() * 0.07;
    }


    @Override
    public double getTotalPrice() {
        totalPrice = (getVehicleSold().getPrice() - expectedEndingValue) + leaseFee;
        return totalPrice;
    }

    // Method to calculate monthly payments based on lease terms
    @Override
    public double getMonthlyPayment() {
        int numberOfPayments = 36;                 // 3-year lease
        double interestRate = 4.0 / 1200;          // 4% annual interest rate, converted to monthly

        // annuity formula
        monthlyPayment = getTotalPrice() * (interestRate * Math.pow(1 + interestRate, numberOfPayments))
                / (Math.pow(1 + interestRate, numberOfPayments) - 1);


        monthlyPayment = Math.round(monthlyPayment * 100) / 100.0;
        return monthlyPayment;
    }

    // return formatted contract details
    @Override
    public String toString() {
        return "LEASE|" + getDate() + "|" + getCustomerName() + "|" + getCustomerEmail() + "|"
                + getVehicleSold().getVin() + "|" + getVehicleSold().getYear() + "|"
                + getVehicleSold().getMake() + "|" + getVehicleSold().getModel() + "|"
                + getVehicleSold().getVehicleType() + "|" + getVehicleSold().getColor() + "|"
                + getVehicleSold().getOdometer() + "|" + getVehicleSold().getPrice() + "|"
                + expectedEndingValue + "|" + leaseFee + "|" + totalPrice + "|" + monthlyPayment;
    }


    public double getExpectedEndingValue() { return expectedEndingValue; }
    public void setExpectedEndingValue(double expectedEndingValue) { this.expectedEndingValue = expectedEndingValue; }

    public double getLeaseFee() { return leaseFee; }
    public void setLeaseFee(double leaseFee) { this.leaseFee = leaseFee; }
}
