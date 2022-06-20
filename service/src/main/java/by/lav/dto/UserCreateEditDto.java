package by.lav.dto;

import by.lav.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String firstName;
    String lastName;
    String email;
    @NotBlank
    String rawPassword;
    Role role;
}
