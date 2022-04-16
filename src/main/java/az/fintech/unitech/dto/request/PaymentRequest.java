package az.fintech.unitech.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequest {
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private BigDecimal amount;
}
