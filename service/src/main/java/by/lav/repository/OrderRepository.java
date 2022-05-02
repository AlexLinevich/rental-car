package by.lav.repository;

import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import javax.persistence.EntityManager;

import java.util.List;

import static by.lav.entity.QOrder.order;

public class OrderRepository extends RepositoryBase<Integer, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findByStatus(Session session, OrderStatus orderStatus) {
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .where(order.status.eq(orderStatus))
                .fetch();
    }
}
