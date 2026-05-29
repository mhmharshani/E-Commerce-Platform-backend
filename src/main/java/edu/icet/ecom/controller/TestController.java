package edu.icet.ecom.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin-api")
    public String testAdminAPI(){
        return "Admin API is working!";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/public-api")
    public String testPublicAPI(){
        return "Public API is working!";
    }
}
