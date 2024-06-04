package tech.study.controlefinanceiro.api.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.study.controlefinanceiro.domain.entity.Customer;
import tech.study.controlefinanceiro.services.CustomerService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        var customerId = this.customerService.saveCustomer(createCustomerDto);
        return ResponseEntity.created(URI.create("/v1/users/"+ customerId.toString())).build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomer() {
        var listCustomer = this.customerService.listCustomer();
        return ResponseEntity.ok(listCustomer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("customerId") String customerId) {
        var customer = this.customerService.getCustomerById(customerId);
        if(customer.isPresent()){
            return ResponseEntity.ok(customer.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteById(@PathVariable("customerId") String customerId) {
        this.customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Void> updateById(@PathVariable("customerId") String customerId, @RequestBody UpdateCustomerDto updateCustomerDto) {
        this.customerService.updateById(customerId, updateCustomerDto);
        return ResponseEntity.noContent().build();
    }

}
