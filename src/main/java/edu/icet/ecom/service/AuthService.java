package edu.icet.ecom.service;

import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.LoginRequest;
import edu.icet.ecom.model.dto.response.LoginResponse;
import edu.icet.ecom.model.dto.request.RegisterRequest;

public interface AuthService {

    public LoginResponse login(LoginRequest request);

    public void register(RegisterRequest request);

    public String generateSetupToken();

    public User getUserFromToken(String token);
}
