package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.LoginRequest;
import az.fintech.unitech.dto.response.LoginResponse;
import az.fintech.unitech.entity.CustomerEntity;
import az.fintech.unitech.exception.LoginException;
import az.fintech.unitech.repository.CustomerRepository;
import az.fintech.unitech.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LoginServiceTest {
    private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);

    @Spy
    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    public void loginSuccessCase() {
        when(customerRepository.findByPinAndPassword(anyString(), anyString()))
                .thenReturn(Optional.of(fakeCustomerEntity()));
        LoginResponse loginResponse = loginService.login(fakeLoginRequest());
        assertEquals(fakeSuccessResponse().getResult(), loginResponse.getResult());
    }

    @Test
    public void failedCase() {
        when(customerRepository.findByPinAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());
        LoginException loginException = assertThrows(LoginException.class,
                () -> loginService.login(fakeLoginRequest()));

        assertEquals("PIN_OR_PASSWORD_WRONG_PLEASE_CHECK_AND_RETRY", loginException.getMessage());
    }

    private LoginResponse fakeSuccessResponse() {
        return LoginResponse.builder()
                .customerId(1L)
                .result("LOGIN_IS_SUCCESS")
                .build();
    }

    private CustomerEntity fakeCustomerEntity() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1L);
        return entity;
    }

    private LoginRequest fakeLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setPassword("PASSWORD");
        request.setPin("PIN");
        return request;
    }


}
