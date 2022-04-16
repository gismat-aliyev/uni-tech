package az.fintech.unitech.exception;

import az.fintech.unitech.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {LoginException.class})
    public ResponseEntity<LoginResponse> loginException(LoginException ex) {
        return new ResponseEntity<>(LoginResponse.builder()
                .errorResponse(CommonErrorResponse.builder()
                        .errorMessage(ex.getMessage())
                        .errorCode(HttpStatus.NOT_FOUND.value())
                        .build())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RegisterException.class})
    public ResponseEntity<RegisterResponse> registerException(RegisterException ex) {
        return new ResponseEntity<>(RegisterResponse.builder()
                .errorResponse(CommonErrorResponse.builder()
                        .errorMessage(ex.getMessage())
                        .errorCode(CONFLICT.value())
                        .build())
                .build(), CONFLICT);
    }

    @ExceptionHandler(value = {AccountToAccountException.class})
    public ResponseEntity<AccountToAccountResponse> accountToAccountException(AccountToAccountException ex) {
        return new ResponseEntity<>(AccountToAccountResponse.builder()
                .errorResponse(CommonErrorResponse.builder()
                        .errorMessage(ex.getMessage())
                        .errorCode(CONFLICT.value())
                        .build())
                .build(), CONFLICT);
    }

    @ExceptionHandler(value = {CurrencyException.class})
    public ResponseEntity<ExchangeCurrencyResponse> currencyException(CurrencyException ex) {
        return new ResponseEntity<>(ExchangeCurrencyResponse.builder()
                .errorResponse(CommonErrorResponse.builder()
                        .errorMessage(ex.getMessage())
                        .errorCode(CONFLICT.value())
                        .build())
                .build(), CONFLICT);
    }
}
