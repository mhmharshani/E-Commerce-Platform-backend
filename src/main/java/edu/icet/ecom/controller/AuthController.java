package edu.icet.ecom.controller;

import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.LoginRequest;
import edu.icet.ecom.model.dto.request.RegisterRequest;
import edu.icet.ecom.model.dto.response.LoginResponse;
import edu.icet.ecom.service.AuthService;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        System.out.println("Received registration request: " + request);
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                userService.findByUsernameOrEmail(userDetails.getUsername())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok(
                "Logged out successfully; remove token on client"
        );
    }

}
