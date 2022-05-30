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

    @Query("select c.carCategory from Car c where c.model = :model")
    Optional<CarCategory> findByModel(String model);
}
