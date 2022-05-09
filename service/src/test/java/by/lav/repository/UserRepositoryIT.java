package by.lav.repository;

import by.lav.dto.UserFilter;
import by.lav.entity.Role;
import by.lav.entity.User;
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

public class UserRepositoryIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private static final int ID_FIRST = 1;

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void finish() {
        sessionFactory.close();
    }

    @Test
    void checkSaveUser() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var user = User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();

        var userRepository = new UserRepository(session);
        userRepository.save(user);

        assertNotNull(user.getId());

        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteUser() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);
        userRepository.delete(ID_FIRST);

        User user = session.get(User.class, ID_FIRST);
        assertNull(user);

        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateUser() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);

        User user = session.get(User.class, ID_FIRST);
        user.setFirstName("Sveta");
        userRepository.update(user);

        session.flush();
        User user1 = session.get(User.class, ID_FIRST);
        assertThat(user1.getFirstName()).isEqualTo("Sveta");

        session.getTransaction().rollback();
    }

    @Test
    void checkFindByIdUser() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);

        Optional<User> user = userRepository.findById(ID_FIRST);

        assertThat(user).isNotNull();
        user.ifPresent(value -> assertThat(value.getFirstName()).isEqualTo("Bob"));
        user.ifPresent(value -> assertThat(value.getEmail()).isEqualTo("test1@tut.by"));

        session.getTransaction().rollback();
    }

    @Test
    void checkFindAllUsers() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);

        List<User> results = userRepository.findAll();
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson", "Ivan Ivanov", "Alex Semenov");

        session.getTransaction().rollback();
    }

    @Test
    void findUserByEmailAndPasswordWithCriteriaAPI() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);

        Optional<User> user = userRepository.findByEmailAndPasswordWithCriteriaAPI("test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));

        session.getTransaction().rollback();
    }

    @Test
    void findUserByEmailAndPasswordWithQuerydsl() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);

        Optional<User> user = userRepository.findByEmailAndPasswordWithQuerydsl("test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));

        session.getTransaction().rollback();
    }

    @Test
    void findUsersByFirstNameAndLastNameWithCriteriaAPI() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);

        UserFilter filter = UserFilter.builder()
                .firstName("Bob")
                .lastName("Robson")
                .build();

        List<User> users = userRepository.findByFirstNameAndLastNameWithCriteriaAPI(filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson");

        session.getTransaction().rollback();
    }

    @Test
    void findUsersByFirstNameAndLastNameWithQuerydsl() {
        var session = (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();

        var userRepository = new UserRepository(session);

        UserFilter filter = UserFilter.builder()
                .firstName("Bob")
                .lastName("Robson")
                .build();
        List<User> users = userRepository.findByFirstNameAndLastNameWithQuerydsl(filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson");

        session.getTransaction().rollback();
    }
}
