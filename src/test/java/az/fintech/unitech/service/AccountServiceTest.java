package az.fintech.unitech.service;

import az.fintech.unitech.dto.request.AccountListRequest;
import az.fintech.unitech.dto.request.AccountToAccountRequest;
import az.fintech.unitech.dto.request.AddAccountRequest;
import az.fintech.unitech.dto.response.*;
import az.fintech.unitech.entity.AccountEntity;
import az.fintech.unitech.exception.AccountToAccountException;
import az.fintech.unitech.repository.AccountRepository;
import az.fintech.unitech.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountServiceTest {
    private AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private CustomerService customerService = Mockito.mock(CustomerService.class);
    private PaymentService paymentService = Mockito.mock(PaymentService.class);

    @Spy
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void testAddAccount() {
        when(accountRepository.save(any(AccountEntity.class)))
                .thenReturn(fakeAddAccountEntity());
        AddAccountResponse addAccountResponse = accountService.addAccount(fakeAddAccountRequest());
        Assert.assertEquals(addAccountResponse.getId(), Long.valueOf(12));
    }

    @Test
    public void testGetAllAccountListByCustomerId() {
        CustomerResponse response = fakeCustomerResponse();
        when(customerService.getCustomerById(any())).thenReturn(response);
        when(accountRepository.findByCif(response.getCif())).thenReturn(fakeAccountList());
        List<AccountResponse> list = accountService.getAccountListByCustomerId(fakeAccountListRequest());
        Assert.assertEquals(list.size(), fakeAccountList().size());
    }

    @Test
    public void testSuccessCaseForAccountToAccount() {
        AccountToAccountRequest request = fakeAccountToAccountRequest();
        when(accountRepository.findByAccountNumber(12345L)).thenReturn(fakeToAccountEntity());
        when(accountRepository.findByAccountNumber(67890L)).thenReturn(fakeFromAccountEntity());
        when(paymentService.savePayment(any())).thenReturn(fakeSavePaymentResponse());
        AccountToAccountResponse response = accountService.accountToAccount(request);
        Assert.assertEquals(response.getPaymentId(), fakeSavePaymentResponse().getPaymentId());
        Assert.assertEquals(response.getResponse(), "TRANSFER_PROCESS_IS_SUCCESS");
    }

    @Test
    public void testFailedNotEnoughMoneyCaseForAccountToAccount() {
        AccountToAccountRequest request = fakeAccountToAccountNotEnoughMoneyRequest();
        when(accountRepository.findByAccountNumber(67890L)).thenReturn(fakeFromAccountEntity());
        AccountToAccountException accountToAccountException = assertThrows(AccountToAccountException.class,
                () -> accountService.accountToAccount(request));
        Assert.assertEquals("THERE_IS_NOT_ENOUGH_MONEY_YOUR_ACCOUNT", accountToAccountException.getMessage());
    }

    @Test
    public void testFailedSameAccountNumberCaseForAccountToAccount() {
        AccountToAccountRequest request = fakeAccountToAccountSameAccountRequest();
        when(accountRepository.findByAccountNumber(12345L)).thenReturn(fakeFromAccountEntity());
        AccountToAccountException accountToAccountException = assertThrows(AccountToAccountException.class,
                () -> accountService.accountToAccount(request));
        Assert.assertEquals("TRANSFER_DOES_NOT_ALLOWED_BETWEEN_SAME_ACCOUNT", accountToAccountException.getMessage());
    }

    @Test
    public void testFailedNonExistingCaseForAccountToAccount() {
        AccountToAccountRequest request = fakeAccountToAccountRequest();
        when(accountRepository.findByAccountNumber(12345L)).thenReturn(null);
        when(accountRepository.findByAccountNumber(67890L)).thenReturn(fakeFromAccountEntity());
        AccountToAccountException accountToAccountException = assertThrows(AccountToAccountException.class,
                () -> accountService.accountToAccount(request));
        Assert.assertEquals("TRANSFER_DOES_NOT_ALLOWED_TO_SEND_NON_EXISTING_ACCOUNT", accountToAccountException.getMessage());
    }

    @Test
    public void testFailedClosedAccountCaseForAccountToAccount() {
        AccountToAccountRequest request = fakeAccountToAccountRequest();
        when(accountRepository.findByAccountNumber(12345L)).thenReturn(fakeClosedAccountEntity());
        when(accountRepository.findByAccountNumber(67890L)).thenReturn(fakeFromAccountEntity());
        AccountToAccountException accountToAccountException = assertThrows(AccountToAccountException.class,
                () -> accountService.accountToAccount(request));
        Assert.assertEquals("TRANSFER_DOES_NOT_ALLOWED_TO_SEND_CLOSED_ACCOUNT", accountToAccountException.getMessage());
    }

    private AccountEntity fakeClosedAccountEntity() {
        AccountEntity entity = new AccountEntity();
        entity.setId(2L);
        entity.setBalance(BigDecimal.valueOf(150));
        entity.setStatus("CLOSE");
        return entity;
    }

    private AccountToAccountRequest fakeAccountToAccountSameAccountRequest() {
        AccountToAccountRequest request = new AccountToAccountRequest();
        request.setAmount(BigDecimal.valueOf(200));
        request.setToAccountNumber(12345L);
        request.setFromAccountNumber(12345L);
        return request;
    }

    private AccountToAccountRequest fakeAccountToAccountNotEnoughMoneyRequest() {
        AccountToAccountRequest request = new AccountToAccountRequest();
        request.setAmount(BigDecimal.valueOf(200000));
        request.setToAccountNumber(12345L);
        request.setFromAccountNumber(67890L);
        return request;
    }

    private PaymentResponse fakeSavePaymentResponse() {
        return PaymentResponse.builder()
                .paymentId(112L)
                .build();
    }

    private AccountEntity fakeFromAccountEntity() {
        AccountEntity entity = new AccountEntity();
        entity.setBalance(BigDecimal.valueOf(10000));
        return entity;
    }

    private AccountEntity fakeToAccountEntity() {
        AccountEntity entity = new AccountEntity();
        entity.setTaxId("TAX");
        return entity;
    }

    private AccountToAccountRequest fakeAccountToAccountRequest() {
        AccountToAccountRequest request = new AccountToAccountRequest();
        request.setAmount(BigDecimal.valueOf(100));
        request.setToAccountNumber(12345L);
        request.setFromAccountNumber(67890L);
        return request;
    }

    private AccountListRequest fakeAccountListRequest() {
        AccountListRequest request = new AccountListRequest();
        request.setCustomerId(1111L);
        return request;
    }

    private List<AccountEntity> fakeAccountList() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCif(123456L);
        accountEntity.setTaxId("TAX");
        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.setCif(123456L);
        accountEntity2.setTaxId("TAX-2");
        return Arrays.asList(accountEntity, accountEntity2);
    }

    private CustomerResponse fakeCustomerResponse() {
        CustomerResponse response = new CustomerResponse();
        response.setCif(123456L);
        return response;
    }

    private AccountEntity fakeAddAccountEntity() {
        AccountEntity entity = new AccountEntity();
        entity.setAccountNumber(123456L);
        entity.setCif(11111L);
        entity.setId(12L);
        entity.setBalance(BigDecimal.valueOf(1000));
        return entity;
    }

    private AddAccountRequest fakeAddAccountRequest() {
        AddAccountRequest request = new AddAccountRequest();
        request.setAccountNumber(123456L);
        request.setCif(11111L);
        request.setBalance(BigDecimal.valueOf(1000));
        return request;
    }
}
