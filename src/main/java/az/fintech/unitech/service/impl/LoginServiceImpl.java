package az.fintech.unitech.service.impl;

import az.fintech.unitech.dto.request.LoginRequest;
import az.fintech.unitech.dto.response.LoginResponse;
import az.fintech.unitech.enums.ExceptionMessageEnum;
import az.fintech.unitech.exception.LoginException;
import az.fintech.unitech.enums.SuccessMessageEnum;
import az.fintech.unitech.repository.CustomerRepository;
import az.fintech.unitech.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final CustomerRepository customerRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        return LoginResponse.builder()
                .customerId(customerRepository.findByPinAndPassword(request.getPin(), request.getPassword())
                        .orElseThrow(() -> new LoginException(ExceptionMessageEnum.PIN_OR_PASSWORD_WRONG_PLEASE_CHECK_AND_RETRY.name())).getId())
                .result(SuccessMessageEnum.LOGIN_IS_SUCCESS.name())
                .build();
    }
}
