package edu.icet.ecom.repository.impl;

import edu.icet.ecom.model.User;
import edu.icet.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
                (user_id, username, email, password, created_at, updated_at, is_active)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        return template.update(
            sql,
            user.getId().toString(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.isActive()
        );
    }
}
