package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.UserRowMapper;
import edu.icet.ecom.model.User;
import edu.icet.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate template;

    @Override
    public int saveUser(User user) {

        String sql = """
                INSERT INTO user
                (user_id, username, email, password, role_id, created_at, updated_at, is_active)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        return template.update(
            sql,
            user.getId().toString(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getRoleId().toString(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.isActive()
        );
    }

    @Override
    public User findByUsername(String username) {
        User user = template.queryForObject("SELECT * FROM user WHERE username = ?", new UserRowMapper(), username);
        return user == null ? null : user;
    }

    @Override
    public User findByEmail(String email) {
        User user = template.queryForObject("SELECT * FROM user WHERE email = ?", new UserRowMapper(), email);
        return user == null ? null : user;
    }

    @Override
    public User findByUsernameOrEmail(String usernameOrEmail) {
        User user = template.queryForObject(""" 
                SELECT u.*, r.id as role_id, r.name as role_name
                FROM user u
                JOIN roles r ON u.role_id = r.id
                WHERE u.username = ? OR u.email = ?""",
                new UserRowMapper(),
                usernameOrEmail,
                usernameOrEmail
        );
        return user == null ? null : user;
    }
}
