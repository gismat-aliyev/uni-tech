package az.fintech.unitech.service.impl;

import az.fintech.unitech.config.UrlConfiguration;
import az.fintech.unitech.dto.request.ExchangeCurrencyRequest;
import az.fintech.unitech.dto.response.ExchangeCurrencyResponse;
import az.fintech.unitech.dto.response.ExchangeResponse;
import az.fintech.unitech.enums.ExceptionMessageEnum;
import az.fintech.unitech.exception.CurrencyException;
import az.fintech.unitech.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final RestTemplate restTemplate;
    private final UrlConfiguration urlConfig;

    @Override
    public Map getCurrencyNameList() {
        return restTemplate.getForObject(urlConfig.getCurrencyNameListUrl(), Map.class);
    }

    @Override
    public ExchangeCurrencyResponse exchangeCurrency(ExchangeCurrencyRequest request) {
        Map<String, String> currencyNameMap = getCurrencyNameList();
        if (!currencyNameMap.containsKey(request.getFromCurrency().toUpperCase()) ||
                !currencyNameMap.containsKey(request.getToCurrency().toUpperCase())) {
            throw new CurrencyException(ExceptionMessageEnum.UN_SUPPORTED_CURRENCY_NAME.name());
        }
        ExchangeResponse exchangeResponse = restTemplate
                .getForEntity(urlConfig.getCurrencyExchangeUrl().concat(request.getFromCurrency().toUpperCase()), ExchangeResponse.class).getBody();
        assert exchangeResponse != null;
        BigDecimal toValueRates = exchangeResponse.getConversionRates().get(request.getToCurrency().toUpperCase());
        return ExchangeCurrencyResponse.builder()
                .afterExchangeAmount(toValueRates.multiply(request.getAmount()))
                .amount(request.getAmount())
                .fromCurrency(request.getFromCurrency())
                .toCurrency(request.getToCurrency())
                .build();
    }

}
