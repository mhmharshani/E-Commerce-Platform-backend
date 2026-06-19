package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.UserRowMapper;
import edu.icet.ecom.model.User;
import edu.icet.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        try {
            return template.queryForObject("SELECT * FROM user WHERE username = ?", new UserRowMapper(), username);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return template.queryForObject(
                    "SELECT * FROM users WHERE email = ?",
                    new UserRowMapper(),
                    email
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByUsernameOrEmail(String usernameOrEmail) {
        try{
            System.out.println("User from Repository : Not null");
            return template.queryForObject(""" 
                    SELECT u.*, r.id as role_id, r.name as role_name
                    FROM user u
                    JOIN roles r ON u.role_id = r.id
                    WHERE u.username = ? OR u.email = ?""",
                    new UserRowMapper(),
                    usernameOrEmail,
                    usernameOrEmail
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
