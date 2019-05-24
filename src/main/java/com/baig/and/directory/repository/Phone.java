package com.baig.and.directory.repository;

public class Phone {
    private String phoneNumber;
    private Customer customer;
    private boolean isActivated;

    public Phone(String phoneNumber, Customer customer, boolean isActivated) {
        this.phoneNumber = phoneNumber;
        this.customer = customer;
        this.isActivated = isActivated;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
