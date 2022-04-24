package by.lav.dao;

import by.lav.by.lav.dto.UserFilter;
import by.lav.entity.User;
import by.lav.util.HibernateUtil;
import by.lav.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class UserDaoIT {

    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final UserDao userDao = UserDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findAllUsers() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> results = userDao.findAll(session);
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson", "Ivan Ivanov", "Alex Semenov");

        session.getTransaction().commit();
    }

    @Test
    void findUserByEmailAndPasswordWithCriteriaAPI() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<User> user = userDao.findByEmailAndPasswordWithCriteriaAPI(
                session, "test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));

        session.getTransaction().commit();
    }

    @Test
    void findUserByEmailAndPasswordWithQuerydsl() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<User> user = userDao.findByEmailAndPasswordWithQuerydsl(
                session, "test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));

        session.getTransaction().commit();
    }

    @Test
    void findUsersByFirstNameAndLastNameWithCriteriaAPI() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> users = userDao.findByFirstNameAndLastNameWithCriteriaAPI(
                session, null, "Robson");

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson");

        session.getTransaction().commit();
    }

    @Test
    void findUsersByFirstNameAndLastNameWithQuerydsl() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserFilter filter = UserFilter.builder()
                .firstName("Bob")
                .lastName("Robson")
                .build();
        List<User> users = userDao.findByFirstNameAndLastNameWithQuerydsl(
                session, filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson");

        session.getTransaction().commit();
    }
}