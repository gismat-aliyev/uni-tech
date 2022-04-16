package az.fintech.unitech.service;

import az.fintech.unitech.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long customerId);
}
