package by.lav.dao;

import by.lav.dto.CarFilter;
import by.lav.entity.Car;
import by.lav.util.HibernateUtil;
import by.lav.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class CarDaoIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final CarDao carDao = CarDao.getInstance();

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void finish() {
        sessionFactory.close();
    }

    @Test
    void findAllCars() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Car> results = carDao.findAll(session);
        assertThat(results).hasSize(6);

        List<String> models = results.stream().map(Car::getModel).collect(toList());
        assertThat(models).containsExactlyInAnyOrder(
                "TOYOTA CAMRY", "MAZDA 6", "TOYOTA LANDCRUISER", "MAZDA CX-9", "TOYOTA RAV4", "MAZDA CX-5");

        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByCarCategory() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Car> results = carDao.findAllByCarCategory(session, "LARGE SUV");
        assertThat(results).hasSize(2);

        session.getTransaction().rollback();
    }

    @Test
    void findCarDayPriceByCarModel() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<Double> carDayPrice = carDao.findDayPriceByCarModel(session, "TOYOTA CAMRY");

        carDayPrice.ifPresent(value -> assertThat(value).isNotNull());
        carDayPrice.ifPresent(value -> assertThat(value).isEqualTo(60.0));

        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByColourAndSeatsQuantity() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        CarFilter filter = CarFilter.builder()
                .colour("WHITE")
                .seatsQuantity(7)
                .build();
        List<Car> cars = carDao.findAllByColourAndSeatsQuantity(
                session, filter);

        assertThat(cars).hasSize(2);

        List<String> modelNames = cars.stream().map(Car::getModel).collect(toList());
        assertThat(modelNames).containsExactlyInAnyOrder("TOYOTA LANDCRUISER", "MAZDA CX-9");

        session.getTransaction().rollback();
    }
}