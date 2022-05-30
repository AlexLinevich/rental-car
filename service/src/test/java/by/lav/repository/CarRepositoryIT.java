package by.lav.repository;

import by.lav.dao.QPredicate;
import by.lav.dto.CarFilter;
import by.lav.entity.Car;
import by.lav.entity.CarCategory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static by.lav.entity.QCar.car;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class CarRepositoryIT extends IntegrationTestBase {

    private static final int ID_FIRST = 1;

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;

    @Test
    void checkSaveCar() {
        Car car = Car.builder()
                .model("BMW")
                .carCategory(carCategoryRepository.getById(1))
                .colour("GREEN")
                .seatsQuantity(5)
                .image("IMAGE")
                .build();

        carRepository.save(car);

        assertNotNull(car.getId());
    }

    @Test
    void checkDeleteCar() {
        Car car = Car.builder()
                .model("BMW")
                .carCategory(carCategoryRepository.getById(1))
                .colour("GREEN")
                .seatsQuantity(5)
                .image("IMAGE")
                .build();

        carRepository.save(car);

        carRepository.delete(car);

        Optional<Car> deletedCar = carRepository.findById(car.getId());
        assertTrue(deletedCar.isEmpty());
    }

    @Test
    void checkUpdateCar() {
        Car car = Car.builder()
                .model("BMW")
                .carCategory(carCategoryRepository.getById(1))
                .colour("GREEN")
                .seatsQuantity(5)
                .image("IMAGE")
                .build();

        carRepository.save(car);
        car.setColour("BLUE");
        carRepository.saveAndFlush(car);

        Car updatedCar = carRepository.getById(car.getId());
        assertThat(updatedCar.getColour()).isEqualTo("BLUE");
    }

    @Test
    void checkFindByIdCar() {
        Optional<Car> car = carRepository.findById(ID_FIRST);

        assertThat(car).isNotNull();
        car.ifPresent(value -> assertThat(value.getModel()).isEqualTo("TOYOTA CAMRY"));
        car.ifPresent(value -> assertThat(value.getColour()).isEqualTo("WHITE"));
    }

    @Test
    void checkFindAllCars() {
        List<Car> results = carRepository.findAll();
        assertThat(results).hasSize(7);

        List<String> cars = results.stream().map(Car::getModel).collect(toList());
        assertThat(cars).containsExactlyInAnyOrder(
                "TOYOTA CAMRY", "MAZDA 6", "TOYOTA LANDCRUISER", "MAZDA CX-9", "TOYOTA RAV4", "MAZDA CX-5", "MAZDA 6");
    }

    @Test
    void findAllCarsByCarCategory() {
        Optional<CarCategory> largeSuv = carCategoryRepository.findByCategory("LARGE SUV");
        List<Car> results = carRepository.findByCarCategory(largeSuv.orElseThrow());
        assertThat(results).hasSize(2);
    }

    @Test
    void findCarDayPriceByCarModel() {
        Optional<CarCategory> carCategory = carRepository.findByModel("MAZDA 6");
        Optional<CarCategory> largeSedan = carCategoryRepository.findByCategory(carCategory.orElseThrow().getCategory());

        assertEquals(largeSedan.orElseThrow().getDayPrice(), 60.0);

        Optional<Double> carDayPrice = carCategoryRepository.findDayPriceByCarModel("MAZDA 6");

        assertThat(carDayPrice).isNotNull();
        carDayPrice.ifPresent(value -> assertThat(value).isEqualTo(60.0));
    }

    @Test
    void findAllCarsByColourAndSeatsQuantity() {
        CarFilter filter = CarFilter.builder()
                .colour("WHITE")
                .seatsQuantity(7)
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        Iterable<Car> cars = carRepository.findAll(predicate);

        assertThat(cars).hasSize(2);

        List<String> modelNames = StreamSupport.stream(cars.spliterator(), false)
                .map(Car::getModel)
                .collect(toList());
        assertThat(modelNames).containsExactlyInAnyOrder("TOYOTA LANDCRUISER", "MAZDA CX-9");
    }
}