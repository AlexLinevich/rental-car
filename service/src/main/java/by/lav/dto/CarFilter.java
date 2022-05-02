package by.lav.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarFilter {

    String colour;
    Integer seatsQuantity;
}
