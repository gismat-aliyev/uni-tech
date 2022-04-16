package az.fintech.unitech.service.impl;

import az.fintech.unitech.dto.response.CustomerResponse;
import az.fintech.unitech.mapper.EntityToDto;
import az.fintech.unitech.repository.CustomerRepository;
import az.fintech.unitech.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return repository.findByStatus(1).stream().map(EntityToDto.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        return EntityToDto.INSTANCE.toDto(repository.findById(customerId).orElse(null));
    }
}
