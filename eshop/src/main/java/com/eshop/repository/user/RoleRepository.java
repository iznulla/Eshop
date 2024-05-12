package com.eshop.repository.user;


import com.eshop.enums.Role;
import com.eshop.model.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByRole(Role role);
    List<RoleEntity> findAllByRoleIn(List<Role> role);
}
