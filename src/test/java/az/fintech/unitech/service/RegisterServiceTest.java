package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.RegisterRequest;
import az.fintech.unitech.dto.response.RegisterResponse;
import az.fintech.unitech.entity.CustomerEntity;
import az.fintech.unitech.exception.LoginException;
import az.fintech.unitech.exception.RegisterException;
import az.fintech.unitech.repository.CustomerRepository;
import az.fintech.unitech.service.impl.RegisterServiceImpl;
import org.junit.Assert;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RegisterServiceTest {
    private CustomerRepository repository = Mockito.mock(CustomerRepository.class);

    @Spy
    @InjectMocks
    private RegisterServiceImpl registerService;

    @Test
    public void successCase() {
        when(repository.findByPin(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(CustomerEntity.class))).thenReturn(fakeEntityForSave());
        RegisterResponse response = registerService.registerCustomer(fakeRegisterRequest());
        Assert.assertEquals(response.getResponse(), "REGISTER_PROCESS_IS_SUCCESS");
    }

    @Test
    public void failedCase() {
        when(repository.findByPin(anyString())).thenReturn(fakeEntityByPin());
        RegisterException registerException = assertThrows(RegisterException.class,
                () -> registerService.registerCustomer(fakeRegisterRequest()));
        assertEquals("PIN_ALREADY_REGISTERED", registerException.getMessage());
    }

    private CustomerEntity fakeEntityForSave() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(123L);
        return entity;
    }

    private Optional<CustomerEntity> fakeEntityByPin() {
        CustomerEntity entity = new CustomerEntity();
        entity.setPin("PIN");
        return Optional.of(entity);
    }

    private RegisterRequest fakeRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setPin("PIN");
        request.setDetails(new RegisterRequest.CustomerSignUpDetails());
        return request;
    }
}
