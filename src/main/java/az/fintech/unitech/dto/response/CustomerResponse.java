package az.fintech.unitech.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerResponse {
    private String name;
    private String surname;
    private String pin;
    private Long cif;
    private String taxId;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate birthDate;
}
