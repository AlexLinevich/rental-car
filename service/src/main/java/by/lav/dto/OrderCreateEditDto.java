package by.lav.dto;

import by.lav.entity.OrderStatus;
import lombok.Data;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Data
@FieldNameConstants
public class OrderCreateEditDto {

    Integer userId;
    Integer carId;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    OrderStatus status;
    String message;
}
