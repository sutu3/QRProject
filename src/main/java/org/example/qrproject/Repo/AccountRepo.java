package org.example.qrproject.Repo;

import org.example.qrproject.Module.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity,String>, JpaSpecificationExecutor<AccountEntity> {
    Optional<AccountEntity> findByUserNameIgnoreCaseAndIsDeleted(String tenTaiKhoan, boolean isDeleted);
    Optional<AccountEntity> findByUserNameAndPassword(String UserName,String matKhau);

    List<AccountEntity> findByIsDeletedFalse();

    Optional<AccountEntity> findByIdAccountAndIsDeletedFalse(String idTaiKhoan);
    boolean existsByRole_IdRole(String idVaiTro);

}
