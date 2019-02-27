package com.sample.customer;

import com.sample.integration.crm.Customer;
import com.sample.library.exception.ResourceConflictException;
import com.sample.library.exception.ResourceNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class CustomerController {
    private static final String HAL = "application/hal+json";

    private final CustomerService customerService;
    private final CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    public CustomerController(final CustomerService customerService, CustomerResourceAssembler customerResourceAssembler) {
        this.customerService = customerService;
        this.customerResourceAssembler = customerResourceAssembler;
    }

    @ApiOperation("Get all customers (paged format)")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "UnAuthorized")
    })
    @GetMapping(value = "/customers", produces = HAL)
    @ResponseStatus(OK)
    public PagedResources<CustomerResource> get(final Pageable pageable,
                                                         final PagedResourcesAssembler<Customer> customerResourcePagedResourcesAssembler) {
        final Page<Customer> customers = customerService.getCustomers(pageable);
        return customerResourcePagedResourcesAssembler.toResource(customers, customerResourceAssembler);
    }


    @ApiOperation("Add new customer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "UnAuthorized"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @PostMapping(value = "/customers")
    @ResponseStatus(CREATED)
    public void add(@Valid @RequestBody final Customer customer) throws ResourceConflictException {
        customerService.addCustomer(customer);
    }

    @ApiOperation("Get customers by user id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "UnAuthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/customer/{userId}", produces = HAL)
    @ResponseStatus(OK)
    public CustomerResource get(@PathVariable final String userId) throws ResourceNotFoundException {
        final Customer customer = customerService.getCustomer(userId);
        return customerResourceAssembler.toResource(customer);
    }

    @ApiOperation("Get customers by user id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "UnAuthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PutMapping(value = "/customer/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable final String userId, @Valid @RequestBody final Customer customer) throws ResourceNotFoundException {
        customerService.updateCustomer(userId, customer);
    }

    @ApiOperation("Delete existing customer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "UnAuthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @DeleteMapping(value = "/customer/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void delete(final String userId) throws ResourceNotFoundException {
        customerService.deleteCustomer(userId);
    }
}
