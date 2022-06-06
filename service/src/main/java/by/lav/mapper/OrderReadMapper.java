package by.lav.mapper;

import by.lav.dto.CarReadDto;
import by.lav.dto.OrderReadDto;
import by.lav.dto.UserReadDto;
import by.lav.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final UserReadMapper userReadMapper;
    private final CarReadMapper carReadMapper;

    @Override
    public OrderReadDto map(Order object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        CarReadDto car = Optional.ofNullable(object.getCar())
                .map(carReadMapper::map)
                .orElse(null);

        return new OrderReadDto(
                object.getId(),
                user,
                car,
                object.getBeginTime(),
                object.getEndTime(),
                object.getStatus(),
                object.getMessage()
        );
    }
}
