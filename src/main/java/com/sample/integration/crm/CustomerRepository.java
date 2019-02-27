package com.sample.integration.crm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserId(final String userId);
    Page<Customer> findAll(Pageable pageable);
    void deleteByUserId(final String userId);
}
