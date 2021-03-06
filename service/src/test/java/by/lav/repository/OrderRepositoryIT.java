package by.lav.repository;

import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class OrderRepositoryIT extends IntegrationTestBase {

    private static final int ID_FIRST = 1;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Test
    void checkSaveOrder() {
        var order = Order.builder()
                .user(userRepository.getById(1))
                .car(carRepository.getById(1))
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .status(OrderStatus.ACCEPTED)
                .build();

        orderRepository.save(order);

        assertNotNull(order.getId());
    }

    @Test
    void checkDeleteOrder() {
        var order = Order.builder()
                .user(userRepository.getById(1))
                .car(carRepository.getById(1))
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .status(OrderStatus.ACCEPTED)
                .build();

        orderRepository.save(order);
        orderRepository.delete(order);

        Optional<Order> deletedOrder = orderRepository.findById(order.getId());

        assertTrue(deletedOrder.isEmpty());
    }

    @Test
    void checkUpdateOrder() {
        var order = Order.builder()
                .user(userRepository.getById(1))
                .car(carRepository.getById(1))
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .status(OrderStatus.ACCEPTED)
                .build();

        orderRepository.save(order);
        var beginTime = LocalDateTime.of(2025, 1, 25, 12, 0);
        order.setBeginTime(beginTime);
        orderRepository.saveAndFlush(order);

        Order updatedOrder = orderRepository.getById(order.getId());

        assertThat(updatedOrder.getBeginTime()).isEqualTo(beginTime);
    }

    @Test
    void checkFindByIdOrder() {
        Optional<Order> order = orderRepository.findById(ID_FIRST);

        var beginTime = LocalDateTime.of(2020, 1, 25, 12, 0);
        var endTime = LocalDateTime.of(2020, 1, 29, 18, 0);

        assertThat(order).isNotNull();
        order.ifPresent(value -> assertThat(value.getBeginTime()).isEqualTo(beginTime));
        order.ifPresent(value -> assertThat(value.getEndTime()).isEqualTo(endTime));
    }

    @Test
    void checkFindAllOrders() {
        List<Order> results = orderRepository.findAll();
        assertThat(results).hasSize(4);

        var beginTime1 = LocalDateTime.of(2020, 1, 25, 12, 0);
        var beginTime2 = LocalDateTime.of(2020, 2, 25, 12, 0);
        var beginTime3 = LocalDateTime.of(2020, 5, 25, 12, 0);
        var beginTime4 = LocalDateTime.of(2020, 3, 25, 12, 0);
        List<LocalDateTime> beginTimes = results.stream().map(Order::getBeginTime).collect(toList());
        assertThat(beginTimes).containsExactlyInAnyOrder(beginTime1, beginTime2, beginTime3, beginTime4);
    }

    @Test
    void findOrdersByStatus() {
        List<Order> results = orderRepository.findByStatus(OrderStatus.ACCEPTED);

        assertThat(results).hasSize(3);
    }
}
