package az.fintech.unitech.controller;

import az.fintech.unitech.dto.request.ExchangeCurrencyRequest;
import az.fintech.unitech.dto.response.ExchangeCurrencyResponse;
import az.fintech.unitech.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("name-list")
    public ResponseEntity<Map<String, String>> getCurrencyNameList() {
        return ResponseEntity.ok(currencyService.getCurrencyNameList());
    }

    @PostMapping("exchange")
    public ResponseEntity<ExchangeCurrencyResponse> exchangeCurrency(@RequestBody ExchangeCurrencyRequest request) {
        return ResponseEntity.ok(currencyService.exchangeCurrency(request));
    }
}
