package by.lav.dto;

import by.lav.entity.OrderStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OrderReadDto {

    Integer id;
    UserReadDto user;
    CarReadDto car;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    OrderStatus orderStatus;
    String message;
}
