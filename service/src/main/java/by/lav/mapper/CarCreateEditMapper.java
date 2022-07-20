package by.lav.mapper;

import by.lav.dto.CarCreateEditDto;
import by.lav.entity.Car;
import by.lav.entity.CarCategory;
import by.lav.repository.CarCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class CarCreateEditMapper implements Mapper<CarCreateEditDto, Car> {

    private final CarCategoryRepository carCategoryRepository;

    @Override
    public Car map(CarCreateEditDto fromObject, Car toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Car map(CarCreateEditDto object) {
        Car car = new Car();
        copy(object, car);
        return car;
    }

    private void copy(CarCreateEditDto object, Car car) {
        car.setModel(object.getModel());
        car.setColour(object.getColour());
        car.setSeatsQuantity(object.getSeatsQuantity());
        car.setCarCategory(getCarCategory(object.getCarCategoryId()));

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> car.setImage(image.getOriginalFilename()));
    }

    public CarCategory getCarCategory(Integer carCategoryId) {
        return Optional.ofNullable(carCategoryId)
                .flatMap(carCategoryRepository::findById)
                .orElse(null);
    }
}
