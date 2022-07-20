package by.lav.repository;

import by.lav.dao.QPredicate;
import by.lav.dto.CarFilter;
import by.lav.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static by.lav.entity.QCar.car;

@RequiredArgsConstructor
public class FilterCarRepositoryImpl implements FilterCarRepository{

    private final EntityManager entityManager;

    @Override
    public List<Car> findAllByFilter(CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::containsIgnoreCase)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        return new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }
}
