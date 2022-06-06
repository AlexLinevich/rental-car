package by.lav.mapper;

import by.lav.dto.CarCategoryCreateEditDto;
import by.lav.entity.CarCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarCategoryCreateEditMapper implements Mapper<CarCategoryCreateEditDto, CarCategory> {

    @Override
    public CarCategory map(CarCategoryCreateEditDto fromObject, CarCategory toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public CarCategory map(CarCategoryCreateEditDto object) {
        CarCategory carCategory = new CarCategory();
        copy(object, carCategory);
        return carCategory;
    }

    private void copy(CarCategoryCreateEditDto object, CarCategory car) {
        car.setCategory(object.getCategory());
        car.setDayPrice(object.getDayPrice());
    }
}
