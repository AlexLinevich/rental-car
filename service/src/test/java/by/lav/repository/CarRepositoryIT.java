package by.lav.repository;

import by.lav.dto.CarFilter;
import by.lav.entity.Car;
import by.lav.repository.annotation.IT;
import by.lav.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@IT
@RequiredArgsConstructor
public class CarRepositoryIT {

    private static final int ID_FIRST = 1;

    private final CarRepository carRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
    }

    @Test
    void checkSaveCar() {
        Car car = Car.builder()
                .model("BMW")
                .colour("GREEN")
                .seatsQuantity(5)
                .build();

        var savedCar = carRepository.save(car);

        assertNotNull(savedCar.getId());
    }

    @Test
    void checkDeleteCar() {
        Car carForSave = Car.builder()
                .model("BMW")
                .colour("GREEN")
                .seatsQuantity(5)
                .build();

        var savedCar = carRepository.save(carForSave);

        carRepository.delete(savedCar.getId());

        Car car = entityManager.find(Car.class, savedCar.getId());
        assertNull(car);
    }

    @Test
    void checkUpdateCar() {
        Car carForSave = Car.builder()
                .model("BMW")
                .colour("GREEN")
                .seatsQuantity(5)
                .build();

        var savedCar = carRepository.save(carForSave);
        savedCar.setColour("BLUE");
        carRepository.update(savedCar);

        entityManager.flush();
        Car car = entityManager.find(Car.class, savedCar.getId());
        assertThat(car.getColour()).isEqualTo("BLUE");
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
        assertThat(results).hasSize(6);

        List<String> cars = results.stream().map(Car::getModel).collect(toList());
        assertThat(cars).containsExactlyInAnyOrder(
                "TOYOTA CAMRY", "MAZDA 6", "TOYOTA LANDCRUISER", "MAZDA CX-9", "TOYOTA RAV4", "MAZDA CX-5");
    }

    @Test
    void findAllCarsByCarCategory() {
        List<Car> results = carRepository.findAllByCarCategory("LARGE SUV");
        assertThat(results).hasSize(2);
    }

    @Test
    void findCarDayPriceByCarModel() {
        Optional<Double> carDayPrice = carRepository.findDayPriceByCarModel("TOYOTA CAMRY");

        assertThat(carDayPrice).isNotNull();
        carDayPrice.ifPresent(value -> assertThat(value).isEqualTo(60.0));
    }

    @Test
    void findAllCarsByColourAndSeatsQuantity() {
        CarFilter filter = CarFilter.builder()
                .colour("WHITE")
                .seatsQuantity(7)
                .build();
        List<Car> cars = carRepository.findAllByColourAndSeatsQuantity(filter);

        assertThat(cars).hasSize(2);

        List<String> modelNames = cars.stream().map(Car::getModel).collect(toList());
        assertThat(modelNames).containsExactlyInAnyOrder("TOYOTA LANDCRUISER", "MAZDA CX-9");
    }
}