package by.lav.mapper;

import by.lav.dto.UserReadDto;
import by.lav.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                object.getPassword(),
                object.getRole()
        );
    }
}
