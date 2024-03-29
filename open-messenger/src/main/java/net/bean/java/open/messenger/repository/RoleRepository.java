package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.data.jpa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
