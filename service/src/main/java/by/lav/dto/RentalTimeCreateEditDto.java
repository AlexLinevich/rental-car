package by.lav.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Value
@FieldNameConstants
public class RentalTimeCreateEditDto {

    Integer carId;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    Integer orderId;
}
