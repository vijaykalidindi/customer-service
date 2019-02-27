package com.sample.customer;

import com.sample.library.exception.ResourceConflictException;
import com.sample.integration.crm.CRMService;
import com.sample.integration.crm.Customer;
import com.sample.library.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Slf4j
class CustomerService {
    private CRMService crmService;

    @Autowired
    CustomerService(final CRMService crmService) {
        this.crmService = crmService;
    }

    void addCustomer(@NotNull final Customer customer) throws ResourceConflictException {
        final Optional<Customer> existing = get(customer.getUserId());
        if (existing.isPresent()) {
            log.error("CustomerExistsAlready", customer.getUserId());
            throw new ResourceConflictException("userId " + customer.getUserId() + " already exists in the system");
        }
        crmService.addCustomer(customer);
        log.info("CustomerAdded", customer);
    }

    Customer getCustomer(@NotBlank final String userId) throws ResourceNotFoundException {
        final Optional<Customer> customer = get(userId);
        if (customer.isPresent())
        {
            log.info("CustomerRetreived", userId);
            return customer.get();
        }
        log.error("CustomerNotFound", userId);
        throw new ResourceNotFoundException("userId " + userId + " not found");
    }

    Page<Customer> getCustomers(final Pageable pageable) {
        final Page<Customer> page = crmService.getCustomers(pageable);
        log.info("CustomersRetrieved", page.getSize());
        return page;
    }

    void updateCustomer(@NotNull final String userId, @NotNull final Customer customer) throws ResourceNotFoundException {
        final Optional<Customer> existing = get(userId);
        if (!existing.isPresent()) {
            log.error("CustomerNotFound", userId);
            throw new ResourceNotFoundException("userId " + userId + " not found");
        }
        crmService.updateCustomer(userId, customer);
        log.info("CustomerDeleted", userId);
    }

    void deleteCustomer(@NotNull final String userId) throws ResourceNotFoundException {
        final Optional<Customer> existing = get(userId);
        if (!existing.isPresent()) {
            log.error("CustomerNotFound", userId);
            throw new ResourceNotFoundException("userId " + userId + " not found");
        }
        crmService.deleteCustomer(userId);
        log.info("CustomerDeleted", userId);
    }

    private Optional<Customer> get(@NotBlank String userId) {
        final Optional<Customer> customer = crmService.getCustomer(userId);
        log.info("CustomerQueried", userId);
        return customer;
    }

}
