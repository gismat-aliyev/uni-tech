package az.fintech.unitech.service.impl;

import az.fintech.unitech.dto.request.PaymentRequest;
import az.fintech.unitech.dto.response.PaymentResponse;
import az.fintech.unitech.mapper.DtoToEntity;
import az.fintech.unitech.repository.PaymentRepository;
import az.fintech.unitech.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse savePayment(PaymentRequest request) {
        return PaymentResponse.builder()
                .paymentId(paymentRepository.save(DtoToEntity.INSTANCE.toEntity(request)).getId())
                .build();
    }

}
