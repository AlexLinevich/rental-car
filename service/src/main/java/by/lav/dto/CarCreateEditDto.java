package by.lav.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@FieldNameConstants
public class CarCreateEditDto {

    String model;
    String colour;
    Integer seatsQuantity;
    MultipartFile image;
    Integer carCategoryId;
}
