package az.fintech.unitech.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponse {
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
    private final String status = "OPEN";
    private final LocalDateTime accountCreateDate = LocalDateTime.now();
}
