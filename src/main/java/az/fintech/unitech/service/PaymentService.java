package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.PaymentRequest;
import az.fintech.unitech.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse savePayment(PaymentRequest request);
}
