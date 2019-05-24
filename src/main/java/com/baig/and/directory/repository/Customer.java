package com.baig.and.directory.repository;

import java.util.List;
import java.util.Optional;

public class Customer {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Optional<List<Phone>> phoneList = Optional.empty();

    public Customer(Long customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Optional<List<Phone>> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(Optional<List<Phone>> phoneList) {
        this.phoneList = phoneList;
    }
}
