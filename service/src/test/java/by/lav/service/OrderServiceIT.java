package by.lav.service;

import by.lav.dto.OrderCreateEditDto;
import by.lav.dto.OrderReadDto;
import by.lav.entity.OrderStatus;
import by.lav.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class OrderServiceIT extends IntegrationTestBase {

    private static final Integer ORDER_1 = 1;

    private final OrderService orderService;

    @Test
    void findAll() {
        List<OrderReadDto> result = orderService.findAll();
        assertThat(result).hasSize(4);
    }

    @Test
    void findById() {
        Optional<OrderReadDto> maybeOrder = orderService.findById(ORDER_1);
        assertTrue(maybeOrder.isPresent());
        maybeOrder.ifPresent(order -> assertEquals(OrderStatus.ACCEPTED, order.getOrderStatus()));
    }

    @Test
    void create() {
        OrderCreateEditDto orderDto = new OrderCreateEditDto(
                1,
                1,
                LocalDateTime.of(2022, 01, 01, 12, 00),
                LocalDateTime.of(2022, 01, 11, 11, 00),
                OrderStatus.PROCESSING,
                "MESSAGE"
        );
        OrderReadDto actualResult = orderService.create(orderDto);

        LocalDateTime time = LocalDateTime.of(2022, 01, 01, 12, 00);
        assertEquals(orderDto.getUserId(), actualResult.getUser().getId());
        assertEquals(orderDto.getCarId(), actualResult.getCar().getId());
        assertEquals(orderDto.getBeginTime(), actualResult.getBeginTime());
        assertEquals(orderDto.getEndTime(), actualResult.getEndTime());
        assertEquals(orderDto.getStatus(), actualResult.getOrderStatus());
        assertEquals(orderDto.getMessage(), actualResult.getMessage());
    }

    @Test
    void update() {
        OrderCreateEditDto orderDto = new OrderCreateEditDto(
                1,
                1,
                LocalDateTime.of(2022, 01, 01, 12, 00),
                LocalDateTime.of(2022, 01, 11, 11, 00),
                OrderStatus.PROCESSING,
                "MESSAGE"
        );

        Optional<OrderReadDto> actualResult = orderService.update(ORDER_1, orderDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(order -> {
            assertEquals(orderDto.getUserId(), order.getUser().getId());
            assertEquals(orderDto.getCarId(), order.getCar().getId());
            assertEquals(orderDto.getBeginTime(), order.getBeginTime());
            assertEquals(orderDto.getEndTime(), order.getEndTime());
            assertEquals(orderDto.getStatus(), order.getOrderStatus());
            assertEquals(orderDto.getMessage(), order.getMessage());
        });
    }

    @Test
    void delete() {
        assertFalse(orderService.delete(-13));
        assertTrue(orderService.delete(ORDER_1));
    }
}
