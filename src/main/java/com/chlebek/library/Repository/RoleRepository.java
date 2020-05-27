package com.chlebek.library.Repository;

import com.chlebek.library.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);
}
