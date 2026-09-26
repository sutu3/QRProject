package org.example.qrproject.Dtos.Response.Account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.qrproject.Dtos.Response.Role.RoleResponse;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    String idAccount;

    String accountName;

    String password;

    String userName;

    RoleResponse roleEntity;

}
