package com.sample.customer;

import com.sample.integration.crm.Customer;
import com.sample.library.exception.ResourceNotFoundException;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {
    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResource.class);
    }

    @Override
    public CustomerResource toResource(Customer customer) {
        final CustomerResource customerResource = new CustomerResource(customer);
        try {
            customerResource.add(linkTo(methodOn(CustomerController.class)
                    .get(customer.getUserId())).withSelfRel());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return customerResource;
    }
}
