package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.ExchangeCurrencyRequest;
import az.fintech.unitech.dto.response.ExchangeCurrencyResponse;

import java.util.Map;

public interface CurrencyService {
    Map<String, String> getCurrencyNameList();

    ExchangeCurrencyResponse exchangeCurrency(ExchangeCurrencyRequest request);
}
