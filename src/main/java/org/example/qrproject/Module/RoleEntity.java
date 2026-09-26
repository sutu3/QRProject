package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_role",columnDefinition = "VARCHAR(36) COMMENT 'Id của row quyền'")
    String idRole;

    @Column(name = "roleName",columnDefinition = "VARCHAR(36) COMMENT 'tên quyền'")
    String roleName;

    @OneToMany(mappedBy="role")
    List<AccountEntity> account;
}
