package edu.icet.ecom.service;

import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.CreateUserRequestDto;
import edu.icet.ecom.model.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequestDto request);

    public User findByUsernameOrEmail(String identifier);
}
