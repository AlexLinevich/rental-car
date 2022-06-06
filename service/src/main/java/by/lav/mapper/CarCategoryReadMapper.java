package by.lav.mapper;

import by.lav.dto.CarCategoryReadDto;
import by.lav.entity.CarCategory;
import org.springframework.stereotype.Component;

@Component
public class CarCategoryReadMapper implements Mapper<CarCategory, CarCategoryReadDto> {

    @Override
    public CarCategoryReadDto map(CarCategory object) {
        return new CarCategoryReadDto(
                object.getId(),
                object.getCategory(),
                object.getDayPrice()
        );
    }
}
