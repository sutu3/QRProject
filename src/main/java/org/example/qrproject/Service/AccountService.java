package org.example.qrproject.Service;

import org.example.qrproject.Dtos.Request.Account.AccountRequest;
import org.example.qrproject.Dtos.Response.Account.AccountResponse;
import org.example.qrproject.Module.AccountEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    AccountResponse createAccount(AccountRequest request);

    AccountEntity getAccountById(String idAccount);

    AccountResponse getAccounResponsetById(String idAccount);

    List<AccountResponse> getAllAccount();

    void lockAccount(String id);
}
