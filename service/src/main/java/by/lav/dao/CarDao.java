package by.lav.dao;

import by.lav.by.lav.dto.CarFilter;
import by.lav.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import java.util.List;
import java.util.Optional;

import static by.lav.entity.QCar.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDao {

    private static final CarDao INSTANCE = new CarDao();

    public List<Car> findAll(Session session) {
        return session.createQuery("select c from Car c", Car.class)
                .list();
    }

    public List<Car> findAllByCarCategory(Session session, String carCategory) {
        var carGraph = session.createEntityGraph(Car.class);
        carGraph.addAttributeNodes("carCategory");

        return new JPAQuery<Car>(session)
                .select(car)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), carGraph)
                .from(car)
                .where(car.carCategory.category.eq(carCategory))
                .fetch();
    }

    public Optional<Double> findDayPriceByCarModel(Session session, String carModel) {

        return Optional.ofNullable(new JPAQuery<Double>(session)
                .select(car.carCategory.dayPrice)
                .from(car)
                .where(car.model.eq(carModel))
                .fetchOne());

    }

    public List<Car> findAllByColourAndSeatsQuantity(Session session, CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        return new JPAQuery<Car>(session)
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }

    public static CarDao getInstance() {
        return INSTANCE;
    }
}
