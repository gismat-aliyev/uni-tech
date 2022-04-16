package az.fintech.unitech.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String pin;
    private String password;
}
