package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_account",columnDefinition = "VARCHAR(36) COMMENT 'Id của tài khoản'")
    String idAccount;

    @Column(name = "accountName",columnDefinition = "VARCHAR(255) COMMENT 'tên tài khoản'", nullable = false)
    String accountName;

    @Column(name = "user_name",columnDefinition = "VARCHAR(255) COMMENT 'tên người dùng'", nullable = false)
    String userName;

    @Column(name = "password",columnDefinition = "VARCHAR(255) COMMENT 'mật khẩu tài khoản'", nullable = false)
    String password;

    @ManyToOne
    @JoinColumn(name = "id_role",nullable = false)
    RoleEntity role;

    @Column(columnDefinition = "BOOLEAN COMMENT 'trạng thái khóa tài khoản'", nullable = false)
    Boolean isLock = false;

}
