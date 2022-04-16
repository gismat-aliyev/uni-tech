package az.fintech.unitech.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String pin;
    private String password;
    private CustomerSignUpDetails details;

    @Data
    public static class CustomerSignUpDetails {
        private String name;
        private String surname;
        private Long cif;
        private String taxId;
        private String email;
        private String address;
        private String phoneNumber;
        private LocalDate birthDate;
    }
}
