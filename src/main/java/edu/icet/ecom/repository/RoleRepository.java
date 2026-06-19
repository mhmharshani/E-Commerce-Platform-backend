package edu.icet.ecom.repository;

import edu.icet.ecom.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    UUID findRoleIdByName(String roleName);

    Role findByName(String name);


    void save(Role role);
}
