package edu.icet.ecom.service.impl;

import edu.icet.ecom.model.Role;
import edu.icet.ecom.repository.RoleRepository;
import edu.icet.ecom.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void createRole(String roleName) {
        Role role = new Role(
                UUID.randomUUID(),
                roleName.toUpperCase()
        );

        roleRepository.save(role);

    }
}
