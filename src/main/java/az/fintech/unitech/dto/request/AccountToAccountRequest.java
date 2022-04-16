package az.fintech.unitech.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountToAccountRequest {
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private BigDecimal amount;
}
