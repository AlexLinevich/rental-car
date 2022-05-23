package by.lav.repository;

import by.lav.entity.Car;
import by.lav.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer>, QuerydslPredicateExecutor<Car> {

    List<Car> findByCarCategory(CarCategory carCategory);

    @Query(value = "SELECT cc.day_price " +
            "FROM car_category cc " +
            "JOIN car c on cc.id = c.car_category_id " +
            "WHERE c.model = :carModel",
            nativeQuery = true)
    Optional<Double> findDayPriceBy(String carModel);

//    @Query("select cc.dayPrice " +
//            "from CarCategory cc " +
//            "join Car c on cc.id = c.carCategory.id " +
//            "where c.model = :carModel")
//    Double findDayPriceBy(String carModel);
}
