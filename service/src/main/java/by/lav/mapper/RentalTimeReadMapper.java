package by.lav.mapper;

import by.lav.dto.CarReadDto;
import by.lav.dto.OrderReadDto;
import by.lav.dto.RentalTimeReadDto;
import by.lav.entity.RentalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RentalTimeReadMapper implements Mapper<RentalTime, RentalTimeReadDto> {

    private final OrderReadMapper userReadMapper;
    private final CarReadMapper carReadMapper;

    @Override
    public RentalTimeReadDto map(RentalTime object) {
        OrderReadDto order = Optional.ofNullable(object.getOrder())
                .map(userReadMapper::map)
                .orElse(null);
        CarReadDto car = Optional.ofNullable(object.getCar())
                .map(carReadMapper::map)
                .orElse(null);

        return new RentalTimeReadDto(
                object.getId(),
                car,
                object.getBeginTime(),
                object.getEndTime(),
                order
        );
    }
}
