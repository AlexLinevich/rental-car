package by.lav.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    String firstName;
    String lastName;
}
