package by.lav.repository;

import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static by.lav.entity.QOrder.order;

@Repository
@Transactional
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
