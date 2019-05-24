package com.baig.and.directory.repository;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findCustomer(Long customerId);
}
