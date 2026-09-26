package org.example.qrproject.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.qrproject.Dtos.Request.Account.AccountRequest;
import org.example.qrproject.Dtos.Response.Account.AccountResponse;
import org.example.qrproject.Exception.AppException;
import org.example.qrproject.Exception.ErrorCode;
import org.example.qrproject.Mapper.AccountMapper;
import org.example.qrproject.Module.AccountEntity;
import org.example.qrproject.Module.RoleEntity;
import org.example.qrproject.Repo.AccountRepo;
import org.example.qrproject.Repo.RoleRepo;
import org.example.qrproject.Service.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    RoleRepo roleRepo;
    AccountMapper accountMapper;
    AccountRepo accountRepo;
    PasswordEncoder passwordEncoder;
    @Override
    public AccountResponse createAccount(AccountRequest request) {
        AccountEntity entity=accountMapper.toEntity(request);
        RoleEntity role=roleRepo.findByRoleName(request.getRole())
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        entity.setRole(role);
        entity.setIsDeleted(false);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        accountRepo.save(entity);

        return accountMapper.toResponse(entity);
    }

    @Override
    public AccountEntity getAccountById(String idAccount) {
        AccountEntity account=accountRepo.findById(idAccount)
                .orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return account;
    }

    @Override
    public AccountResponse getAccounResponsetById(String idAccount) {
        return accountMapper.toResponse(getAccountById(idAccount));
    }

    @Override
    public List<AccountResponse> getAllAccount() {
        return accountRepo.findAll().stream()
                .filter(accountEntity -> !accountEntity.getIsDeleted())
                .map(accountMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void lockAccount(String id) {
        AccountEntity account=getAccountById(id);
        account.setIsDeleted(false);
        account.setDeletedAt(LocalDateTime.now());
        accountRepo.save(account);
    }
}
