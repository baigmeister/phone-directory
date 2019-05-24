package com.baig.and.directory.service;

import com.baig.and.directory.PhoneTest;
import com.baig.and.directory.repository.Customer;
import com.baig.and.directory.repository.CustomerRepository;
import com.baig.and.directory.repository.Phone;
import com.baig.and.directory.repository.PhoneRepository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneServiceTest extends PhoneTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PhoneService phoneService;

    @Test
    public void testGetAllPhoneNumbers() {
        List<Phone> phoneList = getAllPhoneList();
        when(phoneRepository.findAll()).thenReturn (Optional.of(phoneList));

        List<String> phoneNumberList = phoneService.getAllPhoneNumbers();
        assertNotNull(phoneNumberList);
        assertThat(phoneList.get(0).getPhoneNumber(), is("000 000 0000"));
        assertThat(phoneList.get(1).getPhoneNumber(), is("111 111 1111"));
    }

    @Test
    public void testGetAllPhoneNumbersForNoPhoneNumbers() {
        when(phoneRepository.findAll()).thenReturn(Optional.empty());
        List<String> phoneNumberList = phoneService.getAllPhoneNumbers();
        assertTrue(phoneNumberList.size() == 0);
    }

    @Test
    public void testValidGetCustomerPhoneNumber() {
        Customer customer = createCustomer(1l, "firstName", "lastName");
        createCustomerPhoneNumbers(customer);

        Optional<Customer> customerOptional = Optional.of(customer);
        when(customerRepository.findCustomer(1l)).thenReturn(customerOptional);

        List<String> phoneNumberList = phoneService.getCustomerPhoneNumbers(1l);
        assertNotNull(phoneNumberList);
        assertTrue(phoneNumberList.size() == 2);
    }



    @Test
    public void testGetCustomerPhoneNumberForNullCustomer() {
        when(customerRepository.findCustomer(1l)).thenReturn(Optional.empty());
        List<String> phoneNumberList = phoneService.getCustomerPhoneNumbers(1l);

        assertNotNull(phoneNumberList);
        assertTrue(phoneNumberList.size() == 0);
    }

    @Test
    public void testGetCustomerPhoneNumberForCustomerWithNoNumbers() {
        Optional<Customer> optionalCustomer = Optional.of(createCustomer(1l, "firstName", "lastName"));
        when(customerRepository.findCustomer(1l)).thenReturn(optionalCustomer);

        List<String> phoneNumberList = phoneService.getCustomerPhoneNumbers(1l);

        assertNotNull(phoneNumberList);
        assertTrue(phoneNumberList.size() == 0);
    }

    @Test
    public void testActivatePhoneNumberValid() throws Exception {
        Customer customer = new Customer(1l, "firstName", "lastName");
        List<Phone> phoneList = new ArrayList<>();
        Phone phone1 = createPhone("000 000 0000", customer, false );
        Collections.addAll(phoneList, phone1, createPhone("111 111 1111", customer,true));
        customer.setPhoneList(Optional.of(phoneList));

        when(phoneRepository.findAll()).thenReturn(Optional.of(phoneList));
        when(phoneRepository.save(Optional.of(phone1))).thenReturn(true);

        assertTrue(phoneService.activatePhoneNumber("000 000 0000"));
    }

    //TODO Unnecessary stubbing to fix
    @Test(expected = PhoneActivationException.class)
    @Ignore
    public void testActivatePhoneNumberAlreadyActivePhoneNumber () throws Exception {
        Customer customer = createCustomer(1l, "firstName", "lastName");
        List<Phone> phoneList = new ArrayList<>();
        Phone phone1 = createPhone("000 000 0000", customer, false );
        Collections.addAll(phoneList, phone1, createPhone("111 111 1111", customer, true));
        customer.setPhoneList(Optional.of(phoneList));

        when(phoneRepository.findAll()).thenReturn(Optional.of(phoneList));
        when(phoneRepository.save(Optional.of(phone1))).thenReturn(true);

        phoneService.activatePhoneNumber("111 111 1111");
    }

    @Test(expected = PhoneActivationException.class)
    public void testActivatePhoneNumberForFailedSaveOnDB () throws Exception {
        Customer customer = createCustomer(1l, "firstName", "lastName");
        List<Phone> phoneList = new ArrayList<>();
        Phone phone1 = createPhone("000 000 0000", customer, false );
        Collections.addAll(phoneList, phone1, createPhone("111 111 1111", customer, true));
        customer.setPhoneList(Optional.of(phoneList));

        when(phoneRepository.findAll()).thenReturn(Optional.of(phoneList));
        when(phoneRepository.save(Optional.of(phone1))).thenThrow(new PhoneActivationException(""));

        phoneService.activatePhoneNumber("000 000 0000");
    }

    @Test(expected = PhoneActivationException.class)
    public void testActivatePhoneNumberForNoFindAllPhoneNumbers () throws Exception {
        when(phoneRepository.findAll()).thenReturn(Optional.empty());

        phoneService.activatePhoneNumber("111 111 1111");
    }
}
