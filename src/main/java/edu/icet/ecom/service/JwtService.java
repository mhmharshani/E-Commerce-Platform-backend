package edu.icet.ecom.service;

import edu.icet.ecom.model.User;

import java.time.Instant;

public interface JwtService {

    public String generateToken(User user);

    public String extractUsername(String token);

    public Instant extractExpiration(String token);

    public boolean validateToken(String token);
}
