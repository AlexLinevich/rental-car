package by.lav.dto;

import by.lav.entity.OrderStatus;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    Integer userId;
    Integer carId;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    OrderStatus status;
    String message;
}
