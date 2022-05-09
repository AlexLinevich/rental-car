package by.lav.repository;

import by.lav.entity.CarCategory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class CarCategoryRepository extends RepositoryBase<Integer, CarCategory> {

    public CarCategoryRepository(EntityManager entityManager) {
        super(CarCategory.class, entityManager);
    }
}
