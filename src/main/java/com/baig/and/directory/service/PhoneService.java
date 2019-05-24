package com.baig.and.directory.service;

import com.baig.and.directory.repository.Customer;
import com.baig.and.directory.repository.CustomerRepository;
import com.baig.and.directory.repository.Phone;
import com.baig.and.directory.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<String> getAllPhoneNumbers() {
        Optional<List<Phone>> phoneList = phoneRepository.findAll();
        return phoneList.isPresent() ? listPhoneNumbers().apply(phoneList.get()) : new ArrayList<>();
    }

    public List<String> getCustomerPhoneNumbers(Long customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        return customer.isPresent() && customer.get().getPhoneList().isPresent()? listPhoneNumbers().apply(customer.get().getPhoneList().get()) : new ArrayList<>();
    }

    public void setPhoneRepository(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    //@Transactional
    public boolean activatePhoneNumber(String phoneNumber) throws Exception {
        Optional<List<Phone>> phoneList = phoneRepository.findAll();

        Optional<Phone> phone =  phoneList.isPresent() ? phoneList.get().stream().filter(p -> p.getPhoneNumber().equals(phoneNumber)).findFirst(): Optional.empty();
        boolean result = false;
        if(phone.isPresent()) {
            if(!phone.get().isActivated()) {
                phone.get().setActivated(true);
                try {
                    result = phoneRepository.save(phone);
                } catch (Exception e) {
                    throw new PhoneActivationException("Phone number failed to activate" + e);
                }
            } else {
                throw new PhoneActivationException("Phone number has already been activated");
            }
        } else {
            throw new PhoneActivationException("Activation has failed on an invalid Phone number:  " + phoneNumber);
        }
        return result;
    }

    private Function<List<Phone>, List<String>> listPhoneNumbers() {
        return (a) -> a.stream().map((p) -> p.getPhoneNumber()).collect(Collectors.toList());
    }
}
