package by.lav.repository;

import by.lav.entity.CarCategory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class CarCategoryRepository extends RepositoryBase<Integer, CarCategory> {

    public CarCategoryRepository(EntityManager entityManager) {
        super(CarCategory.class, entityManager);
    }
}
