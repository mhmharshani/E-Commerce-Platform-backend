package edu.icet.ecom.repository;

import edu.icet.ecom.model.User;

public interface UserRepository {

    public int saveUser(User user);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByUsernameOrEmail(String usernameOrEmail);
}
