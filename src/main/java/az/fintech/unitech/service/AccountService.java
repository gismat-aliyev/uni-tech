package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.AccountListRequest;
import az.fintech.unitech.dto.request.AccountToAccountRequest;
import az.fintech.unitech.dto.request.AddAccountRequest;
import az.fintech.unitech.dto.response.AccountResponse;
import az.fintech.unitech.dto.response.AccountToAccountResponse;
import az.fintech.unitech.dto.response.AddAccountResponse;

import java.util.List;

public interface AccountService {
    AddAccountResponse addAccount(AddAccountRequest request);
    List<AccountResponse> getAccountListByCustomerId(AccountListRequest request);
    AccountToAccountResponse accountToAccount(AccountToAccountRequest request);
}
