package com.sample.integration.crm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface CRMService {
    void addCustomer(@NotNull final Customer customer);

    Optional<Customer> getCustomer(@NotNull final Long id);

    Optional<Customer> getCustomer(@NotBlank final String userId);

    Page<Customer> getCustomers(final Pageable pageable);

    void deleteCustomer(@NotNull final Long id);

    void deleteCustomer(@NotNull final String userId);

    void updateCustomer(String userId, Customer customer);
}
