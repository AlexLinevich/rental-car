package by.lav.controller;

import by.lav.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static by.lav.dto.CarCategoryCreateEditDto.Fields.category;
import static by.lav.dto.CarCategoryCreateEditDto.Fields.dayPrice;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class CarCategoryControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/car-categories"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("car-category/car-categories"))
                .andExpect(model().attributeExists("carCategories"))
                .andExpect(model().attribute("carCategories", hasSize(7)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/car-categories")
                .param(category, "Test")
                .param(dayPrice, "100.0")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/car-categories/{\\d+}")
                );
    }
}
