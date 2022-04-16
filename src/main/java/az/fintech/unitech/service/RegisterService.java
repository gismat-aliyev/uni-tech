package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.RegisterRequest;
import az.fintech.unitech.dto.response.RegisterResponse;

public interface RegisterService {
    RegisterResponse registerCustomer(RegisterRequest request);
}
