package by.lav.mapper;

import by.lav.dto.CarCategoryReadDto;
import by.lav.dto.CarReadDto;
import by.lav.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarReadMapper implements Mapper<Car, CarReadDto> {

    private final CarCategoryReadMapper carCategoryReadMapper;

    @Override
    public CarReadDto map(Car object) {
        CarCategoryReadDto carCategory = Optional.ofNullable(object.getCarCategory())
                .map(carCategoryReadMapper::map)
                .orElse(null);


        return new CarReadDto(
                object.getId(),
                object.getModel(),
                object.getColour(),
                object.getSeatsQuantity(),
                object.getImage(),
                carCategory
        );
    }
}
