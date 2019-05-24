package com.baig.and.directory.controller;

import com.baig.and.directory.PhoneTest;
import com.baig.and.directory.repository.Customer;
import com.baig.and.directory.repository.CustomerRepository;
import com.baig.and.directory.repository.PhoneRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneDirectoryControllerTest extends PhoneTest {

    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private PhoneRepository phoneRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFindAllPhoneNumbers() throws Exception {
        String uri = "/findAllPhoneNumbers";

        when(phoneRepository.findAll()).thenReturn(Optional.of(getAllPhoneList()));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List<String> phoneList = mapFromJson(content, List.class);

        assertTrue(phoneList.size() == 2);
        assertThat(phoneList.get(0), is("000 000 0000"));
        assertThat(phoneList.get(1), is("111 111 1111"));
    }

    @Test
    public void testFindCustomerPhoneNumbers() throws Exception {
        String uri = "/findCustomerPhoneNumbers/1";

        Customer customer = createCustomer(1l, "firstName", "lastName");
        createCustomerPhoneNumbers(customer);
        when(customerRepository.findCustomer(1l)).thenReturn(Optional.of(customer));


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List<String> phoneList = mapFromJson(content, List.class);

        assertTrue(phoneList.size() == 2);
        assertThat(phoneList.get(0), is("000 000 0000"));
        assertThat(phoneList.get(1), is("111 111 1111"));
    }

    @Test
    public void testActivatePhoneNumber() throws Exception {
        String uri = "/activatePhoneNumber/111 111 1111";

        when(phoneRepository.findAll()).thenReturn(Optional.of(getAllPhoneList()));
        when(phoneRepository.save(ArgumentMatchers.any())).thenReturn(true);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Boolean result = mapFromJson(content, Boolean.class);

        assertTrue(result.booleanValue());
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
