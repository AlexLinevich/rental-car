package by.lav.dao;

import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static by.lav.entity.QOrder.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();

    public List<Order> findAll(Session session) {
        return session.createQuery("select o from Order o", Order.class)
                .list();
    }

    public List<Order> findByStatus(Session session, OrderStatus orderStatus) {
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .where(order.status.eq(orderStatus))
                .fetch();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
