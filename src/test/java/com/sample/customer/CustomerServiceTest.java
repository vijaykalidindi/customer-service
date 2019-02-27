package com.sample.customer;

import com.sample.integration.crm.CRMService;
import com.sample.integration.crm.Customer;
import com.sample.library.exception.ResourceConflictException;
import com.sample.library.exception.ResourceNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    private CustomerService customerService;
    private CRMService crmService = Mockito.mock(CRMService.class);
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customerService = new CustomerService(crmService);

        customer = new Customer();
        customer.setUserId("userId");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addCustomer_successful() throws ResourceConflictException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.empty());
        willDoNothing().given(crmService).addCustomer(any(Customer.class));

        customerService.addCustomer(customer);
        verify(crmService).addCustomer(customer);
    }

    @Test(expected = ResourceConflictException.class)
    public void addCustomer_throws_conflict() throws ResourceConflictException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.of(customer));

        customerService.addCustomer(customer);
        verify(crmService, times(0)).addCustomer(customer);
    }

    @Test
    public void getCustomer_returns_customer() throws ResourceNotFoundException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.of(customer));
        final Customer actual = customerService.getCustomer("userId");
        assertThat(actual).isEqualToComparingFieldByField(customer);
        verify(crmService).getCustomer("userId");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCustomer_throws_notfound() throws ResourceNotFoundException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.empty());
        customerService.getCustomer("userId");
        verify(crmService, times(0)).getCustomer("userId");
    }

    @Test
    public void getAllCustomers() {
        given(crmService.getCustomers(any(Pageable.class))).willReturn(Page.empty());
        final Pageable unpaged = Pageable.unpaged();
        customerService.getCustomers(unpaged);
        verify(crmService).getCustomers(unpaged);
    }

    @Test
    public void updateCustomer_successful() throws ResourceNotFoundException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.of(customer));
        customerService.updateCustomer("userId", customer);
        verify(crmService).updateCustomer("userId", customer);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCustomer_notfound() throws ResourceNotFoundException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.empty());
        customerService.updateCustomer("userId", customer);
        verify(crmService, times(0)).updateCustomer("userId", customer);
    }

    @Test
    public void deleteCustomer_successful() throws ResourceNotFoundException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.of(customer));
        customerService.deleteCustomer("userId");
        verify(crmService).deleteCustomer("userId");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCustomer_notfound() throws ResourceNotFoundException {
        given(crmService.getCustomer(anyString())).willReturn(Optional.empty());
        customerService.deleteCustomer("userId");
        verify(crmService, times(0)).deleteCustomer("userId");
    }
}