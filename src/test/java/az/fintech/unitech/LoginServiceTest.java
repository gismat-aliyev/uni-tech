package az.fintech.unitech;

import az.fintech.unitech.dto.request.LoginRequest;
import az.fintech.unitech.dto.response.LoginResponse;
import az.fintech.unitech.entity.CustomerEntity;
import az.fintech.unitech.repository.CustomerRepository;
import az.fintech.unitech.service.LoginService;
import az.fintech.unitech.service.impl.LoginServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static az.fintech.unitech.util.StringUtils.beautify;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class LoginServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Spy
    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    public void loginSuccessCase() {
        given(customerRepository.findByPinAndPassword("PIN", "PASSWORD"))
                .willReturn(Optional.of(fakeCustomerEntity()));
        LoginResponse loginResponse = loginService.login(fakeLoginRequest());
        Assert.assertEquals(beautify(fakeResponse()), beautify(loginResponse));
    }

    private LoginResponse fakeResponse() {
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
