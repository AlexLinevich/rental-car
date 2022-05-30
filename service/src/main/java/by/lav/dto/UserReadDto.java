package by.lav.dto;

import by.lav.entity.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
}
