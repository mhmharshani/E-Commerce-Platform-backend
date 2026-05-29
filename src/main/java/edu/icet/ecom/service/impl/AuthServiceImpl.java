package edu.icet.ecom.service.impl;

import edu.icet.ecom.event.UserRegisteredEvent;
import edu.icet.ecom.model.Role;
import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.CreateUserRequestDto;
import edu.icet.ecom.model.dto.request.LoginRequest;
import edu.icet.ecom.model.dto.request.RegisterRequest;
import edu.icet.ecom.model.dto.response.LoginResponse;
import edu.icet.ecom.repository.RoleRepository;
import edu.icet.ecom.service.AuthService;
import edu.icet.ecom.service.JwtService;
import edu.icet.ecom.service.NotificationService;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final NotificationService notificationService;
    private final RoleRepository roleRepository;

    private static final String ACCESS_LINK_BASE =
            "https://greenlife.com/access";

    private static final java.security.SecureRandom SECURE_RANDOM =
            new java.security.SecureRandom();

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        User user = userService.findByUsernameOrEmail(request.getUsernameOrEmail());

        String token = jwtService.generateToken(user);

        Instant expiresAt = jwtService.extractExpiration(token);

        return new LoginResponse(
                token,
                expiresAt,
                user.getRole().getName()
        );
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Role role = roleRepository.findByName(
                request.getRole()
        );
        System.out.println(role);

        CreateUserRequestDto userRequest = CreateUserRequestDto.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(role.getName())
                .build();

        userService.createUser(userRequest);

        String setupToken = generateSetupToken();

        String accessLink = buildAccessLink(
                request.getEmail(),
                setupToken
        );

        notificationService.publishUserRegisteredEvent(new UserRegisteredEvent(
                null,
                request.getEmail(),
                request.getUsername(),
                accessLink,
                Instant.now().toString()
        ));

    }

    private String buildAccessLink(String email, String token) {
        return ACCESS_LINK_BASE +
                "?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                "&setupToken=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
    }

    @Override
    public String generateSetupToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    @Override
    public User getUserFromToken(String token) {
        String username = jwtService.extractUsername(token);
        return userService.findByUsernameOrEmail(username);
    }


}
