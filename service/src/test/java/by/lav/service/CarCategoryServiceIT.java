package by.lav.service;

import by.lav.dto.CarCategoryCreateEditDto;
import by.lav.dto.CarCategoryReadDto;
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
class CarCategoryServiceIT extends IntegrationTestBase {

    private static final Integer CAR_CATEGORY_1 = 1;

    private final CarCategoryService carCategoryService;

    @Test
    void findAll() {
        List<CarCategoryReadDto> result = carCategoryService.findAll();
        assertThat(result).hasSize(3);
    }

    @Test
    void findById() {
        Optional<CarCategoryReadDto> maybeCarCategory = carCategoryService.findById(CAR_CATEGORY_1);
        assertTrue(maybeCarCategory.isPresent());
        maybeCarCategory.ifPresent(carCategory -> assertEquals("MIDDLE SUV", carCategory.getCategory()));
    }

    @Test
    void create() {
        CarCategoryCreateEditDto carCategoryDto = new CarCategoryCreateEditDto(
                "BUS",
                100.0
        );
        CarCategoryReadDto actualResult = carCategoryService.create(carCategoryDto);

        assertEquals(carCategoryDto.getCategory(), actualResult.getCategory());
        assertEquals(carCategoryDto.getDayPrice(), actualResult.getDayPrice());
    }

    @Test
    void update() {
        CarCategoryCreateEditDto carCategoryDto = new CarCategoryCreateEditDto(
                "BUS",
                100.0
        );

        Optional<CarCategoryReadDto> actualResult = carCategoryService.update(CAR_CATEGORY_1, carCategoryDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(carCategory -> {
            assertEquals(carCategoryDto.getCategory(), carCategory.getCategory());
            assertEquals(carCategoryDto.getDayPrice(), carCategory.getDayPrice());
        });
    }

    @Test
    void delete() {
        assertFalse(carCategoryService.delete(-13));
        assertTrue(carCategoryService.delete(CAR_CATEGORY_1));
    }
}
