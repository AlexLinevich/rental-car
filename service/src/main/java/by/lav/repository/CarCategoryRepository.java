package by.lav.repository;

import by.lav.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer>,
        QuerydslPredicateExecutor<CarCategory> {

    Optional<CarCategory> findByCategory(String category);

    @Query("select max(cc.dayPrice) from CarCategory cc join cc.cars c where c.model = :model")
    Optional<Double> findDayPriceByCarModel(String model);

    @Query("select max(c.carCategory) from Car c where c.model = :model")
    Optional<CarCategory> findByModel(String model);
}
