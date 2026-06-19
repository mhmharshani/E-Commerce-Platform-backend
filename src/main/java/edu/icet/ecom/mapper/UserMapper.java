package edu.icet.ecom.mapper;

import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.CreateUserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toUser(CreateUserRequestDto requestDto){

        return User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
    }
}
