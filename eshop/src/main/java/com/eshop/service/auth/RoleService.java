package com.eshop.service.auth;


import com.eshop.enums.Role;
import com.eshop.model.user.RoleEntity;
import com.eshop.model.user.UserEntity;
import com.eshop.repository.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    @PostConstruct
    public void setupRoles() {
        for (var role : Role.values()) {
            if (!repository.existsByRole(role)) {
                repository.save(RoleEntity.builder().role(role).build());
            }
        }
    }

    @Transactional
    public void setRolesForUser(UserEntity userEntity, List<Role> roles) {
        var roleEntities = getRoleEntityByRole(roles);
        roleEntities.forEach(role -> role.addUser(userEntity));
        repository.saveAll(roleEntities);
    }

    private List<RoleEntity> getRoleEntityByRole(List<Role> roles) {
        return repository.findAllByRoleIn(roles);
    }
}
