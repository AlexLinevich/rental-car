package by.lav.repository;

import by.lav.entity.RentalTime;
import by.lav.util.HibernateUtil;
import by.lav.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RentalTimeRepositoryIT {

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
    void checkSaveRentalTime() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        RentalTime rentalTime = RentalTime.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        var rentalTimeRepository = new RentalTimeRepository(session);
        var save = rentalTimeRepository.save(rentalTime);

        assertNotNull(save.getId());

        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteRentalTime() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var rentalTimeRepository = new RentalTimeRepository(session);
        rentalTimeRepository.delete(1);

        RentalTime rentalTime = session.get(RentalTime.class, 1);
        assertNull(rentalTime);

        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateRentalTime() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var rentalTimeRepository = new RentalTimeRepository(session);

        RentalTime rentalTime = session.get(RentalTime.class, 1);
        rentalTime.setBeginTime(LocalDateTime.of(2025, 1, 25, 12, 0));
        rentalTimeRepository.update(rentalTime);

        session.flush();
        RentalTime rentalTime1 = session.get(RentalTime.class, 1);
        assertThat(rentalTime1.getBeginTime())
                .isEqualTo(LocalDateTime.of(2025, 1, 25, 12, 0));

        session.getTransaction().rollback();
    }

    @Test
    void checkFindByIdRentalTime() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var rentalTimeRepository = new RentalTimeRepository(session);

        var beginTime = LocalDateTime.of(2020, 1, 25, 12, 0);
        var endTime = LocalDateTime.of(2020, 1, 29, 18, 0);

        Optional<RentalTime> rentalTime = rentalTimeRepository.findById(1);
        rentalTime.ifPresent(System.out::println);

        assertThat(rentalTime).isNotNull();
        rentalTime.ifPresent(value -> assertThat(value.getBeginTime()).isEqualTo(beginTime));
        rentalTime.ifPresent(value -> assertThat(value.getEndTime()).isEqualTo(endTime));

        session.getTransaction().rollback();
    }

    @Test
    void checkFindAllRentalTimes() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var rentalTimeRepository = new RentalTimeRepository(session);

        List<RentalTime> results = rentalTimeRepository.findAll();
        assertThat(results).hasSize(4);

        var beginTime1 = LocalDateTime.of(2020, 1, 25, 12, 0);
        var beginTime2 = LocalDateTime.of(2020, 2, 25, 12, 0);
        var beginTime3 = LocalDateTime.of(2020, 5, 25, 12, 0);
        var beginTime4 = LocalDateTime.of(2020, 3, 25, 12, 0);
        List<LocalDateTime> beginTimes = results.stream().map(RentalTime::getBeginTime).collect(toList());
        assertThat(beginTimes).containsExactlyInAnyOrder(beginTime1, beginTime2, beginTime3, beginTime4);

        session.getTransaction().rollback();
    }
}
