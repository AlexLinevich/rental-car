package by.lav.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RentalTimeReadDto {

    Integer id;
    CarReadDto car;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    OrderReadDto order;
}
