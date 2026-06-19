package edu.icet.ecom.repository.impl;

import edu.icet.ecom.model.Role;
import edu.icet.ecom.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UUID findRoleIdByName(String roleName) {
        String sql = "SELECT id FROM roles WHERE name = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> UUID.fromString(rs.getString("id")),
                roleName);
    }

    @Override
    public Role findByName(String name) {
        String sql = "SELECT * FROM roles WHERE name = ?";

        List<Role> roles = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Role(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name")
                ),
                name
        );

        if (roles.isEmpty()) {
            throw new RuntimeException("Role not found: " + name);
        }

        return roles.get(0);
    }

    @Override
    public void save(Role role) {
        String sql = """
                INSERT INTO roles(id, name)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                sql,
                role.getId().toString(),
                role.getName()
        );
    }
}
