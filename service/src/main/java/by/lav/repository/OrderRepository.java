package by.lav.repository;

import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

import java.util.List;

import static by.lav.entity.QOrder.order;

@Component
public class OrderRepository extends RepositoryBase<Integer, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findByStatus(OrderStatus orderStatus) {
        return new JPAQuery<Order>(getEntityManager())
                .select(order)
                .from(order)
                .where(order.status.eq(orderStatus))
                .fetch();
    }
}
