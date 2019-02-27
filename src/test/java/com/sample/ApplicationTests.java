package com.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.customer.CustomerResource;
import com.sample.integration.crm.CRMService;
import com.sample.integration.crm.Customer;
import com.sample.library.exception.ApiError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ApplicationTests {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CRMService crmService;

    private final String URI_CUSTOMER_RESOURCE = "/customer/userId";
    private final String URI_CUSTOMERS_RESOURCE = "/customers";
    private final Customer customer = prepareCustomer();

    @Test
    public void getCustomers_emptyList() {
        given(crmService.getCustomers(any(Pageable.class))).willReturn(Page.empty());

        this.webClient.get().uri(URI_CUSTOMERS_RESOURCE).exchange()
                .expectStatus().isOk()
                .expectBody(PagedResources.class)
                .consumeWith(result -> assertThat(result.getResponseBody().getMetadata().getTotalElements()).isEqualTo(0));
    }

    @Test
    public void getCustomers_with_customers() {
        final Page<Customer> page = new PageImpl<Customer>(Collections.singletonList(customer));
        given(crmService.getCustomers(any(Pageable.class))).willReturn(page);

        this.webClient.get().uri(URI_CUSTOMERS_RESOURCE).exchange()
                .expectStatus().isOk()
                .expectBody(PagedResources.class)
                .consumeWith(result -> {
                    assertThat(result.getResponseBody().getMetadata().getTotalElements()).isEqualTo(1);
                    assertThat(result.getResponseBody().getContent().contains(customer));
                });
    }

    @Test
    public void getCustomer_success() {
        given(crmService.getCustomer(anyString())).willReturn(Optional.of(customer));

        this.webClient.get().uri(URI_CUSTOMER_RESOURCE).exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResource.class)
                .consumeWith(result -> assertThat(result.getResponseBody().getContent()).isEqualToComparingFieldByField(customer));
    }

    @Test
    public void getCustomer_NotFound() {
        given(crmService.getCustomer(anyString())).willReturn(Optional.empty());

        this.webClient.get().uri(URI_CUSTOMER_RESOURCE).exchange()
                .expectStatus().isNotFound()
                .expectBody(ApiError.class)
                .consumeWith(result -> assertThat(result.getResponseBody().getStatus()).isEqualTo(NOT_FOUND.value()));
    }

    @Test
    public void addCustomer_success() {
        given(crmService.getCustomer(anyString())).willReturn(Optional.empty());
 // TODO
//        this.webClient.post().uri(URI_CUSTOMER_RESOURCE)
//                .body()
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody().isEmpty();
    }

    @Test
    public void addCustomer_conflict() {
     //TODO
    }

    @Test
    public void deleteCustomer_success() {
        given(crmService.getCustomer(anyString())).willReturn(Optional.of(customer));

        this.webClient.delete().uri(URI_CUSTOMER_RESOURCE)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    @Test
    public void deleteCustomer_NotFound() {
        given(crmService.getCustomer(anyString())).willReturn(Optional.empty());

        this.webClient.delete().uri(URI_CUSTOMER_RESOURCE)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    private Customer prepareCustomer() {
        Customer customer = new Customer();
        customer.setUserId("userId");
        customer.setDateOfBirth(LocalDate.of(1950, 10, 12));
        customer.setEmail("hello@email.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setResidentialAddress("10 dont know ave sydney");
        customer.setResidentialPhone("0232432345");
        customer.setWorkAddress("10 unknowst sydney");
        customer.setWorkPhone("023878985");
        return customer;
    }

    private String customerJson() {
        try {
            return new ObjectMapper().writeValueAsString(prepareCustomer());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
