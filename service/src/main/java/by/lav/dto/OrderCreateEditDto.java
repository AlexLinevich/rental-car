package by.lav.dto;

import by.lav.entity.OrderStatus;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    Integer userId;
    Integer carId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime endTime;
    OrderStatus status;
    String message;
}
