package by.lav.mapper;

import by.lav.dto.RentalTimeCreateEditDto;
import by.lav.entity.Car;
import by.lav.entity.Order;
import by.lav.entity.RentalTime;
import by.lav.repository.CarRepository;
import by.lav.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RentalTimeCreateEditMapper implements Mapper<RentalTimeCreateEditDto, RentalTime> {

    private final OrderRepository orderRepository;
    private final CarRepository carRepository;

    @Override
    public RentalTime map(RentalTimeCreateEditDto fromObject, RentalTime toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public RentalTime map(RentalTimeCreateEditDto object) {
        RentalTime rentalTime = new RentalTime();
        copy(object, rentalTime);
        return rentalTime;
    }

    private void copy(RentalTimeCreateEditDto object, RentalTime rentalTime) {
        rentalTime.setCar(getCar(object.getCarId()));
        rentalTime.setBeginTime(object.getBeginTime());
        rentalTime.setEndTime(object.getEndTime());
        rentalTime.setOrder(getOrder(object.getOrderId()));
    }

    public Car getCar(Integer carId) {
        return Optional.ofNullable(carId)
                .flatMap(carRepository::findById)
                .orElse(null);
    }

    public Order getOrder(Integer orderId) {
        return Optional.ofNullable(orderId)
                .flatMap(orderRepository::findById)
                .orElse(null);
    }
}
