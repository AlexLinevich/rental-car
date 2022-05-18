package by.lav.repository;

import by.lav.dao.QPredicate;
import by.lav.dto.CarFilter;
import by.lav.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static by.lav.entity.QCar.car;

@Repository
@Transactional
public class CarRepository extends RepositoryBase<Integer, Car> {

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }

    public List<Car> findAllByCarCategory(String carCategory) {
        var carGraph = getEntityManager().createEntityGraph(Car.class);
        carGraph.addAttributeNodes("carCategory");

        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), carGraph)
                .from(car)
                .where(car.carCategory.category.eq(carCategory))
                .fetch();
    }

    public Optional<Double> findDayPriceByCarModel(String carModel) {
        return Optional.ofNullable(new JPAQuery<Double>(getEntityManager())
                .select(car.carCategory.dayPrice)
                .from(car)
                .where(car.model.eq(carModel))
                .fetchOne());

    }

    public List<Car> findAllByColourAndSeatsQuantity(CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }
}
