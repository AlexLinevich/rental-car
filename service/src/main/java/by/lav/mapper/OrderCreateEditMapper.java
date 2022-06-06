package by.lav.mapper;

import by.lav.dto.OrderCreateEditDto;
import by.lav.entity.Car;
import by.lav.entity.Order;
import by.lav.entity.User;
import by.lav.repository.CarRepository;
import by.lav.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);
        return order;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        order.setUser(getUser(object.getUserId()));
        order.setCar(getCar(object.getCarId()));
        order.setBeginTime(object.getBeginTime());
        order.setEndTime(object.getEndTime());
        order.setStatus(object.getStatus());
        order.setMessage(object.getMessage());
    }

    public Car getCar(Integer carId) {
        return Optional.ofNullable(carId)
                .flatMap(carRepository::findById)
                .orElse(null);
    }

    public User getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
