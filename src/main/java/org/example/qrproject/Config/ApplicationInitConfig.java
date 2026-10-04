package org.example.qrproject.Config;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.qrproject.Module.AccountEntity;
import org.example.qrproject.Module.RoleEntity;
import org.example.qrproject.Repo.AccountRepo;
import org.example.qrproject.Repo.RoleRepo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(AccountRepo accountRepo,
                                        RoleRepo roleRepo) {
        return args -> {

            // Initial data setup
            if (accountRepo.findByUserNameIgnoreCaseAndIsDeleted("admin",false).isEmpty()) {



                RoleEntity vaiTro =
                        roleRepo.findByRoleName("admin")
                                .orElseGet(() -> {

                                    RoleEntity role = RoleEntity.builder()
                                            .roleName("Admin")
                                            .isDeleted(false)
                                            .build();

                                    return roleRepo.save(role);
                                });
                RoleEntity User =
                        roleRepo.findByRoleName("User")
                                .orElseGet(() -> {

                                    RoleEntity role = RoleEntity.builder()
                                            .roleName("User")
                                            .isDeleted(false)
                                            .build();

                                    return roleRepo.save(role);
                                });

                AccountEntity user = AccountEntity.builder()
                        .userName("admin")
                        .accountName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .createdAt(LocalDateTime.now())
                        .role(vaiTro)
                        .isDeleted(false)
                        .isLock(false)
                        .build();


                accountRepo.save(user);

            }

            log.warn("user admin created with default password username is admin");
        };
    }
}
