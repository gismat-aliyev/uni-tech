package az.fintech.unitech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeResponse {
    @JsonProperty("conversion_rates")
    private Map<String, BigDecimal> conversionRates;
}
