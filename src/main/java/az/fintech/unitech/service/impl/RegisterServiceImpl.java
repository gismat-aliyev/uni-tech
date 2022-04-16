package az.fintech.unitech.service.impl;

import az.fintech.unitech.dto.request.RegisterRequest;
import az.fintech.unitech.dto.response.RegisterResponse;
import az.fintech.unitech.exception.RegisterException;
import az.fintech.unitech.mapper.DtoToEntity;
import az.fintech.unitech.repository.CustomerRepository;
import az.fintech.unitech.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.fintech.unitech.enums.ExceptionMessageEnum.PIN_ALREADY_REGISTERED;
import static az.fintech.unitech.enums.SuccessMessageEnum.REGISTER_PROCESS_IS_SUCCESS;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final CustomerRepository repository;

    @Override
    public RegisterResponse registerCustomer(RegisterRequest request) {
        if (repository.findByPin(request.getPin()).isPresent())
            throw new RegisterException(PIN_ALREADY_REGISTERED.name());
        return RegisterResponse.builder()
                .customerId(repository.save(DtoToEntity.toEntity(request)).getId())
                .response(REGISTER_PROCESS_IS_SUCCESS.name())
                .build();
    }
}
