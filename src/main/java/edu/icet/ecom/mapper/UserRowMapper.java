package edu.icet.ecom.mapper;

import edu.icet.ecom.model.Role;
import edu.icet.ecom.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        UUID roleId = UUID.fromString(rs.getString("role_id"));

        user.setId(UUID.fromString(rs.getString("user_id")));
        user.setName(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(new Role(roleId,rs.getString("role_name")));
        user.setRoleId(roleId);
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        user.setActive(rs.getBoolean("is_active"));

        return user;
    }
}
