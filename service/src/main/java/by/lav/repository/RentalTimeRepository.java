package by.lav.repository;

import by.lav.entity.RentalTime;

import javax.persistence.EntityManager;

public class RentalTimeRepository extends RepositoryBase<Integer, RentalTime> {

    public RentalTimeRepository(EntityManager entityManager) {
        super(RentalTime.class, entityManager);
    }
}
