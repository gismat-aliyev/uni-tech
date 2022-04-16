package az.fintech.unitech.controller;

import az.fintech.unitech.dto.request.AccountListRequest;
import az.fintech.unitech.dto.request.AccountToAccountRequest;
import az.fintech.unitech.dto.request.AddAccountRequest;
import az.fintech.unitech.dto.response.AccountResponse;
import az.fintech.unitech.dto.response.AccountToAccountResponse;
import az.fintech.unitech.dto.response.AddAccountResponse;
import az.fintech.unitech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("by-customer")
    public ResponseEntity<List<AccountResponse>> getAccountByCustomerId(@RequestBody AccountListRequest request) {
        return ResponseEntity.ok(accountService.getAccountListByCustomerId(request));
    }

    @PostMapping("add")
    public ResponseEntity<AddAccountResponse> addAccount(@RequestBody AddAccountRequest request) {
        return ResponseEntity.ok(accountService.addAccount(request));
    }

    @PostMapping("account-to-account")
    public ResponseEntity<AccountToAccountResponse> accountToAccount(@RequestBody AccountToAccountRequest request) {
        return ResponseEntity.ok(accountService.accountToAccount(request));
    }
}
