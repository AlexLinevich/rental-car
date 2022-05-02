package by.lav.repository;

import by.lav.entity.CarCategory;

import javax.persistence.EntityManager;

public class CarCategoryRepository extends RepositoryBase<Integer, CarCategory> {

    public CarCategoryRepository(EntityManager entityManager) {
        super(CarCategory.class, entityManager);
    }
}
