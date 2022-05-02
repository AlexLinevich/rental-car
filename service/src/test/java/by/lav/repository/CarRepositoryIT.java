package by.lav.repository;

import by.lav.dto.CarFilter;
import by.lav.entity.Car;
import by.lav.util.HibernateUtil;
import by.lav.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CarRepositoryIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void finish() {
        sessionFactory.close();
    }

    @Test
    void checkSaveCar() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        Car car = Car.builder()
                .model("BMW")
                .colour("GREEN")
                .seatsQuantity(5)
                .build();

        var carRepository = new CarRepository(session);
        var save = carRepository.save(car);

        assertNotNull(save.getId());

        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteCar() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var carRepository = new CarRepository(session);
        carRepository.delete(1);

        Car car = session.get(Car.class, 1);
        assertNull(car);

        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateCar() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var carRepository = new CarRepository(session);

        Car car = session.get(Car.class, 1);
        car.setColour("BLUE");
        carRepository.update(car);

        session.flush();
        Car car1 = session.get(Car.class, 1);
        assertThat(car1.getColour()).isEqualTo("BLUE");

        session.getTransaction().rollback();
    }

    @Test
    void checkFindByIdCar() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var carRepository = new CarRepository(session);

        Optional<Car> car = carRepository.findById(1);

        assertThat(car).isNotNull();
        car.ifPresent(value -> assertThat(value.getModel()).isEqualTo("TOYOTA CAMRY"));
        car.ifPresent(value -> assertThat(value.getColour()).isEqualTo("WHITE"));

        session.getTransaction().rollback();
    }

    @Test
    void checkFindAllCars() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var carRepository = new CarRepository(session);

        List<Car> results = carRepository.findAll();
        assertThat(results).hasSize(6);

        List<String> cars = results.stream().map(Car::getModel).collect(toList());
        assertThat(cars).containsExactlyInAnyOrder(
                "TOYOTA CAMRY", "MAZDA 6", "TOYOTA LANDCRUISER", "MAZDA CX-9", "TOYOTA RAV4", "MAZDA CX-5");

        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByCarCategory() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var carRepository = new CarRepository(session);

        List<Car> results = carRepository.findAllByCarCategory(session, "LARGE SUV");
        assertThat(results).hasSize(2);

        session.getTransaction().rollback();
    }

    @Test
    void findCarDayPriceByCarModel() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var carRepository = new CarRepository(session);

        Optional<Double> carDayPrice = carRepository.findDayPriceByCarModel(session, "TOYOTA CAMRY");

        assertThat(carDayPrice).isNotNull();
        carDayPrice.ifPresent(value -> assertThat(value).isEqualTo(60.0));

        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByColourAndSeatsQuantity() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var carRepository = new CarRepository(session);

        CarFilter filter = CarFilter.builder()
                .colour("WHITE")
                .seatsQuantity(7)
                .build();
        List<Car> cars = carRepository.findAllByColourAndSeatsQuantity(
                session, filter);

        assertThat(cars).hasSize(2);

        List<String> modelNames = cars.stream().map(Car::getModel).collect(toList());
        assertThat(modelNames).containsExactlyInAnyOrder("TOYOTA LANDCRUISER", "MAZDA CX-9");

        session.getTransaction().rollback();
    }
}