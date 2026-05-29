package edu.icet.ecom.service.impl;

import edu.icet.ecom.mapper.UserMapper;
import edu.icet.ecom.model.Role;
import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.CreateUserRequestDto;
import edu.icet.ecom.model.dto.response.UserResponse;
import edu.icet.ecom.repository.RoleRepository;
import edu.icet.ecom.repository.UserRepository;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    @Override
    public UserResponse createUser(CreateUserRequestDto request) {

        User user = mapper.toUser(request);
        user.setId(UUID.randomUUID());
        user.setActive(true);
        Role role = roleRepository.findByName(request.getRole());
        user.setRole(role);
        user.setRoleId(role.getId());
        Timestamp now = Timestamp.from(Instant.now());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        int result = userRepository.saveUser(user);
        if(result>0){
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole().getName())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
        else
            return null;
    }

    @Override
    public User findByUsernameOrEmail(String identifier) {
        User user = userRepository.findByUsernameOrEmail(identifier);
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User not found"
            );
        }
        return user;
    }

//    public UserDto getUserByUsername(String username) {
//
//        User user = userRepository.findByUsernameOrEmail(username);
//
//        if (user == null) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED,
//                    "User not found"
//            );
//        }
//
//        return userMapper.toDto(user);
//    }


}
