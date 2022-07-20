package by.lav.dto;

import by.lav.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;
    @NotEmpty
    String email;
    @NotBlank
    String rawPassword;
    @NotEmpty
    Role role;
}
