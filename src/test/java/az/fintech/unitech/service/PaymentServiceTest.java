package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.PaymentRequest;
import az.fintech.unitech.dto.response.PaymentResponse;
import az.fintech.unitech.entity.PaymentEntity;
import az.fintech.unitech.repository.PaymentRepository;
import az.fintech.unitech.service.impl.PaymentServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {
    private PaymentRepository repository = Mockito.mock(PaymentRepository.class);

    @Spy
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void testSavePayment() {
        when(repository.save(any(PaymentEntity.class))).thenReturn(fakeEntity());
        PaymentResponse paymentResponse = paymentService.savePayment(fakePaymentRequest());
        Assert.assertEquals(paymentResponse.getPaymentId(), fakeEntity().getId());
    }

    private PaymentEntity fakeEntity() {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(112L);
        entity.setAmount(BigDecimal.valueOf(150));
        return entity;
    }

    private PaymentRequest fakePaymentRequest() {
        return PaymentRequest.builder()
                .toAccountNumber(2345L)
                .fromAccountNumber(1234L)
                .amount(BigDecimal.valueOf(150))
                .build();
    }
}
