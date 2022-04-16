package az.fintech.unitech.service.impl;

import az.fintech.unitech.dto.request.AccountListRequest;
import az.fintech.unitech.dto.request.AccountToAccountRequest;
import az.fintech.unitech.dto.request.AddAccountRequest;
import az.fintech.unitech.dto.request.PaymentRequest;
import az.fintech.unitech.dto.response.*;
import az.fintech.unitech.entity.AccountEntity;
import az.fintech.unitech.enums.ExceptionMessageEnum;
import az.fintech.unitech.enums.SuccessMessageEnum;
import az.fintech.unitech.exception.AccountToAccountException;
import az.fintech.unitech.mapper.DtoToEntity;
import az.fintech.unitech.mapper.EntityToDto;
import az.fintech.unitech.repository.AccountRepository;
import az.fintech.unitech.service.AccountService;
import az.fintech.unitech.service.CustomerService;
import az.fintech.unitech.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static az.fintech.unitech.enums.ExceptionMessageEnum.*;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final PaymentService paymentService;

    @Override
    public AddAccountResponse addAccount(AddAccountRequest request) {
        return EntityToDto.INSTANCE.toAddDto(accountRepository.save(DtoToEntity.INSTANCE.toEntity(request)));
    }

    @Override
    public List<AccountResponse> getAccountListByCustomerId(AccountListRequest request) {
        CustomerResponse customer = customerService.getCustomerById(request.getCustomerId());
        return accountRepository.findByCif(customer.getCif()).stream().map(EntityToDto.INSTANCE::toDto)
                .filter(el -> el.getStatus().equals("OPEN")).collect(Collectors.toList());
    }

    @Override
    public AccountToAccountResponse accountToAccount(AccountToAccountRequest request) {
        AccountEntity toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber());
        if (accountRepository.findByAccountNumber(request.getFromAccountNumber()).getBalance().compareTo(request.getAmount()) <= 0)
            throw new AccountToAccountException(THERE_IS_NOT_ENOUGH_MONEY_YOUR_ACCOUNT.name());
        if (request.getFromAccountNumber().equals(request.getToAccountNumber()))
            throw new AccountToAccountException(TRANSFER_DOES_NOT_ALLOWED_BETWEEN_SAME_ACCOUNT.name());
        if (isNull(toAccount))
            throw new AccountToAccountException(TRANSFER_DOES_NOT_ALLOWED_TO_SEND_NON_EXISTING_ACCOUNT.name());
        if (toAccount.getStatus().equals("CLOSE"))
            throw new AccountToAccountException(TRANSFER_DOES_NOT_ALLOWED_TO_SEND_CLOSED_ACCOUNT.name());

        PaymentResponse paymentResponse = paymentService.savePayment(PaymentRequest.builder()
                .fromAccountNumber(request.getFromAccountNumber())
                .toAccountNumber(request.getToAccountNumber())
                .amount(request.getAmount())
                .build());

        return AccountToAccountResponse.builder()
                .paymentId(paymentResponse.getPaymentId())
                .response(SuccessMessageEnum.TRANSFER_PROCESS_IS_SUCCESS.name())
                .build();
    }
}
