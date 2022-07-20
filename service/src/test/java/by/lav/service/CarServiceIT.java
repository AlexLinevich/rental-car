package by.lav.service;

import by.lav.dto.CarCreateEditDto;
import by.lav.dto.CarReadDto;
import by.lav.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class CarServiceIT extends IntegrationTestBase {

    private static final Integer CAR_1 = 1;

    private final CarService carService;

    @Test
    void findAll() {
        List<CarReadDto> result = carService.findAll();
        assertThat(result).hasSize(7);
    }

    @Test
    void findById() {
        Optional<CarReadDto> maybeCar = carService.findById(CAR_1);
        assertTrue(maybeCar.isPresent());
        maybeCar.ifPresent(car -> assertEquals("TOYOTA CAMRY", car.getModel()));
    }

    @Test
    void create() {
        CarCreateEditDto carDto = new CarCreateEditDto(
                "BMW X5",
                "RED",
                5,
                null,
                1
        );
        CarReadDto actualResult = carService.create(carDto);

        assertEquals(carDto.getModel(), actualResult.getModel());
        assertEquals(carDto.getColour(), actualResult.getColour());
        assertEquals(carDto.getSeatsQuantity(), actualResult.getSeatsQuantity());
//        assertEquals(carDto.getImage(), actualResult.getImage());
        assertEquals(carDto.getCarCategoryId(), actualResult.getCarCategory().getId());
    }

    @Test
    void update() {
        CarCreateEditDto carDto = new CarCreateEditDto(
                "BMW X5",
                "RED",
                5,
                null,
                1
        );

        Optional<CarReadDto> actualResult = carService.update(CAR_1, carDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(car -> {
            assertEquals(carDto.getModel(), car.getModel());
            assertEquals(carDto.getColour(), car.getColour());
            assertEquals(carDto.getSeatsQuantity(), car.getSeatsQuantity());
//            assertEquals(carDto.getImage(), car.getImage());
            assertEquals(carDto.getCarCategoryId(), car.getCarCategory().getId());
        });
    }

    @Test
    void delete() {
        assertFalse(carService.delete(-13));
        assertTrue(carService.delete(CAR_1));
    }
}
