package edu.icet.ecom.service.impl;

import edu.icet.ecom.jwt.JwtFilter;
import edu.icet.ecom.jwt.JwtUtil;
import edu.icet.ecom.model.User;
import edu.icet.ecom.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    @Override
    public Instant extractExpiration(String token) {
        return jwtUtil.extractExpiration(token).toInstant();
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
