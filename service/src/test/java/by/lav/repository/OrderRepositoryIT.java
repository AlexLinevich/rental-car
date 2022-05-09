package by.lav.repository;

import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import by.lav.util.HibernateUtil;
import by.lav.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderRepositoryIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private static final int ID_FIRST = 1;

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void finish() {
        sessionFactory.close();
    }

    @Test
    void checkSaveOrder() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var order = Order.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        var orderRepository = new OrderRepository(session);
        orderRepository.save(order);

        assertNotNull(order.getId());

        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteOrder() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var orderRepository = new OrderRepository(session);
        orderRepository.delete(ID_FIRST);

        Order order = session.get(Order.class, ID_FIRST);
        assertNull(order);

        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateOrder() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var orderRepository = new OrderRepository(session);

        var beginTime = LocalDateTime.of(2025, 1, 25, 12, 0);
        Order order = session.get(Order.class, ID_FIRST);
        order.setBeginTime(beginTime);
        orderRepository.update(order);

        session.flush();
        Order order1 = session.get(Order.class, ID_FIRST);
        assertThat(order1.getBeginTime()).isEqualTo(beginTime);

        session.getTransaction().rollback();
    }

    @Test
    void checkFindByIdOrder() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var orderRepository = new OrderRepository(session);

        Optional<Order> order = orderRepository.findById(ID_FIRST);

        var beginTime = LocalDateTime.of(2020, 1, 25, 12, 0);
        var endTime = LocalDateTime.of(2020, 1, 29, 18, 0);

        assertThat(order).isNotNull();
        order.ifPresent(value -> assertThat(value.getBeginTime()).isEqualTo(beginTime));
        order.ifPresent(value -> assertThat(value.getEndTime()).isEqualTo(endTime));

        session.getTransaction().rollback();
    }

    @Test
    void checkFindAllOrders() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var orderRepository = new OrderRepository(session);

        List<Order> results = orderRepository.findAll();
        assertThat(results).hasSize(4);

        var beginTime1 = LocalDateTime.of(2020, 1, 25, 12, 0);
        var beginTime2 = LocalDateTime.of(2020, 2, 25, 12, 0);
        var beginTime3 = LocalDateTime.of(2020, 5, 25, 12, 0);
        var beginTime4 = LocalDateTime.of(2020, 3, 25, 12, 0);
        List<LocalDateTime> beginTimes = results.stream().map(Order::getBeginTime).collect(toList());
        assertThat(beginTimes).containsExactlyInAnyOrder(beginTime1, beginTime2, beginTime3, beginTime4);

        session.getTransaction().rollback();
    }

    @Test
    void findOrdersByStatus() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var orderRepository = new OrderRepository(session);

        List<Order> results = orderRepository.findByStatus(OrderStatus.ACCEPTED);
        assertThat(results).hasSize(3);

        session.getTransaction().rollback();
    }
}
