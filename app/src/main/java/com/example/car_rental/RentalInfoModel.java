package com.example.car_rental;

public class RentalInfoModel {


    public RentalInfoModel(String customerId, String name, String remBal) {
        this.customerId = customerId;
        this.name = name;
        this.remBal = remBal;
    }

    private String customerId;
    private String name;
    private String remBal;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemainingBalance() {
        return remBal;
    }

    public void setRemainingBalance(String remBal) {
        this.remBal = remBal;
    }


}
