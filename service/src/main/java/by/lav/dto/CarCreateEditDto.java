package by.lav.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class CarCreateEditDto {

    String model;
    String colour;
    Integer seatsQuantity;
    String image;
    Integer carCategoryId;
}
