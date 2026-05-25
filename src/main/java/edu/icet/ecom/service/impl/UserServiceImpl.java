package edu.icet.ecom.service.impl;

import edu.icet.ecom.mapper.UserMapper;
import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.CreateUserRequestDto;
import edu.icet.ecom.model.dto.response.UserResponse;
import edu.icet.ecom.repository.UserRepository;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserResponse createUser(CreateUserRequestDto request) {

        User user = mapper.toUser(request);
        user.setId(UUID.randomUUID());
        user.setActive(true);

        Timestamp now = Timestamp.from(Instant.now());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        int result = userRepository.saveUser(user);
        if(result>0){
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
        else
            return null;
    }
}
