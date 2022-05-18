package by.lav.repository;

import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import by.lav.repository.annotation.IT;
import by.lav.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@IT
@RequiredArgsConstructor
public class OrderRepositoryIT {

    private static final int ID_FIRST = 1;

    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
    }

    @Test
    void checkSaveOrder() {
        var order = Order.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        orderRepository.save(order);

        assertNotNull(order.getId());
    }

    @Test
    void checkDeleteOrder() {
        var order = Order.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        Order savedOrder = orderRepository.save(order);

        orderRepository.delete(savedOrder.getId());

        Order order1 = entityManager.find(Order.class, savedOrder.getId());

        assertNull(order1);
    }

    @Test
    void checkUpdateOrder() {
        var order = Order.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        Order savedOrder = orderRepository.save(order);
        var beginTime = LocalDateTime.of(2025, 1, 25, 12, 0);
        Order order1 = entityManager.find(Order.class, savedOrder.getId());
        order1.setBeginTime(beginTime);
        orderRepository.update(order1);

        entityManager.flush();
        Order order2 = entityManager.find(Order.class, savedOrder.getId());

        assertThat(order2.getBeginTime()).isEqualTo(beginTime);
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
