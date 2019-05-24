package com.baig.and.directory.controller;

import com.baig.and.directory.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.List;

@RestController
public class PhoneDirectoryController {

    @Autowired
    private PhoneService phoneService;

    /**
     * Finds All Phone Numbers in the Directory
     * @return
     */
    @RequestMapping(value = "/findAllPhoneNumbers")
    public ResponseEntity findAllPhoneNumbers() {
        List<String> phoneList;
        try {
            phoneList = phoneService.getAllPhoneNumbers();
        } catch (RestClientException re) {
            return new ResponseEntity(getErrorResponse(re.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity(phoneList, HttpStatus.OK);
    }

    /**
     * Finds all Phone Numbers for a Customer
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/findCustomerPhoneNumbers/{customerId}")
    public ResponseEntity findCustomerPhoneNumbers(@PathVariable Long customerId) {
        List<String> phoneList;
        try {
            //TODO when phoneList is empty - HTTP Status of BAD REQUEST should be sent
            phoneList = phoneService.getCustomerPhoneNumbers(customerId);
        } catch (RestClientException re) {
            return new ResponseEntity(getErrorResponse(re.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity(phoneList, HttpStatus.OK);
    }

    /**
     * Activates a Phone Number
     * @param phoneNumber
     * @return
     */
    @RequestMapping(value = "/activatePhoneNumber/{phoneNumber}")
    public ResponseEntity activatePhoneNumber(@PathVariable String phoneNumber) {
        boolean result = false;
        try {
            result = phoneService.activatePhoneNumber(phoneNumber);
        } catch (RestClientException re) {
            return new ResponseEntity(getErrorResponse(re.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            return new ResponseEntity(getErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Boolean.valueOf(result), HttpStatus.OK);
    }

    private ErrorResponse getErrorResponse(String errorCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorCode);
        return errorResponse;
    }
}
