package tech.study.controlefinanceiro.api.Customer;

import java.time.Instant;

public record CreateCustomerDto(String name, String email, String phone) {
}
