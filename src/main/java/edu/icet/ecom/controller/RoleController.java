package edu.icet.ecom.controller;

import edu.icet.ecom.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    
    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<String> createRole(@RequestParam String name) {
        roleService.createRole(name);
        return ResponseEntity.ok("Role created successfully");
    }
}
