package com.baig.and.directory;

import com.baig.and.directory.repository.Customer;
import com.baig.and.directory.repository.Phone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PhoneTest {

    protected List<Phone> getAllPhoneList() {
        List<Phone> phoneList = new ArrayList<>();
        Collections.addAll(phoneList,createPhone("000 000 0000", null, true), createPhone("111 111 1111", null, false));
        return phoneList;
    }

    protected Phone createPhone(String phoneNumber, Customer customer, boolean isActivated) {
        return new Phone(phoneNumber, customer, isActivated);
    }

    protected Customer createCustomer(Long customerId, String firstName, String lastName ) {
        return new Customer(customerId, firstName, lastName);
    }

    protected void createCustomerPhoneNumbers(Customer customer) {
        List<Phone> phoneList = new ArrayList<>();
        Collections.addAll(phoneList, createPhone("000 000 0000", customer, true ), createPhone("111 111 1111", customer,true));
        customer.setPhoneList(Optional.of(phoneList));
    }
}
