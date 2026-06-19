package edu.icet.ecom.config;

import edu.icet.ecom.model.Role;
import edu.icet.ecom.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("USER");
        createRoleIfNotExists("ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {

        try {

            roleRepository.findByName(roleName);

        } catch (Exception e) {

            Role role = new Role(
                    UUID.randomUUID(),
                    roleName
            );

            roleRepository.save(role);

            System.out.println(roleName + " role created");
        }
    }
}
