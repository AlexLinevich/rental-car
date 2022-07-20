package by.lav.repository;

import by.lav.entity.Car;
import by.lav.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CarRepository extends
        JpaRepository<Car, Integer>,
        QuerydslPredicateExecutor<Car>,
        FilterCarRepository {

    List<Car> findByCarCategory(CarCategory carCategory);
}
