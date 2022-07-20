package by.lav.repository;

import by.lav.entity.RentalTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalTimeRepository extends JpaRepository<RentalTime, Integer> {

    List<RentalTime> findAllByCarId(Integer carId);
}
