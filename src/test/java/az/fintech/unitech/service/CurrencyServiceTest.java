package az.fintech.unitech.service;

import az.fintech.unitech.config.UrlConfiguration;
import az.fintech.unitech.dto.request.ExchangeCurrencyRequest;
import az.fintech.unitech.dto.response.ExchangeCurrencyResponse;
import az.fintech.unitech.dto.response.ExchangeResponse;
import az.fintech.unitech.exception.CurrencyException;
import az.fintech.unitech.exception.LoginException;
import az.fintech.unitech.service.impl.CurrencyServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CurrencyServiceTest {
    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private UrlConfiguration urlConfig = Mockito.mock(UrlConfiguration.class);

    @Spy
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    public void testSuccessCase() {
        when(urlConfig.getCurrencyExchangeUrl()).thenReturn("exchange-url");
        when(currencyService.getCurrencyNameList()).thenReturn((Map) fakeNameResponse().getBody());
        when(restTemplate.getForEntity(anyString(), any()))
                .thenReturn(fakeExchangeResponse());

        ExchangeCurrencyResponse exchangeCurrencyResponse = currencyService.exchangeCurrency(fakeExchangeRequest());
        Assert.assertEquals(exchangeCurrencyResponse.getAfterExchangeAmount(),
                fakeExchangeCurrencyResponse().getAfterExchangeAmount());
    }

    @Test
    public void testFailedCase() {
        when(urlConfig.getCurrencyExchangeUrl()).thenReturn("exchange-url");
        when(currencyService.getCurrencyNameList()).thenReturn(new HashMap());
        when(restTemplate.getForEntity(anyString(), any()))
                .thenReturn(fakeExchangeResponse());
        CurrencyException currencyException = assertThrows(CurrencyException.class,
                () -> currencyService.exchangeCurrency(fakeExchangeRequest()));
        Assert.assertEquals("UN_SUPPORTED_CURRENCY_NAME", currencyException.getMessage());
    }

    @Test
    public void testCurrencyNameList() {
        when(urlConfig.getCurrencyNameListUrl()).thenReturn("name-url");
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(fakeNameList());
        Map currencyNameList = currencyService.getCurrencyNameList();
        Assert.assertEquals(currencyNameList.size(), fakeNameList().size());
    }

    private ExchangeCurrencyResponse fakeExchangeCurrencyResponse() {
        return ExchangeCurrencyResponse.builder()
                .toCurrency("AZN")
                .fromCurrency("USD")
                .amount(BigDecimal.valueOf(10000))
                .afterExchangeAmount(BigDecimal.valueOf(1.7 * 10000))
                .build();
    }

    private Map<String, String> fakeNameList() {
        Map<String, String> map = new HashMap<>();
        map.put("USD", "$");
        map.put("AZN", "azn");
        return map;
    }

    private ResponseEntity<Object> fakeNameResponse() {
        return ResponseEntity.ok(fakeNameList());
    }

    private ResponseEntity<Object> fakeExchangeResponse() {
        ExchangeResponse response = new ExchangeResponse();
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("USD", BigDecimal.valueOf(1));
        map.put("AZN", BigDecimal.valueOf(1.7));
        response.setConversionRates(map);
        return ResponseEntity.ok(response);
    }

    private ExchangeCurrencyRequest fakeExchangeRequest() {
        ExchangeCurrencyRequest request = new ExchangeCurrencyRequest();
        request.setFromCurrency("USD");
        request.setToCurrency("AZN");
        request.setAmount(BigDecimal.valueOf(10000L));
        return request;
    }
}
