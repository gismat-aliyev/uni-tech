package az.fintech.unitech.service;

import az.fintech.unitech.dto.response.CustomerResponse;
import az.fintech.unitech.entity.CustomerEntity;
import az.fintech.unitech.repository.CustomerRepository;
import az.fintech.unitech.service.impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {
    private CustomerRepository repository = Mockito.mock(CustomerRepository.class);

    @Spy
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void testGetAllService() {
        when(repository.findByStatus(1)).thenReturn(fakeEntityList());
        List<CustomerResponse> allCustomers = customerService.getAllCustomers();
        Assert.assertEquals(allCustomers.size(), fakeEntityList().size());
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        when(repository.findById(customerId)).thenReturn(fakeEntityById());
        CustomerResponse customer = customerService.getCustomerById(customerId);
        Assert.assertEquals("PIN", customer.getPin());
        Assert.assertEquals(Long.valueOf(12345), customer.getCif());
    }

    private Optional<CustomerEntity> fakeEntityById() {
        CustomerEntity entity = new CustomerEntity();
        entity.setPin("PIN");
        entity.setCif(12345L);
        entity.setId(1L);
        return Optional.of(entity);
    }

    private List<CustomerEntity> fakeEntityList() {
        CustomerEntity customerEntity1 = new CustomerEntity();
        customerEntity1.setPin("PIN-1");
        customerEntity1.setName("NAME-1");

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setPin("PIN-2");
        customerEntity2.setName("NAME-2");

        return Arrays.asList(customerEntity1, customerEntity2);
    }
}
