package az.fintech.unitech.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class UrlConfiguration {
    @Value("${currency.url.exchange}")
    private String currencyExchangeUrl;
    @Value("${currency.url.list}")
    private String currencyNameListUrl;

}
