package tech.study.controlefinanceiro.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.study.controlefinanceiro.api.Customer.CreateCustomerDto;
import tech.study.controlefinanceiro.api.Customer.UpdateCustomerDto;
import tech.study.controlefinanceiro.domain.entity.Customer;
import tech.study.controlefinanceiro.persistence.CustomerRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Async
    public List<Customer> listCustomer() {
        var customers = this.customerRepository.findAll();
        return customers;
    }

    @Async
    public Optional<Customer> getCustomerById(String customerId) {
        return this.customerRepository.findById(UUID.fromString(customerId));
    }

    @Async
    public UUID saveCustomer(CreateCustomerDto createCustomerDto){

        var customer = new Customer(UUID.randomUUID(),
                createCustomerDto.name(),
                createCustomerDto.email(),
                createCustomerDto.phone(),
                Instant.now(),
                null);

        var customerSave = this.customerRepository.save(customer);
        return customerSave.getId();
    }

    @Async
    public void deleteCustomerById(String customerId) {
        var existCustomer = this.customerRepository.existsById(UUID.fromString(customerId));
        if (existCustomer) {
            this.customerRepository.deleteById(UUID.fromString(customerId));
        }
    }

    @Async
    public void updateById(String customerId, UpdateCustomerDto updateCustomerDto) {
        var id = UUID.fromString(customerId);

        var customer = this.customerRepository.findById(id);
        if(customer.isPresent()){
            var client = customer.get();
            if(updateCustomerDto.email() != null) {
                client.setEmail(updateCustomerDto.email());
            }

            if(updateCustomerDto.phone() != null) {
                client.setPhone(updateCustomerDto.phone());
            }

            this.customerRepository.save(client);
        }
    }
}
