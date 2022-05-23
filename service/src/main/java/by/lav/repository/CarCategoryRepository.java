package by.lav.repository;

import by.lav.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer>,
        QuerydslPredicateExecutor<CarCategory> {

    Optional<CarCategory> findByCategory(String categoryName);
}
