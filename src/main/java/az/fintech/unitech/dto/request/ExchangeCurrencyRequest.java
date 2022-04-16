package az.fintech.unitech.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeCurrencyRequest {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
}
