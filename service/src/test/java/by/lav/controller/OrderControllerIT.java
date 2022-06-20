package by.lav.controller;

import by.lav.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static by.lav.dto.OrderCreateEditDto.Fields.beginTime;
import static by.lav.dto.OrderCreateEditDto.Fields.carId;
import static by.lav.dto.OrderCreateEditDto.Fields.endTime;
import static by.lav.dto.OrderCreateEditDto.Fields.message;
import static by.lav.dto.OrderCreateEditDto.Fields.status;
import static by.lav.dto.OrderCreateEditDto.Fields.userId;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class OrderControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/orders"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/orders")
                .param(userId, "1")
                .param(carId, "1")
                .param(beginTime, "2020-01-25 12:00")
                .param(endTime, "2020-01-29 18:00")
                .param(status, "ACCEPTED")
                .param(message, "MESSAGE")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/orders/{\\d+}")
                );
    }
}
