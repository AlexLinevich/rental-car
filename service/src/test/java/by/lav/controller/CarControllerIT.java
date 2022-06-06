package by.lav.controller;

import by.lav.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static by.lav.dto.CarCreateEditDto.Fields.carCategoryId;
import static by.lav.dto.CarCreateEditDto.Fields.colour;
import static by.lav.dto.CarCreateEditDto.Fields.image;
import static by.lav.dto.CarCreateEditDto.Fields.model;
import static by.lav.dto.CarCreateEditDto.Fields.seatsQuantity;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class CarControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("car/cars"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/cars")
                .param(model, "Test")
                .param(colour, "Test")
                .param(seatsQuantity, "7")
                .param(carCategoryId, "1")
                .param(image, "Test")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/cars/{\\d+}")
                );
    }
}
