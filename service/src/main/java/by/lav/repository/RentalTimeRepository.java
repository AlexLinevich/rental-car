package by.lav.repository;

import by.lav.entity.RentalTime;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class RentalTimeRepository extends RepositoryBase<Integer, RentalTime> {

    public RentalTimeRepository(EntityManager entityManager) {
        super(RentalTime.class, entityManager);
    }
}
