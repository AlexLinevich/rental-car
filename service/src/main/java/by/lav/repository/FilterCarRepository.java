package by.lav.repository;

import by.lav.dto.CarFilter;
import by.lav.entity.Car;

import java.util.List;

public interface FilterCarRepository {

    List<Car> findAllByFilter(CarFilter filter);
}
