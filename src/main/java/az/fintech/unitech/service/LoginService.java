package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.LoginRequest;
import az.fintech.unitech.dto.response.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request);
}
