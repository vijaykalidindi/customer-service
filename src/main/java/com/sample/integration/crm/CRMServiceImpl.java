package com.sample.integration.crm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
class CRMServiceImpl implements CRMService {
    private CustomerRepository customerRepository;

    @Autowired
    CRMServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCustomer(@NotNull final Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomer(@NotNull final Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> getCustomer(@NotBlank final String userId) {
        return customerRepository.findByUserId(userId);
    }

    @Override
    public Page<Customer> getCustomers(final Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public void deleteCustomer(@NotNull final Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void deleteCustomer(@NotNull final String userId) {
        customerRepository.deleteByUserId(userId);
    }

    @Override
    public void updateCustomer(String userId, Customer customer) {
        Optional<Customer> existing = customerRepository.findByUserId(userId);
        if (existing.isPresent()){
            final Customer target = existing.get();
            BeanUtils.copyProperties(customer, target, "id");
            customerRepository.save(target);
        }
    }

}
