package az.fintech.unitech.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddAccountRequest {
    private Long cif;
    private Long accountNumber;
    private String currency;
    private String iban;
    private String bankSwift;
    private String bankCorAccount;
    private String bankName;
    private String taxId;
    private String bankAddress;
    private BigDecimal balance;
}
