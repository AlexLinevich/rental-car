package by.lav.dao;

import by.lav.entity.RentalTime;
import by.lav.util.HibernateUtil;
import by.lav.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class RentalTimeDaoIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final RentalTimeDao rentalTimeDao = RentalTimeDao.getInstance();

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void finish() {
        sessionFactory.close();
    }

    @Test
    void findAllRentalTimes() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<RentalTime> results = rentalTimeDao.findAll(session);
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
